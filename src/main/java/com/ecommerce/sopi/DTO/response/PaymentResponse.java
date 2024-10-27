package com.ecommerce.sopi.DTO.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
 @AllArgsConstructor
 @NoArgsConstructor
 @FieldDefaults(level = AccessLevel.PUBLIC)
public class PaymentResponse {
	List<CartResponse> list;
	Long total;
	Long deliveryFee;
	Long preTotal;
}
