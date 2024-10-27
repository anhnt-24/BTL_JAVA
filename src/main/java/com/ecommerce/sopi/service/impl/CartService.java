package com.ecommerce.sopi.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.sopi.DTO.request.CartRequest;
import com.ecommerce.sopi.DTO.response.CartResponse;
import com.ecommerce.sopi.entity.CartItemEntity;
import com.ecommerce.sopi.entity.ColorEntity;
import com.ecommerce.sopi.entity.ProductEntity;
import com.ecommerce.sopi.entity.SizeEntity;
import com.ecommerce.sopi.entity.Size_ColorEntity;
import com.ecommerce.sopi.entity.UserEntity;
import com.ecommerce.sopi.mapper.CartMapper;
import com.ecommerce.sopi.repository.CartRepository;
import com.ecommerce.sopi.service.CartServiceInterface;

@Service
public class CartService implements CartServiceInterface{

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartMapper cartMapper;
	
	
	@Autowired
	private Size_ColorService size_ColorService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public long getCartItemCount() {
		return cartRepository.countByUser(userService.getAthenticatedUser());
	}
	
	
	@Override
	public CartResponse mapCartEntitytoCartReponse(Size_ColorEntity size_ColorEntity,Long quantity,Long total) {
		ColorEntity colorEntity=size_ColorEntity.getColor();
		SizeEntity sizeEntity=size_ColorEntity.getSize();
		ProductEntity productEntity=sizeEntity.getProduct();
		CartResponse cartResponse=new CartResponse();
		cartResponse.setQuantity(quantity);
		if(total==null) {
			cartResponse.setTotal(quantity*productEntity.getPrice());
		}else {
			cartResponse.setTotal(total);
		}
		cartResponse.setColor(colorEntity.getColor());
		cartResponse.setImg(colorEntity.getImg());
		cartResponse.setName(productEntity.getName());
		cartResponse.setPrice(productEntity.getPrice());
		cartResponse.setSize(sizeEntity.getSize());
		cartResponse.setSize_color_id(size_ColorEntity.getId());
		cartResponse.setProductId(productEntity.getId());
		cartResponse.setPriceVnd(ProductService.formatCurrency(cartResponse.getPrice()));
		cartResponse.setTotalVnd(ProductService.formatCurrency(cartResponse.getTotal()));
		return cartResponse;
	}

	@Override
	public List<CartResponse> getALLCartItemByUser() {
		
		UserEntity userEntity=userService.getAthenticatedUser();
		List<CartResponse> list=new LinkedList<CartResponse>();
		List<CartItemEntity> cartItemEntity=cartRepository.findAllCartItemByUser(userEntity);
		for(CartItemEntity item:cartItemEntity) {
			if(item.getSize_color().getStock()==0) {
				cartRepository.delete(item);
				continue;
			}
			CartResponse cartResponse=mapCartEntitytoCartReponse(item.getSize_color(),item.getQuantity(),item.getTotal());
			cartResponse.setId(item.getId());
			list.add(cartResponse);
		}
		return list;
	}

	@Override
	public CartResponse getCartItemById(Long id) {
		// TODO Auto-generated method stub
		CartItemEntity item=cartRepository.findById(id).orElse(null);
		CartResponse cartResponse=mapCartEntitytoCartReponse(item.getSize_color(),item.getQuantity(),item.getTotal());
		cartResponse.setId(item.getId());
		return cartResponse;
		
	}

	@Override
	public void createCartItem(CartRequest request) {
		Size_ColorEntity size_ColorEntity=size_ColorService.getSize_ColorEntityById(request.getSize_color_id());
		UserEntity userEntity=userService.getAthenticatedUser();
		if(request.getQuantity()>size_ColorEntity.getStock()) {
			request.setQuantity(size_ColorEntity.getStock());
		}
		// TODO Auto-generated method stu
		CartItemEntity cartItemEntity=cartRepository.findCartItemEntityBySize_ColorAndUser(userEntity,size_ColorEntity);
		if(cartItemEntity!=null) {
			cartItemEntity.setQuantity(cartItemEntity.getQuantity()+request.getQuantity());
		}else {
			cartItemEntity=cartMapper.toCartItemEntity(request);
			cartItemEntity.setUser(userEntity);
			cartItemEntity.setSize_color(size_ColorEntity);
		}
		cartItemEntity.setTotal(cartItemEntity.getQuantity()*size_ColorEntity.getColor().getProduct().getPrice());
		cartRepository.save(cartItemEntity);
	}

	@Override
	public void deleteCartItemById(Long id) {
		cartRepository.deleteById(id);
	}

	@Override
	public void addCartItemQuantity(Long id, Long quantity) {
		// TODO Auto-generated method stub
		CartItemEntity cartItemEntity=cartRepository.findById(id).orElseThrow();
		if(quantity>cartItemEntity.getSize_color().getStock()) {
			quantity=cartItemEntity.getSize_color().getStock();
		}
		cartItemEntity.setQuantity(quantity);
		cartItemEntity.setTotal(cartItemEntity.getQuantity()*cartItemEntity.getSize_color().getColor().getProduct().getPrice());
		cartRepository.save(cartItemEntity);		
	}

	@Override
	public void deleteCartItemByListId(List<Long> list) {
		// TODO Auto-generated method stub
		for(long x:list) {
			deleteCartItemById(x);
		}
		
	}
	@Override
	public List<CartResponse> getAllCartItemByListId(List<Long> list) {
		List<CartResponse> cartResponses=new ArrayList<CartResponse>();
		for(Long x:list) {
			cartResponses.add(getCartItemById(x));
		}
		return cartResponses;
	}

	
	
	
	
}
