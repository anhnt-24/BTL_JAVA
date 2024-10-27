package com.ecommerce.sopi.mapper;

import org.mapstruct.Mapper;

import com.ecommerce.sopi.DTO.ADMIN.response.ADMINSize_ColorResponse;
import com.ecommerce.sopi.DTO.response.Size_ColorResponse;
import com.ecommerce.sopi.entity.Size_ColorEntity;

@Mapper(componentModel = "spring")
public interface Size_ColorMapper {
	Size_ColorResponse toSize_ColorResponse(Size_ColorEntity size_ColorEntity);
	ADMINSize_ColorResponse toAdminSize_ColorResponse(Size_ColorEntity size_ColorEntity);
}
