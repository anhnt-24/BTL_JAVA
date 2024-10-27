package com.ecommerce.sopi.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResquest {
	String receiver;
	String phone;
	String province;
	String district;
	String commune;
	String id_province;
	String id_district;
	String id_commune;
	String detail;
}
