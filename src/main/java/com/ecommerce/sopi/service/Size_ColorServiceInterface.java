package com.ecommerce.sopi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommerce.sopi.DTO.response.Size_ColorResponse;
import com.ecommerce.sopi.entity.Size_ColorEntity;

public interface Size_ColorServiceInterface {
	
	Page<?> getAllSize_Color(int offset,String field,String order); // Lấy ra tất cả thông tin cho mục chi tiết sản phẩm trong trang admin
	
	Size_ColorResponse getSize_ColorResponseByMTM(Long sizeId,Long colorId); // Lấy ra thông tin của size_color bởi sizeId và colorId theo quan hệ Many To Many 
	
	Size_ColorEntity getSize_ColorEntityById(Long id); // Lấy ra thông tin về size_color bởi id theo kiểu entity
	
	List<Size_ColorEntity> getAll(); // Lấy ra tất cả size_color theo kiểu entity
	
	void updateSold(Size_ColorEntity size_ColorEntity,Long quantity); // Cập nhật số lượng bán được và kho khi người dùng nhận hàng
	
}
