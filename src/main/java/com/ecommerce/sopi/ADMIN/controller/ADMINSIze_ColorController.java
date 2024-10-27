package com.ecommerce.sopi.ADMIN.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.sopi.service.impl.ProductService;
import com.ecommerce.sopi.service.impl.Size_ColorService;

@Controller
public class ADMINSIze_ColorController {
	
	@Autowired
	private Size_ColorService size_ColorService;
	


	@GetMapping("/admin/sizeandcolor")
	public String getAllOrder(Model model,@RequestParam(name="page",defaultValue = "0") int page
			,@RequestParam(name="field",defaultValue = "" ) String field,@RequestParam(name="order",defaultValue = "") String order) {
		String activeTitle="colorAndsize";
		String [] titles= {"Id","Mã sản phẩm","Tên sản phẩm","Màu sắc","Kích cỡ","Hình ảnh","Đã bán","Kho"};
		String [] fields= {"id","color.product.id","color.product.name","color.color","size.size","color.img","sold","stock"};
		model.addAttribute("activeTitle",activeTitle);
		model.addAttribute("data", size_ColorService.getAllSize_Color(page,field,order));
		model.addAttribute("titles",titles);
		model.addAttribute("fields",fields);
		model.addAttribute("field",field);
		model.addAttribute("page",page);
		model.addAttribute("order",order);
		return "admin/home";
	}
}
