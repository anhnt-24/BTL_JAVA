package com.ecommerce.sopi.mapper;

import org.mapstruct.Mapper;

import com.ecommerce.sopi.DTO.response.SizeResponse;
import com.ecommerce.sopi.entity.SizeEntity;

@Mapper(componentModel = "spring")
public interface SizeMapper {
	SizeResponse toSizeResponse(SizeEntity sizeEntity);
}
