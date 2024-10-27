package com.ecommerce.sopi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.sopi.DTO.request.CartRequest;
import com.ecommerce.sopi.service.impl.ProductService;

@Controller
public class ProductDetailController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/productdetail/{id}")
	public String productdetail(@PathVariable Long id, Model model) {
		model.addAttribute("product",productService.getProductByIdDetail(id));
		model.addAttribute("cartRequest",new CartRequest());
		
		return "client/productdetail";
	}
}
