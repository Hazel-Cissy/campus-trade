package com.example.campustrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campustrade.entity.Order;

import java.util.List;

public interface OrderService extends IService<Order> {
    void createOrder(Integer goodsId, Integer buyerId);
    void confirmOrder(Integer orderId, Integer sellerId);
    void cancelOrder(Integer orderId, Integer buyerId);
    List<Order> getBuyOrders(Integer buyerId);
    List<Order> getSellOrders(Integer sellerId);
}
