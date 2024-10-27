package com.ecommerce.sopi.service;

import java.util.List;

import com.ecommerce.sopi.DTO.response.ColorResponse;
import com.ecommerce.sopi.entity.ColorEntity;

public interface ColorServiceInterface {
	List<ColorEntity> getAll(); // Lấy ra tất cả màu sắc sản phẩm 
	
	List<?> getAllColor(); //  Lấy ra tất cả màu sắc sản phẩm
	
	ColorResponse getColorById(Long id); // Lấy ra màu sắc theo id
	
	List<?> getAllColorByProductId(Long id); // Lấy ra tất cả màu sắc theo id của sản phẩm
	
}
