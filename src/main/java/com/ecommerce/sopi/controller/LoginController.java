package com.ecommerce.sopi.controller;

import java.lang.ProcessBuilder.Redirect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.sopi.DTO.request.LoginRequest;
import com.ecommerce.sopi.service.impl.UserService;

import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("/client/")
public class LoginController {
	
	@GetMapping("/login")
	public String loginView() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() && authentication.getName()!="anonymousUser") {
			return "redirect:/";
		}
		return "client/login";
	}
	
	
	
}
