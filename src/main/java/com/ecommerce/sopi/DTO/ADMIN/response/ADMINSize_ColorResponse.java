package com.ecommerce.sopi.DTO.ADMIN.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class ADMINSize_ColorResponse {
	Long id;
	Long productId;
	String name;
	String colorSTR;
	String sizeSTR;
	String img;
	Long sold;
	Long stock;
	
}