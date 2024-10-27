package com.ecommerce.sopi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommerce.sopi.DTO.response.CartResponse;
import com.ecommerce.sopi.DTO.response.OrderItemResponse;
import com.ecommerce.sopi.entity.AddressEntity;
import com.ecommerce.sopi.entity.OrderEntity;

public interface OrderItemServiceInterface {
	
	Page<?> getAllOrderItem(int offset,String field,String order); // Lấy ra tất cả đơn hàng con cho mục chi tiết đơn hàng trong admin
	
	List<?> getAllOderItemByOrder(OrderEntity orderEntity); // Lấy ra tất cả đơn hàng con của một đơn hàng to xác định
	
	List<?> getAllOrderByStatus(List<OrderEntity> orderEntities); // Phân loại orderItem theo trạng thái đơn hàng
	
	OrderItemResponse getOrderItemResponseById(Long id); // Lấy ra orderItem bằng id
	
	void createOrderItem(OrderEntity orderEntity, CartResponse cartResponse,AddressEntity addressEntity); // Tạo mới một orderItem
	
	void updateOrderItemStatus(Long id,String status); // Thay đổi trạng thái đơn hàng
	
	void returnOrderItem(Long id); // Người dùng xác nhận trả hàng
	
	void changeAddressOrderItem(Long orderItemId,Long addressId); // Thay đổi địa chỉ đơn hàng con
	
	List<?> getOrderItemForDashboard(); // Lấy ra các thông tin overview về các đơn hàng con cho trang dashboard
	
 }
