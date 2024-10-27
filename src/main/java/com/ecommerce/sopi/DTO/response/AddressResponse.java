 package com.ecommerce.sopi.DTO.response;

import java.time.LocalDate;

import com.ecommerce.sopi.DTO.request.UserRequest;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class AddressResponse {
	Long id;
	String receiver;
	String phone;
	String province;
	String district;
	String commune;
	String id_province;
	String id_district;
	String id_commune;
	String detail;
	Boolean isdefault;
}
