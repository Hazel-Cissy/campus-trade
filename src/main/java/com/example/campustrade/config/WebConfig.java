package com.example.campustrade.config;

import com.example.campustrade.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/", "/login", "/register",
                        "/user/login", "/user/register",
                        "/goods/list", "/goods/detail",
                        "/css/**", "/js/**", "/img/**"
                );
    }
}
