package com.ecommerce.sopi.mapper;

import org.mapstruct.Mapper;

import com.ecommerce.sopi.DTO.ADMIN.response.ADMINOrderResponse;
import com.ecommerce.sopi.entity.OrderEntity;

@Mapper(componentModel = "spring")
public interface OrderMapper {
	ADMINOrderResponse toAdminOrderResponse(OrderEntity orderEntity);
}
