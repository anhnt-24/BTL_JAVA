package com.ecommerce.sopi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.sopi.DTO.response.UserResponse;
import com.ecommerce.sopi.service.impl.UserService;
import com.ecommerce.sopi.service.impl.FileUploadService;


@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileUploadService fileUploadService;

	@ModelAttribute("UserAuth")
	public UserResponse getUserAuth() {
		return userService.getMyInfo();
	}
	
	@GetMapping("/my-info")
	public String user(Model model) {
		UserResponse response=userService.getMyInfo();
		response.setEmail(response.formatEmail(response.getEmail()));
		response.setPhone(response.formatPhoneNumber(response.getPhone()));
		
		model.addAttribute("user",response);
		return "client/user";
	}
	
	@PostMapping("/my-info/update")
	public String updateUser(@ModelAttribute("user") UserResponse response, @RequestParam(value = "file") MultipartFile file
			 ,RedirectAttributes redirectAttributes) {
		if(file!=null && !file.isEmpty() && file.getSize() > 0) {
			response.setImg(fileUploadService.handleFileUpload(response.getImg(),file));
		}
		redirectAttributes.addFlashAttribute("message", "Cập nhật thành công");
		userService.updateUser(response);
		return "redirect:/my-info";
	}
}
