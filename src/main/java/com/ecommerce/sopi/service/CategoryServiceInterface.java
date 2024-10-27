package com.ecommerce.sopi.service;

import java.util.List;

import com.ecommerce.sopi.DTO.response.CategoryResponse;

public interface CategoryServiceInterface {
	List<?> getAllCategory(); // Lấy ra tất cả các danh mục
	
	CategoryResponse getCategoryById(Long id); // Lấy ra danh mục bằng id
	
	List<?> getAllCategoryByGeneralId(Long id); // Lấy ra tất cả danh mục bằng general_category
}
