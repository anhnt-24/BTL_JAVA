package com.ecommerce.sopi.DTO.ADMIN.response;

import com.ecommerce.sopi.DTO.response.CartResponse;
import com.ecommerce.sopi.entity.Roles;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class ADMINUserResponse {
	String id;
	String username;
	String name;
	String email;
	String phone;
	String gender;
	Roles role;
	Long total;
	Long orderSuccess;
	
}
