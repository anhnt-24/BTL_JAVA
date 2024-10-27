package com.ecommerce.sopi.ADMIN.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.sopi.DTO.response.MessageResponse;

@Controller
public class ADMINLoginController {

	@GetMapping("/admin/login")
	public String loginViewAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() && authentication.getName()!="anonymousUser") {
			return "redirect:/admin";
		}
		return "admin/login";
	}

}
