package com.ecommerce.sopi.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.sopi.DTO.response.CategoryResponse;
import com.ecommerce.sopi.mapper.CategoryMapper;
import com.ecommerce.sopi.repository.CategoryRepository;
import com.ecommerce.sopi.service.CategoryServiceInterface;

@Service
public class CategoryService implements CategoryServiceInterface {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Autowired 
	private ProductService productService;

	@Override
	public List<?> getAllCategory() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll().stream().map(x->categoryMapper.toCategoryResponse(x)).collect(Collectors.toList());
		
	}

	@Override
	public CategoryResponse getCategoryById(Long id) {
		// TODO Auto-generated method stub
		
		CategoryResponse categoryResponse=categoryMapper.toCategoryResponse(categoryRepository.findById(id).orElseThrow());
		categoryResponse.setProduct(productService.getAllProductByCategoryId(id));
		return categoryResponse;
	}

	@Override
	public List<CategoryResponse> getAllCategoryByGeneralId(Long id) {
		List<CategoryResponse> categoryResponses=categoryRepository.findALlCategoryByGeneralCategoryId(id).stream().map(x->categoryMapper.toCategoryResponse(x)).collect(Collectors.toList());
		List<CategoryResponse> list= new LinkedList<CategoryResponse>();
		for(CategoryResponse x: categoryResponses) {
			x=getCategoryById(x.id);
			list.add(x);
		}
		return list;
	}

}
