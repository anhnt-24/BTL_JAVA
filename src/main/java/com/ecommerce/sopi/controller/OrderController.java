package com.ecommerce.sopi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.sopi.DTO.response.AddressResponse;
import com.ecommerce.sopi.DTO.response.PaymentResponse;
import com.ecommerce.sopi.service.impl.AddressService;
import com.ecommerce.sopi.service.impl.CartService;
import com.ecommerce.sopi.service.impl.DiscountService;
import com.ecommerce.sopi.service.impl.OrderItemService;
import com.ecommerce.sopi.service.impl.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private DiscountService discountService;
	
	@Autowired
	private CartService cartService;
	
	@GetMapping("/my-order")
	public String order(Model model) {
		model.addAttribute("order",orderService.getAllOrderByUser());
		model.addAttribute("address",addressService.getAllAddressByUser());
		return "client/order";
	}
	
	@PostMapping("/my-order/create")
	public String createOrder(HttpSession session,RedirectAttributes redirectAttributes) {
		PaymentResponse paymentResponse=(PaymentResponse) session.getAttribute("payment");
		AddressResponse addressResponse=(AddressResponse) session.getAttribute("address");
		Long discount=(Long) session.getAttribute("discount");
		orderService.createOrder(paymentResponse, addressResponse.id,discount);
		session.removeAttribute("payment");
		session.removeAttribute("discount");
		session.removeAttribute("address");
		if(session.getAttribute("cartIds")!=null) {
			cartService.deleteCartItemByListId((List<Long>) session.getAttribute("cartIds"));
			session.removeAttribute("cartIds");
		}
		if(session.getAttribute("code")!=null) {
			discountService.useCode((String)session.getAttribute("code"));
			session.removeAttribute("code");
		}
		redirectAttributes.addFlashAttribute("message", "Đặt hàng thành công");
		return "redirect:/my-order";
	}
	@PostMapping("/my-order/changeAddress/{orderId}")
	public String changeAddressOrder(@PathVariable(name = "orderId") Long orderId, 
			@RequestParam("address") Long addressId,RedirectAttributes redirectAttributes) {
		orderItemService.changeAddressOrderItem(orderId, addressId);
		redirectAttributes.addFlashAttribute("message", "Cập nhật thành công");
		return "redirect:/my-order";
	}
	
	@PostMapping("/my-order/update/{id}/{status}")
	public String updateOrderStatus(@PathVariable(name = "id")  Long id,
			@PathVariable(name = "status")  String status ,RedirectAttributes redirectAttributes) {
		orderItemService.updateOrderItemStatus(id,status);
		redirectAttributes.addFlashAttribute("message", "Thành công");
		return "redirect:/my-order";
	}
	@PostMapping("/my-order/return/{id}")
	public String returnOrderItem(@PathVariable(name = "id")  Long id,
			RedirectAttributes redirectAttributes) {
		orderItemService.returnOrderItem(id);
		redirectAttributes.addFlashAttribute("message", "Thành công");
		return "redirect:/my-order";
	}
	
}
