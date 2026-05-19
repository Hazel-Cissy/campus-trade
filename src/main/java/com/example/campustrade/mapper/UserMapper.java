package com.example.campustrade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.campustrade.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
