package com.example.campustrade.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("goods")
public class Goods {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private BigDecimal price;
    private String description;
    private String image;
    private String status;
    private Integer sellerId;
    private Integer categoryId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getSellerId() { return sellerId; }
    public void setSellerId(Integer sellerId) { this.sellerId = sellerId; }
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
}
