package com.ecommerce.sopi.globalvar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecommerce.sopi.DTO.response.GeneralCategoryResponse;
import com.ecommerce.sopi.DTO.response.UserResponse;
import com.ecommerce.sopi.service.impl.CartService;
import com.ecommerce.sopi.service.impl.GeneralCategoryService;
import com.ecommerce.sopi.service.impl.UserService;

// Các dữ liệu ở mức toàn cục trên thanh header (template engine helper)
@ControllerAdvice
public class GlobalVariable {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private GeneralCategoryService generalCategoryService;
	
	// Thông tin người dùng đang đăng nhập
	@ModelAttribute("UserAuth")
	public UserResponse getUserAuth() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		  if (authentication != null && authentication.isAuthenticated() && authentication.getName()!="anonymousUser") {
			  UserResponse userResponse=userService.getMyInfo();
			  return userResponse;
		  }
		return null;
	}
	
	// Số lượng sản phẩm trong giỏ hàng
	@ModelAttribute("CartSize")
	public long getCartSize() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() && authentication.getName()!="anonymousUser") {
			return cartService.getCartItemCount();
		}
		return (long) 0;
	}
	
	// Mục sản phẩm trên thanh header
	@ModelAttribute("HeaderProductCategory")
	public List<GeneralCategoryResponse> getAllGeneralCategory(){
		return generalCategoryService.getAllGeneralCategory();
	}
	
	// Lấy ra role người dùng hiện tại
	@ModelAttribute("ROLE")
	public String getUserRole() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		  if (authentication != null && authentication.isAuthenticated() && authentication.getName()!="anonymousUser") {
			  return userService.getAthenticatedUser().getRole().toString();
			  
		  }
		return null;
	}
	
}
