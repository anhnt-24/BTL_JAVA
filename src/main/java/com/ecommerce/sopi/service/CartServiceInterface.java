package com.ecommerce.sopi.service;

import java.util.List;

import com.ecommerce.sopi.DTO.request.CartRequest;
import com.ecommerce.sopi.DTO.response.CartResponse;
import com.ecommerce.sopi.entity.Size_ColorEntity;


public interface CartServiceInterface {
	
	long getCartItemCount(); // Lấy ra số lượng sản phẩm của người dùng xác định hiện có trong giỏ hàng
	
	CartResponse mapCartEntitytoCartReponse(Size_ColorEntity size_ColorEntity,Long quantity,Long total); // chuyển CartEntity thành CartResponse
	
	List<?> getALLCartItemByUser(); // Lấy ra tất cả các sản phẩm của một người dùng xác định hiện có trong giỏ hàng
	
	CartResponse getCartItemById(Long id); // Lấy CartResponse bằng id 
	
	List<?> getAllCartItemByListId(List<Long> list); // Lấy ra tất cả sản phẩm của giỏ hàng bằng LIST(id)
	
	void createCartItem(CartRequest request); // Thêm sản phẩm vào giỏ hàng
	
	void deleteCartItemById(Long id); // Xóa sản phẩm trong giỏ hàng bằng id
	
	void addCartItemQuantity(Long id, Long quantity);	// Thay đổi số lượng sản phẩm của 1 item trong giỏ hàng
	
	void deleteCartItemByListId(List<Long> list); // Xóa nhiều sản phẩm 
}
