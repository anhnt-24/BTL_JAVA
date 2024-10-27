package com.ecommerce.sopi.mapper;

import org.mapstruct.Mapper;

import com.ecommerce.sopi.DTO.request.CartRequest;
import com.ecommerce.sopi.DTO.response.CartResponse;
import com.ecommerce.sopi.entity.CartItemEntity;

@Mapper(componentModel = "spring")
public interface CartMapper {
	CartResponse toCartResponse(CartItemEntity cartItemEntity);
	CartItemEntity toCartItemEntity(CartRequest cartRequest);

}
