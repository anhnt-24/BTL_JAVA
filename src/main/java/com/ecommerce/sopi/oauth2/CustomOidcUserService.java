package com.ecommerce.sopi.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.ecommerce.sopi.DTO.request.RegisterRequest;
import com.ecommerce.sopi.entity.Roles;
import com.ecommerce.sopi.service.impl.ClientService;
import com.ecommerce.sopi.service.impl.UserService;

// Đăng nhập với Google đi qua đây
@Service
public class CustomOidcUserService extends OidcUserService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private ClientService clientService;
	
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
    	
        OidcUser oidcUser = super.loadUser(userRequest);
        RegisterRequest registerRequest=new RegisterRequest();
        String id = oidcUser.getName(); 
        registerRequest.setUsername(id);
		registerRequest.setPassword("default");
		userService.checkRegister(registerRequest,Roles.USER);
		clientService.updateVisit();
        return oidcUser;
    }
}
