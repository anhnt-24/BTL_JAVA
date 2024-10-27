package com.ecommerce.sopi.DTO.response;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class ProductResponse {
	Long id;
	String name;
	Long price;
	String priceVnd;
	Long sold;
	List<SizeResponse> size;
	List<ColorResponse> color;
	List<Size_ColorResponse> size_color;
	
	
}
