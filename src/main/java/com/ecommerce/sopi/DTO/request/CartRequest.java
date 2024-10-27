package com.ecommerce.sopi.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
	Long quantity;
	Long total;
	Long size_color_id;
}
