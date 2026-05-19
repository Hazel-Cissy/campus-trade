package com.example.campustrade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.campustrade.common.Result;
import com.example.campustrade.dto.GoodsDto;
import com.example.campustrade.entity.Category;
import com.example.campustrade.entity.Goods;
import com.example.campustrade.entity.User;
import com.example.campustrade.service.CategoryService;
import com.example.campustrade.service.GoodsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        return "redirect:/goods/list";
    }

    @GetMapping("/goods/list")
    public String list(Model model,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword) {
        Page<Goods> result = goodsService.search(page, categoryId, keyword);
        List<Category> categories = categoryService.list();
        model.addAttribute("result", result);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("keyword", keyword);
        return "goods/list";
    }

    @GetMapping("/goods/detail")
    public String detail(@RequestParam Integer id, Model model) {
        Goods goods = goodsService.getById(id);
        if (goods == null) {
            return "redirect:/goods/list";
        }
        model.addAttribute("goods", goods);
        return "goods/detail";
    }

    @GetMapping("/goods/publish")
    public String publishPage(Model model) {
        model.addAttribute("categories", categoryService.list());
        return "goods/publish";
    }

    @PostMapping("/goods/publish")
    @ResponseBody
    public Result publish(@RequestBody GoodsDto dto, HttpSession session) {
        goodsService.publish(dto, session);
        return Result.ok("发布成功");
    }

    @PostMapping("/goods/offshelf")
    @ResponseBody
    public Result offShelf(@RequestParam Integer id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        goodsService.offShelf(id, user.getId());
        return Result.ok("下架成功");
    }

    @GetMapping("/goods/my")
    public String myGoods(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Goods> goodsList = goodsService.lambdaQuery()
                .eq(Goods::getSellerId, user.getId())
                .orderByDesc(Goods::getCreateTime)
                .list();
        model.addAttribute("goodsList", goodsList);
        return "goods/my";
    }
}
