package com.ecommerce.sopi.mapper;

import org.mapstruct.Mapper;

import com.ecommerce.sopi.DTO.ADMIN.response.ADMINProductResponse;
import com.ecommerce.sopi.DTO.response.ProductResponse;
import com.ecommerce.sopi.entity.ProductEntity;

@Mapper(componentModel = "spring")
public interface ProductMapper {
	ProductResponse toProductResponse(ProductEntity productEntity);
	ADMINProductResponse toAdminProductResponse(ProductEntity productEntity);
}
