package com.ecommerce.sopi.ADMIN.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.sopi.service.impl.OrderItemService;
import com.ecommerce.sopi.service.impl.OrderService;

@Controller
public class ADMINOrderController {
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/admin/order")
	public String getAllOrder(Model model,@RequestParam(name="page",defaultValue = "0") int page
			,@RequestParam(name="field",defaultValue = "" ) String field,@RequestParam(name="order",defaultValue = "") String order) {
		String activeTitle="order";
		String [] titles= {"Id","Mã đơn hàng","Mã người dùng","Số lượng đơn", "Giảm","Phí vận chuyển","Thành tiền"};
		String [] fields= {"id","code","user.id","orderItem","discount","deliveryFee","total"};
		model.addAttribute("data",orderService.getAllOrder(page,field,order));
		model.addAttribute("activeTitle",activeTitle);
		model.addAttribute("titles",titles);
		model.addAttribute("fields",fields);
		model.addAttribute("field",field);
		model.addAttribute("page",page);
		model.addAttribute("order",order);
		return "admin/home";
	}
}
