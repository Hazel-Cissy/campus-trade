package com.example.campustrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.campustrade.common.BizException;
import com.example.campustrade.entity.Goods;
import com.example.campustrade.entity.Order;
import com.example.campustrade.mapper.GoodsMapper;
import com.example.campustrade.mapper.OrderMapper;
import com.example.campustrade.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    @Transactional
    public void createOrder(Integer goodsId, Integer buyerId) {
        Goods goods = goodsMapper.selectById(goodsId);
        if (goods == null) {
            throw new BizException("商品不存在");
        }
        if (!"在售".equals(goods.getStatus())) {
            throw new BizException("商品已被买走或下架");
        }
        if (goods.getSellerId().equals(buyerId)) {
            throw new BizException("不能购买自己的商品");
        }

        Order order = new Order();
        order.setGoodsId(goodsId);
        order.setBuyerId(buyerId);
        order.setSellerId(goods.getSellerId());
        order.setStatus("待确认");
        order.setCreateTime(LocalDateTime.now());
        baseMapper.insert(order);

        goods.setStatus("已售出");
        goodsMapper.updateById(goods);
    }

    @Override
    public void confirmOrder(Integer orderId, Integer sellerId) {
        Order order = baseMapper.selectById(orderId);
        if (order == null) {
            throw new BizException("订单不存在");
        }
        if (!order.getSellerId().equals(sellerId)) {
            throw new BizException("只能确认自己的订单");
        }
        if (!"待确认".equals(order.getStatus())) {
            throw new BizException("订单状态不正确");
        }
        order.setStatus("已完成");
        baseMapper.updateById(order);
    }

    @Override
    public void cancelOrder(Integer orderId, Integer buyerId) {
        Order order = baseMapper.selectById(orderId);
        if (order == null) {
            throw new BizException("订单不存在");
        }
        if (!order.getBuyerId().equals(buyerId)) {
            throw new BizException("只能取消自己的订单");
        }
        if (!"待确认".equals(order.getStatus())) {
            throw new BizException("订单状态不正确");
        }
        order.setStatus("已取消");
        baseMapper.updateById(order);

        Goods goods = goodsMapper.selectById(order.getGoodsId());
        goods.setStatus("在售");
        goodsMapper.updateById(goods);
    }

    @Override
    public List<Order> getBuyOrders(Integer buyerId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_id", buyerId).orderByDesc("create_time");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<Order> getSellOrders(Integer sellerId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("seller_id", sellerId).orderByDesc("create_time");
        return baseMapper.selectList(wrapper);
    }
}
