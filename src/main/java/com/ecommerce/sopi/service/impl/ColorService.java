package com.ecommerce.sopi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.sopi.DTO.response.ColorResponse;
import com.ecommerce.sopi.entity.ColorEntity;
import com.ecommerce.sopi.mapper.ColorMapper;
import com.ecommerce.sopi.repository.ColorRepository;
import com.ecommerce.sopi.service.ColorServiceInterface;

@Service
public class ColorService  implements ColorServiceInterface{
	
	@Autowired
	private ColorRepository colorRepository;
	
	@Autowired
	private ColorMapper colorMapper;
	
	@Override
	public List<ColorEntity> getAll() {
		// TODO Auto-generated method stub
		return colorRepository.findAll();
	}
	@Override
	public List<ColorResponse> getAllColor() {
		// TODO Auto-generated method stub
		return colorRepository.findAll().stream().map(x->colorMapper.toColorResponse(x)).collect(Collectors.toList());
	}

	@Override
	public ColorResponse getColorById(Long id) {
		// TODO Auto-generated method stub
		return colorMapper.toColorResponse(colorRepository.findById(id).orElseThrow());
	}

	@Override
	public List<ColorResponse> getAllColorByProductId(Long id) {
		// TODO Auto-generated method stub
		return colorRepository.findAllColorByProductId(id).stream().map(x->colorMapper.toColorResponse(x)).collect(Collectors.toList());
	}

}
