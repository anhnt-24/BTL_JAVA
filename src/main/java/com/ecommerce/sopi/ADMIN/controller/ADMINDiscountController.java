package com.ecommerce.sopi.ADMIN.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.sopi.DTO.request.DiscountRequest;
import com.ecommerce.sopi.service.impl.DiscountService;

@Controller
public class ADMINDiscountController {

	@Autowired
	private DiscountService discountService;

	@GetMapping("/admin/discount")
	public String getAllDiscount(Model model,@RequestParam(name="page",defaultValue = "0") int page
			,@RequestParam(name="field",defaultValue = "" ) String field,@RequestParam(name="order",defaultValue = "") String order) {
		String activeTitle="discount";
		String [] titles= {"Id","Mã giảm giá","Lượt sử dụng","Chiết khấu","Thao tác"};
		String [] fields= {"id","code","turn","discount","action"};
		model.addAttribute("activeTitle",activeTitle);
		model.addAttribute("data", discountService.getAllDiscount(page,field,order));
		model.addAttribute("titles",titles);
		model.addAttribute("fields",fields);
		model.addAttribute("field",field);
		model.addAttribute("page",page);
		model.addAttribute("order",order);
		model.addAttribute("request",new DiscountRequest());
		return "admin/home";
	}
	
	@PostMapping("/admin/discount/delete/{id}")
	public String delete(@PathVariable Long id) {
		discountService.delete(id);
		return "redirect:/admin/discount";
	}
	
	@PostMapping("/admin/discount/create")
	public String create(@ModelAttribute("request") DiscountRequest request,RedirectAttributes redirectAttributes) {
		String message=discountService.create(request);
		if(message!=null)
			redirectAttributes.addFlashAttribute("info",message);
		else {
			redirectAttributes.addFlashAttribute("message","Thành công");
		}
		return "redirect:/admin/discount";
	}
	

}
