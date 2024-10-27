package com.ecommerce.sopi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.sopi.DTO.request.CartRequest;
import com.ecommerce.sopi.DTO.response.DiscountResponse;
import com.ecommerce.sopi.DTO.response.PaymentResponse;
import com.ecommerce.sopi.service.impl.AddressService;
import com.ecommerce.sopi.service.impl.DiscountService;
import com.ecommerce.sopi.service.impl.PaymentService;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;

@Controller
public class PaymentController {
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private DiscountService discountService;
	
	@GetMapping("/payment")
	public String payment(HttpSession session,Model model,RedirectAttributes redirectAttributes) {
		if(session.getAttribute("payment")==null) {
			redirectAttributes.addFlashAttribute("info","Bạn chưa có đơn hàng nào cần xác nhận.");
			return "redirect:/";
		}
		if(session.getAttribute("address")==null) {
			session.setAttribute("address", addressService.getDefaultAddress());
		}
		model.addAttribute("allAddress",addressService.getAllAddressByUser());
		return "client/payment";
	}
	
	@PostMapping("/payment/processMono")
	public String processMono(HttpSession session,@ModelAttribute("cartRequest") CartRequest request) {
		session.setAttribute("payment", paymentService.proccessMono(request));
		session.setAttribute("discount", (long)0);
		return "redirect:/payment";
	}
	
	@PostMapping("/payment/processMulti")
	public String processMulti(HttpSession session,@RequestParam("cartItem[]") List<Long> cartIds,
			@RequestParam("total") Long total) {
		session.setAttribute("payment",paymentService.proccessMulti(cartIds, total));
		session.setAttribute("discount", (long)0);
		session.setAttribute("cartIds", cartIds);
		return "redirect:/payment";
	}
	
	@PostMapping("/payment/changeAddress")
	public String changeAddress(HttpSession session,@RequestParam("address") Long addressId,RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "Cập nhật thành công");
		session.setAttribute("address", addressService.getAddressById(addressId));
		return "redirect:/payment";
	}
	
	@PostMapping("/payment/discount/apply")
	public String applyDiscount(HttpSession session,@RequestParam("discount") String discount,RedirectAttributes redirectAttributes) {
		DiscountResponse discountResponse=discountService.getDiscoutByCode(discount);
		if(discountResponse!=null) {
			redirectAttributes.addFlashAttribute("message", "Áp dụng thành công");
			PaymentResponse paymentResponse=(PaymentResponse) session.getAttribute("payment");
			long preDiscount=(long) session.getAttribute("discount");
			
			session.setAttribute("discount", discountResponse.discount);
			session.setAttribute("code", discount);
			session.setAttribute("payment",paymentService.applyDiscount(preDiscount, discountResponse.discount, paymentResponse));
		}else {
			redirectAttributes.addFlashAttribute("info","Mã giảm giá không tồn tại.");
		}
		return "redirect:/payment";
	}
	@GetMapping("/payment/discount/remove")
	public String removeDiscount(HttpSession session) {
		PaymentResponse paymentResponse=(PaymentResponse) session.getAttribute("payment");
		Long preDiscount=(Long) session.getAttribute("discount");
		session.setAttribute("discount", (long) 0);
		session.removeAttribute("code");
		session.setAttribute("payment",paymentService.applyDiscount(preDiscount, (long)0, paymentResponse));
		return "redirect:/payment";
	}
	
}
