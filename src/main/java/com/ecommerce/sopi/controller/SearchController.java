package com.ecommerce.sopi.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.sopi.service.impl.ProductService;

@Controller
public class SearchController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/search/product")
	public String searchProduct(
			@RequestParam("search") String search,
			@RequestParam("page") int page,
			@RequestParam("field") String field,
			@RequestParam("order") String order,
			Model model) {
		
		model.addAttribute("product",productService.findProductByName(search, page, field, order));
		model.addAttribute("search",search);
		model.addAttribute("page",page);
		model.addAttribute("order", order);
		model.addAttribute("field", field);
		return "client/search";
		
	}
}
