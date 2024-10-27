package com.ecommerce.sopi.service;


import org.springframework.data.domain.Page;

import com.ecommerce.sopi.DTO.request.RegisterRequest;
import com.ecommerce.sopi.DTO.response.UserResponse;
import com.ecommerce.sopi.entity.Roles;
import com.ecommerce.sopi.entity.UserEntity;

public interface UserServiceInterface {
	Long count(); // Lấy số lượng khách hàng của website
	
	void saveUser(UserEntity userEntity); // Lưu thông tin của một khách hàng
	
	UserEntity getUserByPhone(String phone) ;  // Lấy thông tin của khách hàng theo số điện thoại
	
	UserEntity getUserByEmail(String email) ; // Lấy thông tin của khách hàng theo email
	
	UserEntity getUserByUserName(String username); // Lấy thông tin của khách hàng theo username
	
	void setPassword(UserEntity userEntity,String password) ; // Thay đổi mật khẩu của tài khoản
	
	UserEntity getAthenticatedUser(); // Lấy thông tin của khách hàng hiện tại đang đăng nhập kiểu entity
	
	Page<?> getAllUser(int offset,String field,String order); // Lấy ra thông tin của tất cả khách hàng cho mục khách hàng trong trang admin
	
	UserEntity getUserById(Long id); // Lấy thông tin khách hàng bởi id
	
	void createUser(RegisterRequest request,Roles rolse); // Tạo mới một tài khoản khách hàng
	
	UserResponse getMyInfo(); // Lấy thông tin của khách hàng hiện tại hiển thị trong mục tài khoản
	
	void updateUser(UserResponse response); // Cập nhật thông tin khách hàng
	
	String checkRegister(RegisterRequest request,Roles role); // Kiểm tra các điều kiện để có thể đăng kí được tài khoản

	void updateUserTotal(Long total); // Cập nhật thông tin chi tiêu của khách hàng
}
