package com.ecommerce.sopi.DTO.response;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class OrderItemResponse {
	Long id;
	Long orderId;
	String code;
	Long discount;
	String status;
	Date createdAt;
	Date deliveredAt;
	AddressResponse addressResponse;
	CartResponse cartResponse;
}
