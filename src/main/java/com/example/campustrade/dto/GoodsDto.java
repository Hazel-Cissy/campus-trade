package com.example.campustrade.dto;

import java.math.BigDecimal;

public class GoodsDto {
    private String title;
    private BigDecimal price;
    private String description;
    private Integer categoryId;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
}
