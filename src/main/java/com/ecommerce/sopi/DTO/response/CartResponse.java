package com.ecommerce.sopi.DTO.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class CartResponse {
	Long id;
	String name;
	String size;
	String color;
	String img;
	Long total;
	String totalVnd;
	Long price;
	String priceVnd;
	Long quantity;
	Long size_color_id;
	Long productId;
}
