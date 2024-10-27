package com.ecommerce.sopi.service;

import java.util.List;

import com.ecommerce.sopi.DTO.response.SizeResponse;

public interface SizeServiceInterface {
	
	List<?> getAllSizeByProductId(Long id); // Lấy tất cả size của một sản phẩm
	
}
