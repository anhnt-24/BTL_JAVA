package com.ecommerce.sopi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.ecommerce.sopi.DTO.ADMIN.response.ADMINUserResponse;
import com.ecommerce.sopi.DTO.request.UserRequest;
import com.ecommerce.sopi.DTO.response.UserResponse;
import com.ecommerce.sopi.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserResponse toUserResponse(UserEntity userEntity);
	void  updateUserEntity(UserRequest request, @MappingTarget UserEntity userEntity);
	UserRequest toUserRequest(UserResponse response);
	ADMINUserResponse toAdminUserResponse(UserEntity userEntity);
	
}
