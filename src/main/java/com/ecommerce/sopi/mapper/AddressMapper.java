package com.ecommerce.sopi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.ecommerce.sopi.DTO.request.AddressResquest;
import com.ecommerce.sopi.DTO.response.AddressResponse;
import com.ecommerce.sopi.entity.AddressEntity;

@Mapper(componentModel = "spring")
public interface AddressMapper {
	AddressResponse toAddressResponse(AddressEntity addressEntity);
	AddressResquest toAddressResquest(AddressResponse addressResponse);
	void toAddressEntity(AddressResquest resquest,@MappingTarget AddressEntity addressEntity);
}
