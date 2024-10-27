package com.ecommerce.sopi.DTO.response;

import groovy.transform.builder.Builder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class MessageResponse {
	String status;
	String message;
}
