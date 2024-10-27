package com.ecommerce.sopi.entity;


import lombok.Getter;

@Getter
public enum StatusDelivery {
	
	PENDING("Chờ xác nhận"),
	PROCESSING("Đang xử lí"),
	ONDELIVERY("Đang giao hàng"),
	DELIVERED("Giao thành công"),
	CANCELLED("Đã hủy");
	
	private final String message;
	
	StatusDelivery (String message) {
		this.message=message;
	}
	
	
	
}
