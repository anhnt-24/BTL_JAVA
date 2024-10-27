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
import org.springframework.stereotype.Service;

import com.ecommerce.sopi.DTO.ADMIN.response.ADMINOrderItemResponse;
import com.ecommerce.sopi.DTO.ADMIN.response.ADMINOrderResponse;
import com.ecommerce.sopi.DTO.response.CartResponse;
import com.ecommerce.sopi.DTO.response.PaymentResponse;
import com.ecommerce.sopi.entity.AddressEntity;
import com.ecommerce.sopi.entity.OrderEntity;
import com.ecommerce.sopi.entity.OrderItemEntity;
import com.ecommerce.sopi.entity.UserEntity;
import com.ecommerce.sopi.globalvar.ClassToList;
import com.ecommerce.sopi.globalvar.CodeGenerator;
import com.ecommerce.sopi.mapper.OrderMapper;
import com.ecommerce.sopi.repository.OrderRepository;
import com.ecommerce.sopi.service.OrderServiceInterface;

@Service
public class OrderService implements OrderServiceInterface{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private AddressService addressService;

	@Autowired
	private MessageService messageService;
	
	@Override
	public Page<?> getAllOrder(int offset,String field,String order) {

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
		Page<OrderEntity> orderItemPage=orderRepository.findAll(pageable);
		List<ADMINOrderResponse>list2=orderItemPage.getContent().stream().map(x->orderMapper.toAdminOrderResponse(x)).collect(Collectors.toList());
		List<OrderEntity> orderEntities=orderItemPage.getContent();
		for(int i=0;i<orderEntities.size();i++) {
			list2.get(i).setUserid(orderEntities.get(i).getUser().getId());
			list2.get(i).setOrderitem(orderEntities.get(i).getOrderItem());
			
		}
		for(ADMINOrderResponse x:list2) {
			list.add(ClassToList.getFieldValues(x));
		}
		Page<List<?>> page=new PageImpl<List<?>>(list,pageable,orderItemPage.getTotalElements());
		
		return page;
		
		
	}

	@Override
	public List<?> getAllOrderByUser() {
		Sort sort=Sort.by("createdAt").descending();	
		List<OrderEntity> orderEntities=orderRepository.findAllOrderByUser(userService.getAthenticatedUser(),sort);
		return orderItemService.getAllOrderByStatus(orderEntities);
	}



	@Override
	public void createOrder(PaymentResponse paymentResponse,Long addressId,Long discount) {
		UserEntity userEntity=userService.getAthenticatedUser();
		// TODO Auto-generated method stub

		
		OrderEntity orderEntity=new OrderEntity();
		orderEntity.setDiscount((long)0);
		orderEntity.setTotal(paymentResponse.total);
		orderEntity.setDeliveryFee(paymentResponse.deliveryFee);
		orderEntity.setUser(userEntity);
		orderEntity.setOrderItem((long)paymentResponse.list.size());		
		orderRepository.save(orderEntity);
		AddressEntity addressEntity=addressService.getAddressEntityById(addressId);
		orderEntity.setCode(CodeGenerator.generateCode(userEntity.getId()));
		orderEntity.setDiscount(discount);
		for(CartResponse x:paymentResponse.list) {
			orderItemService.createOrderItem(orderEntity, x, addressEntity);
			
		}
		messageService.create("Có đơn hàng mới!", "Khách hàng " + userEntity.getUsername()+" "
				+ "vừa đặt một đơn hàng mới với mã đơn hàng là "+ orderEntity.getCode());
		
		
	}

	
	@Override
	public List<?> getOrderByCode(String code) {
		OrderEntity orderEntity=orderRepository.findOrderByCode(code);
		if(orderEntity == null) {
			return null;
		}
		List<OrderEntity> orderEntities=new ArrayList<OrderEntity>();
		
		orderEntities.add(orderEntity);
		return (List<?>) orderItemService.getAllOrderByStatus(orderEntities).get(0);
	}





}
