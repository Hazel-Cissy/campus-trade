package com.example.campustrade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campustrade.dto.GoodsDto;
import com.example.campustrade.entity.Goods;
import jakarta.servlet.http.HttpSession;

public interface GoodsService extends IService<Goods> {
    void publish(GoodsDto dto, HttpSession session);
    Page<Goods> search(int page, Integer categoryId, String keyword);
    void offShelf(Integer goodsId, Integer userId);
}
