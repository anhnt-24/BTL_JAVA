package com.ecommerce.sopi.DTO.ADMIN.response;

import org.springframework.stereotype.Controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class ADMINProductResponse {
	Long  id;
	String name;
	Long price;
	String categoryName;
	Long colorQuantity;
	Long sold;
}
