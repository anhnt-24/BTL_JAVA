package com.ecommerce.sopi.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.sopi.DTO.request.CartRequest;
import com.ecommerce.sopi.DTO.response.AddressResponse;
import com.ecommerce.sopi.DTO.response.CartResponse;
import com.ecommerce.sopi.DTO.response.PaymentResponse;
import com.ecommerce.sopi.entity.ColorEntity;
import com.ecommerce.sopi.entity.ProductEntity;
import com.ecommerce.sopi.entity.SizeEntity;
import com.ecommerce.sopi.entity.Size_ColorEntity;
import com.ecommerce.sopi.mapper.CartMapper;
import com.ecommerce.sopi.service.PaymentServiceInterface;

@Service
public class PaymentService implements PaymentServiceInterface {
	@Autowired
	private CartService cartService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private Size_ColorService size_ColorService;

	@Override
	public PaymentResponse proccessMono(CartRequest request) {
		PaymentResponse paymentResponse=new PaymentResponse();
		Size_ColorEntity size_ColorEntity=size_ColorService.getSize_ColorEntityById(request.getSize_color_id());
		Long quantity=request.getQuantity();
		Long total=request.getTotal();
		if(quantity>size_ColorEntity.getStock()) {
			quantity=size_ColorEntity.getStock();
			total=quantity*size_ColorEntity.getColor().getProduct().getPrice();
		}
		System.out.println(total);
		CartResponse cartResponse=cartService.mapCartEntitytoCartReponse(size_ColorEntity, quantity,null);
		List<CartResponse> list=new LinkedList<CartResponse>();
		list.add(cartResponse);
		paymentResponse.setList(list);
		paymentResponse.setPreTotal(cartResponse.total);
		paymentResponse.setTotal(cartResponse.total+20000);
		cartResponse.setTotal(cartResponse.getTotal()+20000);
		paymentResponse.setDeliveryFee((long)20000);
		return paymentResponse;
	}

	@Override
	public PaymentResponse proccessMulti(List<Long> cartId,Long total) {
		PaymentResponse paymentResponse=new PaymentResponse();
		List<CartResponse> cartResponses=cartService.getAllCartItemByListId(cartId);
		long deliveryFeeOnEachItem=(long)20000/cartResponses.size();
		for(int i=0;i<cartResponses.size();i++) {
			CartResponse cartResponse=cartResponses.get(i);
			cartResponse.setTotal(cartResponse.getTotal()+deliveryFeeOnEachItem);
			cartResponses.set(i, cartResponse);
		}
		paymentResponse.setList(cartResponses);
		paymentResponse.setPreTotal(total);
		paymentResponse.setTotal(total+20000);
		paymentResponse.setDeliveryFee((long)20000);
		return paymentResponse;
	}

	@Override
	public PaymentResponse applyDiscount(Long preDiscount, Long discount, PaymentResponse paymentResponse) {
		List<CartResponse> cartResponses=paymentResponse.getList();
		long discountOnEachItem=(long)discount/cartResponses.size()-(long)preDiscount/cartResponses.size();
		for(int i=0;i<cartResponses.size();i++) {
			cartResponses.get(i).setTotal(cartResponses.get(i).getTotal()-discountOnEachItem);
			
		}
		paymentResponse.setList(cartResponses);
		paymentResponse.setTotal(paymentResponse.getTotal()+preDiscount-discount);
		return paymentResponse;
	}

	
	
	
	
}
