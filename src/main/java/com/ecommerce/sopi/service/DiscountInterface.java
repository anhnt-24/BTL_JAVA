package com.ecommerce.sopi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommerce.sopi.DTO.request.DiscountRequest;
import com.ecommerce.sopi.DTO.response.DiscountResponse;

public interface DiscountInterface {
	Page<?> getAllDiscount(int offset,String field,String order); // Lấy ra tất cả mã giảm giá hiện có theo kiểu page
	
	DiscountResponse getDiscoutByCode(String code); // Lấy ra mã giảm giá theo code
	
	List<?> getDiscountForDashboard(); // Lấy ra tất cả thông tin về mã giảm giá hiển thị trên trang dashboard
	
	void delete(Long id); // Xóa một mã giảm giá bởi id
	
	String create(DiscountRequest discountRequest); // Thêm mới một mã giảm giá
}
