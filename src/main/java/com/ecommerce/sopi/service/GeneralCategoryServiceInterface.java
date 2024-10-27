package com.ecommerce.sopi.service;

import java.util.List;

import com.ecommerce.sopi.DTO.response.GeneralCategoryResponse;

public interface GeneralCategoryServiceInterface {
	
	List<?> getAllGeneralCategory(); // Lấy ra tất cả GeneralCategory hiển thị trong mục sản phẩm trong thanh header
	
	GeneralCategoryResponse getGeneralCategoryById(Long Id); // Lấy ra GeneralCategory bằng id
	
	List<?> getAllGeneralCategoryDetail(); // Lấy ra tất cả GeneralCategory theo kiểu chi tiết, kết quả join từ nhiều bảng để hiện trang home
}
