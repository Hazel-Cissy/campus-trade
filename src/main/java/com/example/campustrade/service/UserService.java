package com.example.campustrade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campustrade.dto.LoginDto;
import com.example.campustrade.entity.User;

public interface UserService extends IService<User> {
    User login(LoginDto dto);
    void register(User user);
}
