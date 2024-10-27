package com.ecommerce.sopi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.sopi.DTO.response.MessageResponse;
import com.ecommerce.sopi.service.impl.OrderService;

@Controller
public class LookupProductController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/lookup/product")
	public String lookupProduct() {
		return "client/lookup";
	}
	@PostMapping("/lookup/product")
	public String lookupProductPost(@RequestParam("code") String code, Model model,RedirectAttributes redirectAttributes) {
		
		List<?> orderItem=orderService.getOrderByCode(code);
		if(orderItem==null || orderItem.size()==0) {
			MessageResponse messageResponse=new MessageResponse("error", "Mã đơn hàng không tồn tại.");
			redirectAttributes.addFlashAttribute("messageResponse",messageResponse);
			return "redirect:/lookup/product";
		}
		MessageResponse messageResponse=new MessageResponse("success","Kết quả tra cứu cho mã đơn hàng "+code);
		model.addAttribute("messageResponse",messageResponse);
		model.addAttribute("order",orderItem);
		return "client/lookup";
	}
	
}
