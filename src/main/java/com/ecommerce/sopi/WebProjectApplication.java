package com.ecommerce.sopi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ecommerce.sopi.DTO.request.RegisterRequest;
import com.ecommerce.sopi.DTO.request.UserRequest;
import com.ecommerce.sopi.entity.Roles;
import com.ecommerce.sopi.service.impl.UserService;

@SpringBootApplication 
@EnableScheduling 
public class WebProjectApplication implements ApplicationRunner{
	
	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(WebProjectApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// Khi chương trình khởi chạy thì kiểm tra xem csdl đã có tài khoản ADMIN chưa nếu chưa thì thêm vào csdl
		RegisterRequest registerRequest=new RegisterRequest();
		registerRequest.setEmail("anhnt204@gmail.com");
		registerRequest.setPassword("admin");
		registerRequest.setPhone("0389801058");
		registerRequest.setUsername("admin");
		userService.checkRegister(registerRequest, Roles.ADMIN);
	}

}
