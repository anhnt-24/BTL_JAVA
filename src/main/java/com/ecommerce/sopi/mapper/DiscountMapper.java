package com.ecommerce.sopi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.ecommerce.sopi.DTO.ADMIN.response.ADMINDiscountResponse;
import com.ecommerce.sopi.DTO.request.DiscountRequest;
import com.ecommerce.sopi.DTO.response.DiscountResponse;
import com.ecommerce.sopi.entity.DiscountEntity;

import groovy.lang.DelegatesTo.Target;

@Mapper(componentModel = "spring")
public interface DiscountMapper {
	DiscountResponse toDiscountResponse(DiscountEntity discountEntity);
	ADMINDiscountResponse toAdminDiscountResponse(DiscountEntity discountEntity);
	void updateDiscount(DiscountRequest discountRequest,@MappingTarget DiscountEntity discountEntity);
}
