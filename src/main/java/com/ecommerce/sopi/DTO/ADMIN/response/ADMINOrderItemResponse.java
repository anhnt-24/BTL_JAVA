package com.ecommerce.sopi.DTO.ADMIN.response;

import java.util.Date;

import com.ecommerce.sopi.DTO.response.AddressResponse;
import com.ecommerce.sopi.DTO.response.CartResponse;
import com.ecommerce.sopi.entity.Roles;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class ADMINOrderItemResponse {
	Long id;
	String code;
	Long productId;
	String color;
	String size;
	String status;
	String receiver;
	String phone;
	String detail;
	String commune;
	String district;
	String province;
	String quantity;
	String total;
	Date deliveredAt;
}
