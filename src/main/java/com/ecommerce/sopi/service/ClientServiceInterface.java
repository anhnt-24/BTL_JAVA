package com.ecommerce.sopi.service;

import java.util.List;

import com.ecommerce.sopi.entity.ClientEntity;

public interface ClientServiceInterface {
	ClientEntity getToday(); // Lấy ra thông tin của khách hàng ngày hôm nay 
	
	void create(); // Tạo mới thông tin về khách hàng ngày hôm nay
	
	void updateVisit(); // Cập nhật lượt đăng nhập website
	
	List<String []> getClientResponse(); // Lấy ra tất cả các thông tin ngày hôm nay về khách hàng 
}
