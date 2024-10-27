package com.ecommerce.sopi.ADMIN.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.sopi.service.impl.ProductService;

@Controller
public class ADMINProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/admin/product")
	public String getAllOrder(Model model,@RequestParam(name="page",defaultValue = "0") int page
			,@RequestParam(name="field",defaultValue = "" ) String field,@RequestParam(name="order",defaultValue = "") String order) {
		String activeTitle="product";
		String [] titles= {"Id","Tên sản phẩm","Đơn giá","Thể loại","Số lượng màu","Đã bán"};
		String [] fields= {"id","name","price","category.name","colorQuantity","sold"};
		model.addAttribute("activeTitle",activeTitle);
		model.addAttribute("data", productService.getAllProduct(page,field,order));
		model.addAttribute("titles",titles);
		model.addAttribute("fields",fields);
		model.addAttribute("field",field);
		model.addAttribute("page",page);
		model.addAttribute("ordepr",order);
		return "admin/home";
	}
	@RequestMapping("/admin/product/update/sold")
	public String updateSold(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message","Thành công.");
		productService.updateProductSoldMultil();
		return "redirect:/admin/product";
		
	}
}
