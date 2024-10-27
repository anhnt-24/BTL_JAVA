package com.ecommerce.sopi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.sopi.DTO.request.CartRequest;
import com.ecommerce.sopi.service.impl.CartService;

import jakarta.websocket.server.PathParam;

@Controller
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@GetMapping("/my-cart")
	public String cart(Model model) {
		model.addAttribute("cart",cartService.getALLCartItemByUser());
		
		return "client/cart";
	}
	
	@PostMapping("/my-cart/create")
	public String createCartItem(@ModelAttribute("cartRequest") CartRequest request,RedirectAttributes redirectAttributes) {
		cartService.createCartItem(request);
		redirectAttributes.addFlashAttribute("message", "Thêm giỏ hàng thành công");
		return "redirect:/my-cart";
		
	}
	
	@PostMapping("/my-cart/quantity/{id}/{quantity}")
	public String updateQuantityCartItem(@PathVariable(name = "id") Long id,@PathVariable(name = "quantity") Long quantity) {
		cartService.addCartItemQuantity(id, quantity);
		return "redirect:/my-cart";
	}
	
	@PostMapping("/my-cart/delete/{id}")
	public String deleteCartItem(@PathVariable Long id) {
		cartService.deleteCartItemById(id);
		return "redirect:/my-cart";
	}
	@PostMapping("/my-cart/delete/multi")
	public String deleteCartItem(@RequestParam("cartItem[]") ArrayList<Long> list ,RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "Xóa thành công");
		cartService.deleteCartItemByListId(list);
		return "redirect:/my-cart";
	}
}
