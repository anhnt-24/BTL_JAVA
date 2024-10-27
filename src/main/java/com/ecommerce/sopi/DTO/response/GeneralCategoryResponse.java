package com.ecommerce.sopi.DTO.response;

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
public class GeneralCategoryResponse {
	Long id;
	String name;
	String img;
	String imgName;
	List<CategoryResponse> category;
	
}
