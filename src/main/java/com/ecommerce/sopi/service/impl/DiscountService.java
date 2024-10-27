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

import com.ecommerce.sopi.DTO.ADMIN.response.ADMINDiscountResponse;
import com.ecommerce.sopi.DTO.ADMIN.response.ADMINProductResponse;
import com.ecommerce.sopi.DTO.request.DiscountRequest;
import com.ecommerce.sopi.DTO.response.DiscountResponse;
import com.ecommerce.sopi.entity.DiscountEntity;
import com.ecommerce.sopi.entity.ProductEntity;
import com.ecommerce.sopi.globalvar.ClassToList;
import com.ecommerce.sopi.mapper.DiscountMapper;
import com.ecommerce.sopi.repository.DiscountRepository;
import com.ecommerce.sopi.service.DiscountInterface;

@Service
public class DiscountService implements DiscountInterface {
	
	@Autowired
	private DiscountRepository discountRepository;
	
	@Autowired
	private DiscountMapper discountMapper;

	@Override
	public DiscountResponse getDiscoutByCode(String code) {
	
		DiscountResponse discountResponse=discountMapper.toDiscountResponse(discountRepository.findDiscountByCode(code));
		return discountResponse;
	}

	@Override
	public Page<?> getAllDiscount(int offset, String field, String order) {
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
		Page<DiscountEntity> discountPage=discountRepository.findAll(pageable);
		List<ADMINDiscountResponse> list2=discountPage.getContent().stream().map(x->discountMapper.toAdminDiscountResponse(x)).collect(Collectors.toList());
		
		for(ADMINDiscountResponse x:list2) {
			List<String> classToList=ClassToList.getFieldValues(x);
			classToList.add("/admin/discount/delete/"+x.getId());
			list.add(classToList);
		}
		
		Page<List<?>> page=new PageImpl<List<?>>(list,pageable,discountPage.getTotalElements());
		return page;
	}
	@Override
	public List<?> getDiscountForDashboard() {
		String [] titles= {"Số lượng mã giảm giá"};
		String [] units= {"Mã"};
		List<String> a=new ArrayList<>();
		a.add(String.valueOf(discountRepository.count()));
		String [] data=a.toArray(new String [0]);
		String [] percent={"-0"};
		List<String []> l=new ArrayList<>();
		l.add(titles);
		l.add(data);
		l.add(units);
		l.add(percent);
		return l;
	}
	@Override
	public void delete(Long id) {
		discountRepository.deleteById(id);
	}
	@Override
	public String create(DiscountRequest discountRequest) {
		if(discountRepository.findDiscountByCode(discountRequest.getCode())!=null) {
			return "Mã giảm giá đã tồn tại.";
		}
		DiscountEntity discountEntity=new DiscountEntity();
		discountMapper.updateDiscount(discountRequest, discountEntity);
		discountRepository.save(discountEntity);
		return null;
	}
	public void useCode(String code) {
		DiscountEntity discountEntity=discountRepository.findDiscountByCode(code);
		discountEntity.setTurn(discountEntity.getTurn()-1);
		if(discountEntity.getTurn()==0) {
			discountRepository.delete(discountEntity);
		}else {
			discountRepository.save(discountEntity);
		}
	}

}
