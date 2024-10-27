package com.ecommerce.sopi.mapper;

import org.mapstruct.Mapper;

import com.ecommerce.sopi.DTO.response.ColorResponse;
import com.ecommerce.sopi.entity.ColorEntity;

@Mapper(componentModel = "spring")
public interface ColorMapper {
	ColorResponse toColorResponse(ColorEntity colorEntity);
}
