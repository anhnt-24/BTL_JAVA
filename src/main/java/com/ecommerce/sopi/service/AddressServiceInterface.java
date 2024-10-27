package com.ecommerce.sopi.service;

import java.util.List;

import com.ecommerce.sopi.DTO.request.AddressResquest;
import com.ecommerce.sopi.DTO.response.AddressResponse;
import com.ecommerce.sopi.entity.AddressEntity;

public interface AddressServiceInterface {
	List<?> getAllAddressByUser();  		// Lấy tất cả địa chỉ của một người dùng = defaultAddress + List<notDefaultAddress>
	
	AddressResponse getDefaultAddress();    // Lấy địa chỉ mặc định của người dùng xác định
	
	List<?> getNotDefaultAddress(); 		// Lấy tất cả địa chỉ không mặc định của người dùng xác định
	
	AddressResponse getAddressById(Long id); // Trả về địa chỉ kiểu response theo id dùng để hiển thị 
	
	AddressEntity getAddressEntityById(Long id); // Trả về địa chỉ kiểu entity theo id dùng để cập nhật
	
	void deleteAddressById(Long id); 		// Xóa địa chỉ bằng id
	
	void updateAddress(AddressResquest resquest,Long id); // Cập nhật địa chỉ bằng id
	
	void createAddress(AddressResquest resquest); // Thêm mới một địa chỉ bởi một người dùng xác định
	
	void setDefaultAddressById(Long id);	// Cập nhật mặc định cho một địa chỉ bằng id
	
}	
