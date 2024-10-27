package com.ecommerce.sopi.service;


import com.ecommerce.sopi.DTO.response.MessageResponse;
import com.ecommerce.sopi.entity.UserEntity;

import jakarta.servlet.http.HttpSession;

public interface AuthenticateServiceInterface {
	String setSomeAttr(HttpSession session,UserEntity userEntity,String link,String next); // Thêm các thuộc tính vào trong session vd: token, otp, ...
	
	String sendEmailChange(String otp,String linkAuthenticate); // Gửi email liên quan đến các thay đổi vd: mật khẩu, gmail, số điện thoại
	
	String sendEmailGetUserName(String otp,String username,String linkAuthenticate); // Gửi email khôi phục tài khoản;
	
	MessageResponse forgetPassword(HttpSession session, String username,String link); // Quên mật khẩu
	
	MessageResponse forgetUsername(HttpSession session, String email,String link); // Quên tài khoản
	
	MessageResponse comfirmOTP(HttpSession session,String OTP);  // Xác nhận OTP có đúng hay không
	
	MessageResponse sendChangePassword(HttpSession session,String link) ;  // Gửi email thay đổi mật khẩu
	
	MessageResponse sendChangeEmail(HttpSession session,String link); // Gửi email thay đổi Email
	
	MessageResponse sendChangePhone(HttpSession session,String link); // Gửi email thay đổi Số điện thoại
	
	MessageResponse changePassword(HttpSession session,String password,String token); // Kiểm tra token hợp lệ, Tiến hành thay đổi mật khẩu 
	
	MessageResponse changeEmail(HttpSession session,String email,String token); // Kiểm tra token hợp lệ, tiến hành thay đổi email
	
	MessageResponse changePhone(HttpSession session,String phone,String token); // Kiểm tra token hợp lê, tiến hành thay đổi số điện thoại
}
