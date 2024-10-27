package com.ecommerce.sopi.oauth2;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ecommerce.sopi.DTO.request.RegisterRequest;
import com.ecommerce.sopi.entity.Roles;
import com.ecommerce.sopi.service.impl.ClientService;
import com.ecommerce.sopi.service.impl.UserService;

// Đăng nhập với facebook đi qua đây
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	
	@Autowired
	private UserService userService;

	@Autowired
    private ClientService clientService;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2User oAuth2User=super.loadUser(userRequest);
		RegisterRequest registerRequest=new RegisterRequest();
        String id = oAuth2User.getName(); 
        registerRequest.setUsername(id);
		registerRequest.setPassword("default");
		userService.checkRegister(registerRequest,Roles.USER);
		clientService.updateVisit();
		return oAuth2User;
	}

	@Override
	public void setAttributesConverter(
			Converter<OAuth2UserRequest, Converter<Map<String, Object>, Map<String, Object>>> attributesConverter) {
		// TODO Auto-generated method stub
		super.setAttributesConverter(attributesConverter);
	}
	
	
   
}

