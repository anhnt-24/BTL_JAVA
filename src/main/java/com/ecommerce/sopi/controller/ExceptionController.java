package com.ecommerce.sopi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController {
	
	@RequestMapping("/notfound/404")
	String notFound() {
		return "client/404view";
	}
}
