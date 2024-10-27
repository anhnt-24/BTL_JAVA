package com.ecommerce.sopi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommerce.sopi.DTO.response.PaymentResponse;
import com.ecommerce.sopi.entity.UserEntity;

public interface OrderServiceInterface {
	
	Page<?> getAllOrder(int offset,String field,String order); // Lấy ra tất cả thông tin thông tin của 1 đơn hàng
	
	List<?> getAllOrderByUser(); // Lấy ra tất cả đơn hàng của một người dùng hiển thị trong mục đơn hàng
	
	void createOrder(PaymentResponse paymentResponse,Long addressId,Long discount); // Thêm mới một đơn hàng
	
	List<?> getOrderByCode(String code); // Lấy thông tin của đơn hàng bao gồm các đơn hàng con bởi mã của đơn hàng
}
