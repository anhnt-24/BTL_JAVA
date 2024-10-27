package com.ecommerce.sopi.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
	
	// Có lỗi thì chuyển về trang 404
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	String handleNotFound() {
		return "redirect:/notfound/404";
		
	}
}
