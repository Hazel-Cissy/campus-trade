package com.example.campustrade.controller;

import com.example.campustrade.common.BizException;
import com.example.campustrade.common.Result;
import com.example.campustrade.dto.LoginDto;
import com.example.campustrade.entity.User;
import com.example.campustrade.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/user/login")
    @ResponseBody
    public Result login(@RequestBody LoginDto dto, HttpSession session) {
        User user = userService.login(dto);
        session.setAttribute("user", user);
        return Result.ok("登录成功");
    }

    @PostMapping("/user/register")
    @ResponseBody
    public Result register(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new BizException("用户名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new BizException("密码至少6位");
        }
        userService.register(user);
        return Result.ok("注册成功");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
