package com.example.campustrade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.campustrade.entity.Category;
import com.example.campustrade.mapper.CategoryMapper;
import com.example.campustrade.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
