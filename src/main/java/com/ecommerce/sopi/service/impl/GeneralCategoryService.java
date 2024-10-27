package com.ecommerce.sopi.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.sopi.DTO.response.GeneralCategoryResponse;
import com.ecommerce.sopi.entity.GeneralCategoryEntity;
import com.ecommerce.sopi.mapper.GeneralCategoryMapper;
import com.ecommerce.sopi.repository.GeneralCategoryRepository;
import com.ecommerce.sopi.service.GeneralCategoryServiceInterface;

@Service
public class GeneralCategoryService implements GeneralCategoryServiceInterface{
	
	@Autowired
	private GeneralCategoryRepository generalCategoryRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private GeneralCategoryMapper generalCategoryMapper;
	
	@Override
	public List<GeneralCategoryResponse> getAllGeneralCategory() {
		// TODO Auto-generated method stub
		return generalCategoryRepository.findAll().stream().map(x->generalCategoryMapper.toGeneralCategoryResponse(x)).collect(Collectors.toList());
	}

	@Override
	public GeneralCategoryResponse getGeneralCategoryById(Long Id) {
		GeneralCategoryResponse generalCategoryResponse=generalCategoryMapper
				.toGeneralCategoryResponse(generalCategoryRepository.findById(Id).orElseThrow(null));
		generalCategoryResponse.setCategory(categoryService
				.getAllCategoryByGeneralId(generalCategoryResponse.id));
		
		
		return generalCategoryResponse ;
	}

	@Override
	public List<?> getAllGeneralCategoryDetail() {
		// TODO Auto-generated method stub
		List<GeneralCategoryResponse> generalCategoryResponses=getAllGeneralCategory();
		List<GeneralCategoryResponse> list=new LinkedList<GeneralCategoryResponse>();
		for(GeneralCategoryResponse x:generalCategoryResponses) {
			x=getGeneralCategoryById(x.id);
			list.add(x);
		}
		return list;
	}

}
