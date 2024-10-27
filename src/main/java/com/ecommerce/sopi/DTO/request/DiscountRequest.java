package com.ecommerce.sopi.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountRequest {
	String code;
	Long turn;
	Long discount;
}
