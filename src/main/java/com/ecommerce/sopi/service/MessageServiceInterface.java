package com.ecommerce.sopi.service;

import java.util.List;

public interface MessageServiceInterface {
	
	void create(String title,String content);  // Tạo mới một thông báo
	
	List<?> getMessageForDashboard(); // Hiển thị tất cả thông báo trong trang dashboard
}
