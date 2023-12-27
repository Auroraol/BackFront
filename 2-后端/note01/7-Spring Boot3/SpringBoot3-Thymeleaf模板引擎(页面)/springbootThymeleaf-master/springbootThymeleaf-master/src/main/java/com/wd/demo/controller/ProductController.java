package com.wd.demo.controller;

import com.wd.demo.db2.service.IProductService;
import com.wd.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    IProductService productService;


    /**
     * 商品列表
     * @param model
     * @return
     */
    @RequestMapping("/productList")
    public String productList(Model model){
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "product/productList";
    }

}
