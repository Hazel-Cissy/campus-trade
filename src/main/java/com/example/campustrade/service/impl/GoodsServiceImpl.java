package com.example.campustrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.campustrade.common.BizException;
import com.example.campustrade.dto.GoodsDto;
import com.example.campustrade.entity.Goods;
import com.example.campustrade.entity.User;
import com.example.campustrade.mapper.GoodsMapper;
import com.example.campustrade.service.GoodsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Override
    public void publish(GoodsDto dto, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new BizException("商品标题不能为空");
        }
        if (dto.getPrice() == null || dto.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new BizException("价格必须大于0");
        }
        Goods goods = new Goods();
        goods.setTitle(dto.getTitle());
        goods.setPrice(dto.getPrice());
        goods.setDescription(dto.getDescription());
        goods.setCategoryId(dto.getCategoryId());
        goods.setSellerId(user.getId());
        goods.setStatus("在售");
        goods.setCreateTime(LocalDateTime.now());
        baseMapper.insert(goods);
    }

    @Override
    public Page<Goods> search(int page, Integer categoryId, String keyword) {
        Page<Goods> p = new Page<>(page, 12);
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq("status", "在售");
        if (categoryId != null) {
            wrapper.eq("category_id", categoryId);
        }
        if (keyword != null && !keyword.isBlank()) {
            wrapper.like("title", keyword);
        }
        wrapper.orderByDesc("create_time");
        return baseMapper.selectPage(p, wrapper);
    }

    @Override
    public void offShelf(Integer goodsId, Integer userId) {
        Goods goods = baseMapper.selectById(goodsId);
        if (goods == null) {
            throw new BizException("商品不存在");
        }
        if (!goods.getSellerId().equals(userId)) {
            throw new BizException("只能下架自己的商品");
        }
        goods.setStatus("已下架");
        baseMapper.updateById(goods);
    }
}
