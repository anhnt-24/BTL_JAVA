package com.ecommerce.sopi.service;

import java.util.List;

import com.ecommerce.sopi.DTO.request.CartRequest;
import com.ecommerce.sopi.DTO.response.PaymentResponse;

public interface PaymentServiceInterface {
	PaymentResponse proccessMono(CartRequest request) ; // Xử lí khi đặt hàng trực tiếp không qua giỏ hàng
	
	PaymentResponse proccessMulti(List<Long> cartId,Long total); // Xử lí khi đặt hàng qua giỏ hàng
	
	PaymentResponse applyDiscount(Long preDiscount,Long discount,PaymentResponse paymentResponse); // Áp dụng mã giảm giá 
}
