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

import com.ecommerce.sopi.DTO.ADMIN.response.ADMINProductResponse;
import com.ecommerce.sopi.DTO.ADMIN.response.ADMINSize_ColorResponse;
import com.ecommerce.sopi.DTO.response.SizeResponse;
import com.ecommerce.sopi.DTO.response.Size_ColorResponse;
import com.ecommerce.sopi.entity.ProductEntity;
import com.ecommerce.sopi.entity.Size_ColorEntity;
import com.ecommerce.sopi.globalvar.ClassToList;
import com.ecommerce.sopi.mapper.Size_ColorMapper;
import com.ecommerce.sopi.repository.Size_ColorRepository;
import com.ecommerce.sopi.service.Size_ColorServiceInterface;

@Service
public class Size_ColorService implements Size_ColorServiceInterface{
	@Autowired
	private Size_ColorRepository size_ColorRepository;
	
	@Autowired
	private Size_ColorMapper size_ColorMapper;
	
	
	
	
	@Override
	public Page<?> getAllSize_Color(int offset,String field,String order) {
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
		Page<Size_ColorEntity> productPage=size_ColorRepository.findAll(pageable);
		List<ADMINSize_ColorResponse> list2=productPage.getContent().stream().map(x->size_ColorMapper.toAdminSize_ColorResponse(x)).collect(Collectors.toList());
		List<Size_ColorEntity> sizecolorList=productPage.getContent();
		for(int i=0;i<sizecolorList.size();i++) {
			list2.get(i).setColorSTR(sizecolorList.get(i).getColor().getColor());
			list2.get(i).setSizeSTR(sizecolorList.get(i).getSize().getSize());
			list2.get(i).setProductId(sizecolorList.get(i).getColor().getProduct().getId());
			list2.get(i).setImg(sizecolorList.get(i).getColor().getImg());
			list2.get(i).setName(sizecolorList.get(i).getColor().getProduct().getName());
		}
		
		for(ADMINSize_ColorResponse x:list2) {
			list.add(ClassToList.getFieldValues(x));
		}
		Page<List<?>> page=new PageImpl<List<?>>(list,pageable,productPage.getTotalElements());
		return page;
	}




	@Override
	public Size_ColorResponse getSize_ColorResponseByMTM(Long sizeId, Long colorId) {
		// TODO Auto-generated method stub
		Size_ColorEntity size_ColorEntity=size_ColorRepository.findSize_ColorByMTM(sizeId, colorId);
		Size_ColorResponse size_ColorResponse=size_ColorMapper.toSize_ColorResponse(size_ColorEntity);
		size_ColorResponse.setColorId(size_ColorEntity.getColor().getId());
		size_ColorResponse.setSizeId(size_ColorEntity.getSize().getId());
		return size_ColorResponse;
	}
	
	@Override
	public Size_ColorEntity getSize_ColorEntityById(Long id) {
		return size_ColorRepository.findById(id).orElseThrow();
	}
	@Override
	public List<Size_ColorEntity> getAll(){
		return size_ColorRepository.findAll();
	}
	@Override
	public void updateSold(Size_ColorEntity size_ColorEntity,Long quantity) {
		size_ColorEntity.setSold(size_ColorEntity.getSold()+quantity);
		size_ColorEntity.setStock(size_ColorEntity.getStock()-quantity);
		size_ColorRepository.save(size_ColorEntity);
	}
	
	
}
