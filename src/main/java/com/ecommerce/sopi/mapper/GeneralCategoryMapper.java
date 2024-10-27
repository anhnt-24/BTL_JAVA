package com.ecommerce.sopi.mapper;

import org.mapstruct.Mapper;

import com.ecommerce.sopi.DTO.response.GeneralCategoryResponse;
import com.ecommerce.sopi.entity.GeneralCategoryEntity;

@Mapper(componentModel = "spring")
public interface GeneralCategoryMapper {
	GeneralCategoryResponse toGeneralCategoryResponse(GeneralCategoryEntity generalCategoryEntity);
}
