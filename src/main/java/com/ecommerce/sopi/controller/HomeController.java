package com.ecommerce.sopi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerce.sopi.service.impl.GeneralCategoryService;
import com.ecommerce.sopi.service.impl.SliderService;

@Controller
public class HomeController {
	
	@Autowired
	private GeneralCategoryService generalCategoryService;
	
	@Autowired
	private SliderService sliderService;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("generalCategory",generalCategoryService.getAllGeneralCategoryDetail());
		model.addAttribute("slider",sliderService.getAllSlider());
		return "client/home";
	}
	
}
