package com.ecommerce.sopi.ADMIN.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerce.sopi.entity.OrderItemEntity;
import com.ecommerce.sopi.service.impl.ClientService;
import com.ecommerce.sopi.service.impl.DiscountService;
import com.ecommerce.sopi.service.impl.MessageService;
import com.ecommerce.sopi.service.impl.OrderItemService;
import com.ecommerce.sopi.service.impl.ProductService;
import com.ecommerce.sopi.service.impl.RevenueService;

@Controller
public class ADMINDashboardController {
	@Autowired
	private RevenueService revenueService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private DiscountService discountService;
	
	@Autowired
	private MessageService messageService;
	
	@GetMapping("/admin/dashboard")
	public String dashboard(Model model) {
		String [] titles= {"Dòng tiền","Khách hàng","Đơn hàng","Sản phẩm", "Mã giảm giá"};
		List<List<?>> data=new ArrayList<>();
		String activeTitle="dashboard";
		model.addAttribute("activeTitle",activeTitle);
		model.addAttribute("titles",titles);
		data.add(revenueService.getRevenueResponse());
		data.add(clientService.getClientResponse());
		data.add(orderItemService.getOrderItemForDashboard());
		data.add(productService.getProductForDashboard());
		data.add(discountService.getDiscountForDashboard());
		model.addAttribute("data",data);
		model.addAttribute("messageDashboard",messageService.getMessageForDashboard());
		return "admin/dashboard";
	}
	
}
