package com.ecommerce.sopi.controller;

import java.lang.ProcessBuilder.Redirect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.sopi.DTO.request.AddressResquest;
import com.ecommerce.sopi.service.impl.AddressService;


@Controller
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@GetMapping("/my-address")
	public String address(Model model) {
		model.addAttribute("addressRequest",new AddressResquest());
		model.addAttribute("address", addressService.getAllAddressByUser());
		return "client/shippingAddress";
	}
	
	@PostMapping("/my-address/create")
	public String createAddress(@ModelAttribute("addressRequest") AddressResquest resquest,RedirectAttributes redirectAttributes  ){
		
		redirectAttributes.addFlashAttribute("message", "Tạo mới thành công");
		addressService.createAddress(resquest);
		return "redirect:/my-address";
	}
	@PostMapping("/my-address/delete/{id}")
	public String deletAddress(@PathVariable Long id) {
		addressService.deleteAddressById(id);
		return "redirect:/my-address";
	}
	
	@PostMapping("/my-address/setdefault/{id}")
	public String setDefaultAddress(@PathVariable Long id, RedirectAttributes redirectAttributes ) {
		redirectAttributes.addFlashAttribute("message", "Cập nhật thành công");
		addressService.setDefaultAddressById(id);
		return "redirect:/my-address";
	}
	
	@PostMapping("/my-address/update/{id}")
	public String updateAddress(@PathVariable Long id , @ModelAttribute("addressRequest") AddressResquest resquest,RedirectAttributes redirectAttributes  ) {
		redirectAttributes.addFlashAttribute("message", "Cập nhật thành công");
		addressService.updateAddress(resquest, id);
		return "redirect:/my-address";
	}
	
	
	
}
