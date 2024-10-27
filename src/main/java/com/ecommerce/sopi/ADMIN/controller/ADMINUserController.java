package com.ecommerce.sopi.ADMIN.controller;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.sopi.entity.Gender;
import com.ecommerce.sopi.entity.Roles;
import com.ecommerce.sopi.service.impl.UserService;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Controller
public class ADMINUserController {
	@Autowired
	private UserService userService;

	@GetMapping("/admin/user")
	public String getAllUser(Model model,@RequestParam(name="page",defaultValue = "0") int page
			,@RequestParam(name="field",defaultValue = "" ) String field,@RequestParam(name="order",defaultValue = "") String order) {
		String activeTitle="user";
		String [] titles= {"Id","Tên đăng nhập","Họ và tên","Email","Số điện thoại","Giới tính","Role","Chi tiêu","Đơn thành công"};
		String [] fields= {"id","username","name","email","phone","gender","role","total","orderSuccess"};
		model.addAttribute("data",userService.getAllUser(page,field,order));
		model.addAttribute("activeTitle",activeTitle);
		model.addAttribute("titles",titles);
		model.addAttribute("fields",fields);
		model.addAttribute("field",field);
		model.addAttribute("page",page);
		model.addAttribute("order",order);
		return "admin/home";
	}
}
