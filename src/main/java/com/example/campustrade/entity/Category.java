package com.example.campustrade.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName("category")
public class Category {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
