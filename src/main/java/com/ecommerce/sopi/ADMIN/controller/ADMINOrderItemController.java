package com.ecommerce.sopi.ADMIN.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.sopi.service.impl.OrderItemService;
import com.ecommerce.sopi.service.impl.OrderService;

@Controller
public class ADMINOrderItemController {

	@Autowired
	private OrderItemService orderItemService;
	
	@GetMapping("/admin/orderitem")
	public String getAllOrder(Model model,@RequestParam(name="page",defaultValue = "0") int page
			,@RequestParam(name="field",defaultValue = "" ) String field,@RequestParam(name="order",defaultValue = "") String order) {
		String activeTitle="orderItem";
		String [] titles= {"Id","Mã đơn hàng","Mã sản phẩm","Màu sắc","Kích cỡ","Trạng thái","Người nhận","Số điện thoại","Địa chỉ","Xã/Phường","Quận/Huyện","Tỉnh/Thành phố","Số lượng","Thành tiền","Cập nhật lúc"};
		String [] fields= {"id","order.code","sizecolor.color.product.id","sizecolor.color.color","sizecolor.size.size","status","receiver","phone","detail","commune","district","province","quantity","total","deliveredAt"};
		model.addAttribute("data",orderItemService.getAllOrderItem(page,field,order));
		model.addAttribute("activeTitle",activeTitle);
		model.addAttribute("titles",titles);
		model.addAttribute("fields",fields);
		model.addAttribute("field",field);
		model.addAttribute("page",page);
		model.addAttribute("order",order);
		return "admin/home";
	}
	@PostMapping("/admin/orderitem/update/{id}/{status}")
	public String updateOrderStatus(@PathVariable(name = "id")  Long id,
			@PathVariable(name = "status")  String status ,RedirectAttributes redirectAttributes) {
		orderItemService.updateOrderItemStatus(id,status);
		redirectAttributes.addFlashAttribute("message", "Thành công");
		return "redirect:/admin/orderitem";
	}
}
