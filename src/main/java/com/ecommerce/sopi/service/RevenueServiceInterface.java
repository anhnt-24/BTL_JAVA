package com.ecommerce.sopi.service;

import java.util.List;

import com.ecommerce.sopi.DTO.ADMIN.response.RevenueResponse;
import com.ecommerce.sopi.entity.RevenueEntity;

public interface RevenueServiceInterface {
	
	RevenueEntity getToday(); // Lấy ra thông tin về doanh thu hôm nay
	
	RevenueEntity getPrevious(); // Lấy ra thông tin về doanh thu hôm trước
	
	void create(); // Tạo mới một thông tin về doanh thu
	
	void update(Long total,Long sales); // Cập nhật doanh thu
	
	List<String []> getRevenueResponse(); // Thông tin doanh thu hiển thị trong trang admin
}
