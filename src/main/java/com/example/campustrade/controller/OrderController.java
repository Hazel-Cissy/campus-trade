package com.example.campustrade.controller;

import com.example.campustrade.common.Result;
import com.example.campustrade.entity.Order;
import com.example.campustrade.entity.User;
import com.example.campustrade.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/create")
    @ResponseBody
    public Result create(@RequestParam Integer goodsId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        orderService.createOrder(goodsId, user.getId());
        return Result.ok("下单成功");
    }

    @PostMapping("/order/confirm")
    @ResponseBody
    public Result confirm(@RequestParam Integer orderId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        orderService.confirmOrder(orderId, user.getId());
        return Result.ok("已确认");
    }

    @PostMapping("/order/cancel")
    @ResponseBody
    public Result cancel(@RequestParam Integer orderId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        orderService.cancelOrder(orderId, user.getId());
        return Result.ok("已取消");
    }

    @GetMapping("/order/my")
    public String myOrders(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Order> buyOrders = orderService.getBuyOrders(user.getId());
        List<Order> sellOrders = orderService.getSellOrders(user.getId());
        model.addAttribute("buyOrders", buyOrders);
        model.addAttribute("sellOrders", sellOrders);
        return "order/list";
    }
}
