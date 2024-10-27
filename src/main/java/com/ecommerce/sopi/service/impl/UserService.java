package com.ecommerce.sopi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.sopi.DTO.ADMIN.response.ADMINUserResponse;
import com.ecommerce.sopi.DTO.request.RegisterRequest;
import com.ecommerce.sopi.DTO.request.UserRequest;
import com.ecommerce.sopi.DTO.response.UserResponse;
import com.ecommerce.sopi.entity.Roles;
import com.ecommerce.sopi.entity.UserEntity;
import com.ecommerce.sopi.globalvar.ClassToList;
import com.ecommerce.sopi.mapper.UserMapper;
import com.ecommerce.sopi.repository.UserRepository;
import com.ecommerce.sopi.service.UserServiceInterface;

@Service
public class UserService implements UserServiceInterface {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Long count() {
		return userRepository.count();
	}
	
	@Override
	public void saveUser(UserEntity userEntity) {
		userRepository.save(userEntity);
	}
	
	@Override
	public UserEntity getUserByPhone(String phone) {
		UserEntity userEntity=userRepository.findUserByPhone(phone);
		return userEntity;
	}
	
	@Override
	public UserEntity getUserByEmail(String email) {
		UserEntity userEntity=userRepository.findUserByEmail(email);
		return userEntity;
	}

	@Override
	public void setPassword(UserEntity userEntity,String password) {
		userEntity.setPassword(passwordEncoder.encode(password));
		saveUser(userEntity);
	}
	
	@Override
	public UserEntity getUserByUserName(String username) {
		UserEntity userEntity=userRepository.findByUsername(username).orElse(null);
		return userEntity;
	}
	
	@Override
	public UserEntity getAthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 String username=authentication.getName();
		 UserEntity userEntity=userRepository.findByUsername(username).orElse(null);
		 return userEntity;
	}
	
	@Override
	public Page<?> getAllUser(int offset,String field,String order) {
		Pageable pageable;
		if(order.compareTo("desc")==0) {
			pageable=PageRequest.of(offset, 50).withSort(Sort.by(field).descending());
		}else if(order.compareTo("asc")==0) {
			pageable=PageRequest.of(offset, 50).withSort(Sort.by(field).ascending());
		}else {
			pageable=PageRequest.of(offset, 50);
			
		}
		// TODO Auto-generated method stub
		List<List<?>> list=new ArrayList<>();
		Page<UserEntity> userPage=userRepository.findAll(pageable);
		List<ADMINUserResponse> list2=userPage.getContent().stream().map(x->userMapper.toAdminUserResponse(x)).collect(Collectors.toList());
		
		for(ADMINUserResponse x:list2) {
			list.add(ClassToList.getFieldValues(x));
		}
		Page<List<?>> pageAdminUser=new PageImpl<List<?>>(list,pageable,userPage.getTotalElements());
		return pageAdminUser;
	}

	@Override
	public UserEntity getUserById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).orElseThrow();
	}
	
	@Override
	public String checkRegister(RegisterRequest request,Roles role) {
		if(request.getUsername() != null && userRepository.existsUserByUsername(request.getUsername()))
			return "Tên đăng nhập đã tồn tại.";
		if(request.getEmail() != null &&  userRepository.existsUserByEmail(request.getEmail())) {
			return "Tên Email đã tồn tại.";
		}
		if(request.getPhone() != null &&  userRepository.existsUserByPhone(request.getPhone())) {
			return "Số điện thoại đã tồn tại.";
		}
		createUser(request,role);
		return null;
		
	}
	
	@Override
	public void createUser(RegisterRequest request,Roles role) {
		UserEntity user=UserEntity.builder()
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.phone(request.getPhone())
				.email(request.getEmail())
				.role(role)
				.total((long) 0)
				.orderSuccess((long) 0)
				.build();
		userRepository.save(user);
		
		
	}
	
	@Override
	public UserResponse getMyInfo() {
		
		 UserEntity userEntity=getAthenticatedUser();
		 return userMapper.toUserResponse(userEntity);
		 
		 
	}

	@Override
	public void updateUser(UserResponse response) {
		UserResponse user=getMyInfo();
		UserEntity userEntity=userRepository.findByUsername(user.getUsername()).orElseThrow();
		UserRequest userRequest=userMapper.toUserRequest(response);
		userMapper.updateUserEntity(userRequest,userEntity);
		userRepository.save(userEntity);
		
	}
	@Override
	public void updateUserTotal(Long total) {
		UserEntity userEntity=getAthenticatedUser();
		if(userEntity.getTotal()==null) {
			userEntity.setTotal(total);
			userEntity.setOrderSuccess((long)0);
			}else {
			userEntity.setTotal(total+userEntity.getTotal());
			userEntity.setOrderSuccess(userEntity.getOrderSuccess()+1);		}
		
		userRepository.save(userEntity);
		
	}

	
	

}
