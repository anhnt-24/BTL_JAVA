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
import com.ecommerce.sopi.DTO.ADMIN.response.ADMINUserResponse;
import com.ecommerce.sopi.DTO.response.CartResponse;
import com.ecommerce.sopi.DTO.response.OrderItemResponse;
import com.ecommerce.sopi.entity.AddressEntity;
import com.ecommerce.sopi.entity.OrderEntity;
import com.ecommerce.sopi.entity.OrderItemEntity;
import com.ecommerce.sopi.entity.RevenueEntity;
import com.ecommerce.sopi.entity.StatusDelivery;
import com.ecommerce.sopi.entity.UserEntity;
import com.ecommerce.sopi.globalvar.CalculateDistance;
import com.ecommerce.sopi.globalvar.ClassToList;
import com.ecommerce.sopi.globalvar.TextUtils;
import com.ecommerce.sopi.mapper.AddressMapper;
import com.ecommerce.sopi.mapper.OrderItemMapper;
import com.ecommerce.sopi.repository.OrderItemRepository;
import com.ecommerce.sopi.service.OrderItemServiceInterface;

@Service
public class OrderItemService implements OrderItemServiceInterface{
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private AddressMapper addressMapper;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	@Autowired
	private Size_ColorService size_ColorService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private RevenueService revenueService;

	@Override
	public Page<?> getAllOrderItem(int offset,String field,String order) {
		// TODO Auto-generated method stub
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
		Page<OrderItemEntity> orderItemPage=orderItemRepository.findAll(pageable);
		List<ADMINOrderItemResponse>list2=orderItemPage.getContent().stream().map(x->orderItemMapper.toAdminOrderResponse(x)).collect(Collectors.toList());
		List<OrderItemEntity> orderItemEntities=orderItemPage.getContent();
		for(int i=0;i<orderItemEntities.size();i++) {
			list2.get(i).setCode(orderItemEntities.get(i).getOrder().getCode());
			list2.get(i).setProductId(orderItemEntities.get(i).getSizecolor().getColor().getProduct().getId());
			list2.get(i).setColor(orderItemEntities.get(i).getSizecolor().getColor().getColor());
			list2.get(i).setSize(orderItemEntities.get(i).getSizecolor().getSize().getSize());
			
		}
		for(ADMINOrderItemResponse x:list2) {
			list.add(ClassToList.getFieldValues(x));
		}
		Page<List<?>> pageAdminUser=new PageImpl<List<?>>(list,pageable,orderItemPage.getTotalElements());
		return pageAdminUser;
	}

	@Override
	public List<?> getAllOderItemByOrder(OrderEntity orderEntity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<?> getAllOrderByStatus(List<OrderEntity> orderEntities) {
		// TODO Auto-gene
		Sort sort=Sort.by("deliveredAt").descending();		
		List<OrderItemEntity> orderItemEntities=new ArrayList<OrderItemEntity>();
		for(OrderEntity x:orderEntities) {
			orderItemEntities.addAll(orderItemRepository.findAllOrderItemByOrder(x,sort));
		}
		List<List<?>> list=new ArrayList<>();
		List<OrderItemResponse> all=new ArrayList<>();
		for(StatusDelivery x: StatusDelivery.values()) {
			List<OrderItemResponse> orderItemResponses=new ArrayList<>(); 
			for (OrderItemEntity y:orderItemEntities) {
				if(x.compareTo(y.getStatus())==0) {
					OrderItemResponse orderItemResponse=new OrderItemResponse();
					orderItemResponse.setId(y.getId());
					orderItemResponse.setStatus(x.toString());
					orderItemResponse.setCartResponse(cartService.mapCartEntitytoCartReponse(y.getSizecolor(), y.getQuantity(), y.getTotal()));
					orderItemResponse.setCreatedAt(y.getOrder().getCreatedAt());
					orderItemResponse.setDeliveredAt(y.getDeliveredAt());
					orderItemResponse.setId(y.getId());
					orderItemResponse.setOrderId(y.getOrder().getId());
					orderItemResponse.setCode(y.getOrder().getCode());
					orderItemResponse.setDiscount(y.getOrder().getDiscount());
					orderItemResponse.setAddressResponse(orderItemMapper.toaddAddressResponse(y));
					orderItemResponses.add(orderItemResponse);
					all.add(orderItemResponse);
				}
				
			}
			list.add(orderItemResponses);
			
		
		}
		list.add(0,all);
		return list;
	}
	

	@Override
	public OrderItemResponse getOrderItemResponseById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createOrderItem(OrderEntity orderEntity, CartResponse cartResponse,AddressEntity addressEntity) {
		OrderItemEntity orderItemEntity=new OrderItemEntity();
		orderItemEntity.setAddress(addressEntity);	
		orderItemMapper.updateOrderItem(addressEntity, orderItemEntity);	
		orderItemEntity.setQuantity(cartResponse.getQuantity());
		orderItemEntity.setOrder(orderEntity);
		orderItemEntity.setTotal(cartResponse.total);
		orderItemEntity.setSizecolor(size_ColorService.getSize_ColorEntityById(cartResponse.size_color_id));
		orderItemEntity.setStatus(StatusDelivery.PENDING);
		orderItemRepository.save(orderItemEntity);
		
		productService.updateProductSoldMono(orderItemEntity.getSizecolor(),orderItemEntity.getQuantity());	
		
	}

	@Override
	public void updateOrderItemStatus(Long id, String status) {
		OrderItemEntity orderItemEntity=orderItemRepository.findById(id).orElse(null);
		
		OrderEntity orderEntity=orderItemEntity.getOrder();
		UserEntity userEntity=orderEntity.getUser();		
		orderItemEntity.setStatus(StatusDelivery.valueOf(status));
		orderItemMapper.updateOrderItem(orderItemEntity.getAddress(), orderItemEntity);
		if(status.compareTo("CANCELLED")==0) {
			messageService.create("Đơn hàng bị hủy!", "Khách hàng " + userEntity.getUsername()+" "
					+ "đã hủy đơn hàng "+orderItemEntity.getId() 
					+" với mã đơn hàng là "
					+ orderEntity.getCode() 		
			);
			productService.updateProductSoldMono(orderItemEntity.getSizecolor(),-orderItemEntity.getQuantity());	
			
		}
		if(status.compareTo("DELIVERED")==0) {
			userService.updateUserTotal(orderItemEntity.getTotal());
			revenueService.update(orderItemEntity.getTotal(), orderItemEntity.getQuantity());
			messageService.create("Có đơn hàng mới!", "Khách hàng " + userEntity.getUsername()+" "
					+ "vừa nhận đơn hàng "+orderItemEntity.getId() 
					+" với mã đơn hàng là "
					+ orderEntity.getCode() + orderEntity.getCode()
					+"\n" + "+"+TextUtils.formatCurrency(orderItemEntity.getTotal())
				
			);
			
		}
		if(status.compareTo("PROCESSING")==0) {
			messageService.create("Đơn hàng đang được xử lí!", "Bạn vừa chuyển đơn " 
					+orderItemEntity.getId() 
					+" với mã đơn hàng là "
					+ orderEntity.getCode()
					+" sang trạng thái đang xử lí." 
					
			);
					
		}
		if(status.compareTo("ONDELIVERY")==0) {
			messageService.create("Đơn hàng đang được giao!", "Bạn vừa chuyển đơn " 
					+orderItemEntity.getId() 
					+" với mã đơn hàng là "
					+ orderEntity.getCode()
					+" sang trạng thái đang giao hàng." 
					
			);
					
		}
		
		orderItemRepository.save(orderItemEntity);
	}
	
	@Override
	public void returnOrderItem(Long id) {
		OrderItemEntity orderItemEntity=orderItemRepository.findById(id).orElse(null);
		
		OrderEntity orderEntity=orderItemEntity.getOrder();
		UserEntity userEntity=orderEntity.getUser();		
		orderItemEntity.setStatus(StatusDelivery.valueOf("CANCELLED"));
		orderItemRepository.save(orderItemEntity);
		messageService.create("Có yêu cầu Trả hàng/Hoàn tiền!", "Khách hàng " + userEntity.getUsername()+" "
				+ "đã yêu cầu trả hàng "+orderItemEntity.getId() 
				+" với mã đơn hàng là "
				+ orderEntity.getCode() 
				+'\n'+"-"+TextUtils.formatCurrency(orderItemEntity.getTotal())
		);
		productService.updateProductSoldMono(orderItemEntity.getSizecolor(),-orderItemEntity.getQuantity());	
		userService.updateUserTotal(-orderItemEntity.getTotal());
		revenueService.update(-orderItemEntity.getTotal(), -orderItemEntity.getQuantity());
	}
	
	
	@Override
	public void changeAddressOrderItem(Long orderItemId, Long addressId) {
		OrderItemEntity orderItemEntity=orderItemRepository.findById(orderItemId).orElse(null);
		orderItemEntity.setAddress(addressService.getAddressEntityById(addressId));	
		orderItemMapper.updateOrderItem(addressService.getAddressEntityById(addressId), orderItemEntity);
		orderItemRepository.save(orderItemEntity);
	}
	
	@Override
	public List<?> getOrderItemForDashboard() {
		String [] titles= {"Tất cả","Đang chờ","Đang xử lí","Đang giao","Đã giao","Đã hủy"};
		String [] units= {"Đơn","Đơn","Đơn","Đơn","Đơn","Đơn"};
		List<String> a=new ArrayList<>();
		a.add(String.valueOf( orderItemRepository.count()));
		for(StatusDelivery x: StatusDelivery.values()) {
			a.add(String.valueOf(orderItemRepository.countByStatus(x)));
		}
		
		String [] data=a.toArray(new String[0]);		
		String [] percent={"-0","-0","-0","-0","-0","-0",};
		
		List<String []> l=new ArrayList<>();
		l.add(titles);
		l.add(data);
		l.add(units);
		l.add(percent);
		return l;
	}
	
	
	

	
	
}
