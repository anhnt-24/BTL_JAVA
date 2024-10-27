package com.ecommerce.sopi.mapper;

import org.mapstruct.Mapper;

import com.ecommerce.sopi.DTO.response.CategoryResponse;
import com.ecommerce.sopi.entity.CategoryEntity;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
	CategoryResponse toCategoryResponse(CategoryEntity categoryEntity);
}
