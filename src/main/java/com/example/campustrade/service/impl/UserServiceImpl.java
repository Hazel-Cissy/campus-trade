package com.example.campustrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.campustrade.common.BizException;
import com.example.campustrade.dto.LoginDto;
import com.example.campustrade.entity.User;
import com.example.campustrade.mapper.UserMapper;
import com.example.campustrade.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User login(LoginDto dto) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", dto.getUsername());
        User user = baseMapper.selectOne(wrapper);
        if (user == null) {
            throw new BizException("用户不存在");
        }
        if (!user.getPassword().equals(dto.getPassword())) {
            throw new BizException("密码错误");
        }
        return user;
    }

    @Override
    public void register(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        if (baseMapper.selectOne(wrapper) != null) {
            throw new BizException("用户名已存在");
        }
        user.setCreateTime(LocalDateTime.now());
        baseMapper.insert(user);
    }
}
