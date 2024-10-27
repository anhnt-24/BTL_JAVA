package com.ecommerce.sopi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.sopi.DTO.request.RegisterRequest;
import com.ecommerce.sopi.entity.Roles;
import com.ecommerce.sopi.service.impl.UserService;


@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/register/view")
	public String registerView(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() && authentication.getName()!="anonymousUser") {
			return "redirect:/";
		}
		model.addAttribute("user",new RegisterRequest());
		return "client/register";
	}
	
	@PostMapping("/register")
	public String register(RegisterRequest request,RedirectAttributes redirectAttributes) {
		String error=userService.checkRegister(request,Roles.USER);
		if(error!=null) {
			redirectAttributes.addFlashAttribute("error",error);
			return "redirect:/register/view";
		}
		redirectAttributes.addFlashAttribute("message","Đăng kí thành công");
		return "redirect:/client/login";
	}
	
}
