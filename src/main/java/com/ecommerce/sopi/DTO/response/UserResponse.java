package com.ecommerce.sopi.DTO.response;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class UserResponse {
	String username;
	String name;
	String email;
	String phone;
	String gender;
	String img;
	LocalDate birth;
	
	
	
	
	public String formatPhoneNumber(String phoneNumber) {
		if(phoneNumber!=null) {
	        String visibleStart = phoneNumber.substring(0, 2);
	        String visibleEnd = phoneNumber.substring(phoneNumber.length() - 2);
	        String maskedMiddle = "*".repeat(phoneNumber.length() - 4);
	        return visibleStart + maskedMiddle + visibleEnd;
		}
	    return "";
	}
	 public String formatEmail(String email) {
		 if(email!=null) {
		        int atIndex = email.indexOf('@');
		        if (atIndex <= 1) {
		            return email; // If the email is too short, return it as is
		        }
	
		        String visibleStart = email.substring(0, 1);  // First character
		        String visibleEnd = email.substring(atIndex - 1, atIndex);  // Last character before '@'
		        String maskedMiddle = "*".repeat(atIndex - 2);  // Mask the middle part
	
		        return visibleStart + maskedMiddle + visibleEnd + email.substring(atIndex);
		 }
		 return "";
	    }

}
