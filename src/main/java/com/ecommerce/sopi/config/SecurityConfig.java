package com.ecommerce.sopi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.ecommerce.sopi.oauth2.CustomOAuth2UserService;
import com.ecommerce.sopi.oauth2.CustomOidcUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	
	
	@Bean
	public DefaultOAuth2UserService defaultOAuth2UserService() {
		return new CustomOAuth2UserService();
	}
	
	@Bean
    public OidcUserService oidcUserService() {
        return new CustomOidcUserService();
    }

	
	@Bean
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
	 
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
    
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/css/**","/js/**","/uploads/**","/assets/**","/client/login?error=true","/admin/register/**","/register/**","/authenticate/**","/lookup/**").permitAll()
				.anyRequest().authenticated()
				
			)
		.oauth2Login(oauth2 -> oauth2
				.loginPage("/client/login")
				.userInfoEndpoint(x -> x.oidcUserService(this.oidcUserService())
						.userService(this.defaultOAuth2UserService()))
				.defaultSuccessUrl("/",true)
		);
		
		

		http.csrf(x->x.disable());
		return http.build();
	}
	
	// Cấu hình đăng nhập vào trang người dùng
	@Configuration
	@Order(2)
    public static class App2ConfigurationAdapter {

        @Bean
        public SecurityFilterChain filterChainApp2(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
            MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
            http.securityMatcher("/client/**")
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/client/**"))
                            .hasAnyAuthority("ROLE_USER","OAUTH2_USER","ROLE_ADMIN"))
                // log in
                .formLogin(httpSecurityFormLoginConfigurer ->
                        httpSecurityFormLoginConfigurer.loginPage("/client/login")
                                .loginProcessingUrl("/client/login")
                                .failureUrl("/client/login?error=true")
                                .defaultSuccessUrl("/")
                                .permitAll())
                				
                // logout
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer.logoutUrl("/client/logout")
                                .logoutSuccessUrl("/client/login")
                                .deleteCookies("JSESSIONID"))
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.accessDeniedPage("/client/logout"))
                .csrf(AbstractHttpConfigurer::disable);
            return http.build();
        }
    }
	
	// Cấu hình đăng nhập vào trang admin
	@Configuration
    @Order(1)
    public static class App1ConfigurationAdapter {
        @Bean
        public SecurityFilterChain filterChainApp1(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
            MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
            http.securityMatcher("/admin/**")
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/**")).hasAnyAuthority("ROLE_ADMIN"))
                // log in
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                httpSecurityExceptionHandlingConfigurer.accessDeniedPage("/client/login"))
                .formLogin(httpSecurityFormLoginConfigurer ->
                        httpSecurityFormLoginConfigurer.loginPage("/admin/login")
                        		.loginProcessingUrl("/admin/login")
                                .failureUrl("/admin/login?error=true")
                                .defaultSuccessUrl("/admin/dashboard")
                                .permitAll())
                
                // logout
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer.logoutUrl("/admin/logout")
                                .logoutSuccessUrl("/admin/login")
                                .deleteCookies("JSESSIONID"))
                
                .csrf(AbstractHttpConfigurer::disable);

            return http.build();
        }
    }
	
	   
	
}
