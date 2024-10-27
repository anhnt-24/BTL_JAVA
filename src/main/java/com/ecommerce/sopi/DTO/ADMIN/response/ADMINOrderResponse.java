package com.ecommerce.sopi.DTO.ADMIN.response;

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
public class ADMINOrderResponse {
	Long id;
	String code;
	Long userid;
	Long orderitem;
	Long discount;
	Long deliveryFee;
	Long total;
}
