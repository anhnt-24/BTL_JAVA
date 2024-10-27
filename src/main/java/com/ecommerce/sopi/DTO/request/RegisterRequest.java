package com.ecommerce.sopi.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	String username;
	String password;
	String confirm_password;
	String phone;
	String email;
}
