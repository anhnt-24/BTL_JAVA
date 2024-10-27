package com.ecommerce.sopi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.sopi.DTO.response.SizeResponse;
import com.ecommerce.sopi.mapper.SizeMapper;
import com.ecommerce.sopi.repository.SizeRepository;
import com.ecommerce.sopi.service.SizeServiceInterface;

@Service
public class SizeService implements SizeServiceInterface {
	
	@Autowired
	private SizeRepository sizeRepository;
	
	@Autowired
	private SizeMapper sizeMapper;
	


	@Override
	public List<SizeResponse> getAllSizeByProductId(Long id) {
		// TODO Auto-generated method stub
		List<SizeResponse> list=sizeRepository.findAllSizeByProductId(id).stream().map(x->sizeMapper.toSizeResponse(x)).collect(Collectors.toList());;
		return  list;
	}

}
