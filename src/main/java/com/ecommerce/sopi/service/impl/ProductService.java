package com.ecommerce.sopi.service.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.sopi.DTO.ADMIN.response.ADMINProductResponse;
import com.ecommerce.sopi.DTO.ADMIN.response.ADMINUserResponse;
import com.ecommerce.sopi.DTO.response.ColorResponse;
import com.ecommerce.sopi.DTO.response.ProductResponse;
import com.ecommerce.sopi.DTO.response.SizeResponse;
import com.ecommerce.sopi.DTO.response.Size_ColorResponse;
import com.ecommerce.sopi.entity.ColorEntity;
import com.ecommerce.sopi.entity.ProductEntity;
import com.ecommerce.sopi.entity.Size_ColorEntity;
import com.ecommerce.sopi.entity.UserEntity;
import com.ecommerce.sopi.globalvar.ClassToList;
import com.ecommerce.sopi.mapper.ProductMapper;
import com.ecommerce.sopi.repository.ProductRepository;
import com.ecommerce.sopi.service.ProductServiceInterface;

@Service
public class ProductService implements ProductServiceInterface {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SizeService sizeService;
	
	@Autowired
	private ColorService colorService;
	
	@Autowired
	private Size_ColorService size_ColorService;
	
	@Autowired
	private ProductMapper productMapper;
	
	
	public static String formatCurrency(long amount) {
		NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
		numberFormat.setMinimumFractionDigits(0); 
		numberFormat.setMaximumFractionDigits(0); 
		return numberFormat.format(amount) + "đ"; 
	}
	
	@Override
	public void updateProductSoldMono(Size_ColorEntity size_ColorEntity,Long quantity) {
		ProductEntity productEntity=size_ColorEntity.getColor().getProduct();
		size_ColorService.updateSold(size_ColorEntity, quantity);
		productEntity.setSold(productEntity.getSold()+quantity);
		productRepository.save(productEntity);
	}
	
	@Override
	public void updateProductSoldMultil() {
		for(ProductEntity x:getAll()) {
			x.setSold((long)0);
			x.setColorQuantity((long)0);
			productRepository.save(x);
			
		}
		for(ColorEntity x: colorService.getAll()) {
			ProductEntity productEntity =x.getProduct();
			productEntity.setColorQuantity(productEntity.getColorQuantity()+1);
			productRepository.save(productEntity);
			
		}
		for(Size_ColorEntity x:size_ColorService.getAll()) {
			ProductEntity productEntity =x.getColor().getProduct();
			productEntity.setSold(productEntity.getSold()+x.getSold());
			productRepository.save(productEntity);
		}
		
	}
	
	@Override
	public Page<?> getAllProduct(int offset,String field,String order) {
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
		Page<ProductEntity> productPage=productRepository.findAll(pageable);
		List<ADMINProductResponse> list2=productPage.getContent().stream().map(x->productMapper.toAdminProductResponse(x)).collect(Collectors.toList());
		List<ProductEntity> productList=productPage.getContent();
		for(int i=0;i<productList.size();i++) {
			list2.get(i).setCategoryName(productList.get(i).getCategory().getName());
		}
		
		for(ADMINProductResponse x:list2) {
			list.add(ClassToList.getFieldValues(x));
		}
		Page<List<?>> page=new PageImpl<List<?>>(list,pageable,productPage.getTotalElements());
		return page;
	}
	
	@Override
	public ProductResponse getProductByIdDetail(Long id) {
		// TODO Auto-generated method stub
		List<SizeResponse> size= sizeService.getAllSizeByProductId(id);
		List<ColorResponse> color=colorService.getAllColorByProductId(id);
		ProductResponse productResponse=productMapper.toProductResponse(productRepository.findById(id).orElseThrow());
		productResponse.setPriceVnd(formatCurrency(productResponse.getPrice()));
		List<Size_ColorResponse> size_color=new LinkedList<Size_ColorResponse>();
		
		for(SizeResponse x: size) {
			for(ColorResponse y:color) {
				size_color.add(size_ColorService.getSize_ColorResponseByMTM(x.id, y.id));
			}
		
		}
		productResponse.setColor(color);
		productResponse.setSize(size);
		productResponse.setSize_color(size_color);
		
		return productResponse;
	}

	@Override
	public List<ProductResponse> getAllProductByCategoryId(Long id) {
		List<ProductResponse> productResponses= productRepository.findAllProductByCategoryId(id)
				.stream().map(x->productMapper.toProductResponse(x)).collect(Collectors.toList());
		List<ProductResponse> list=new LinkedList<ProductResponse>();
		for(ProductResponse x:productResponses) {
			x=getProductById(x.id);
			list.add(x);
		}
		
		// TODO Auto-generated method stub
		return list;
	}


	@Override
	public ProductResponse getProductById(Long id) {
		// TODO Auto-generated method stub
		ProductResponse productResponse=productMapper.toProductResponse(productRepository.findById(id).orElseThrow());
		List<ColorResponse> color=colorService.getAllColorByProductId(id);
		productResponse.setColor(color);
		productResponse.setPriceVnd(formatCurrency(productResponse.getPrice()));
		
		return productResponse;
	}
	
	@Override
	public Page<?> findProductByName(String name,int offset,String field,String order){
		
		Pageable pageable;
		if(order.compareTo("desc")==0)
			pageable=PageRequest.of(offset,8,Sort.by(field).descending());
		else if(order.compareTo("asc")==0) {
			pageable=PageRequest.of(offset,8,Sort.by(field).ascending());
		}else {
			pageable=PageRequest.of(offset,8);
		}
		Page<ProductEntity> products=productRepository.findProdcutByNameContainingIgnoreCase(name, pageable);
		List<ProductEntity> listProducts=products.getContent();
		List<ProductResponse> listProductsResponse=new ArrayList<>();
		for(ProductEntity x:listProducts) {
			listProductsResponse.add(getProductById(x.getId()));
		}
		
		Page<ProductResponse> productResponses=new PageImpl<ProductResponse>(listProductsResponse, pageable, products.getTotalElements());
		return productResponses;
	}
	
	@Override
	public List<?> getProductForDashboard() {
		String [] titles= {"Số lượng sản phẩm"};
		String [] units= {"Sản phẩm"};
		List<String> a=new ArrayList<>();
		a.add(String.valueOf(productRepository.count()));
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
	public List<ProductEntity> getAll(){
		return productRepository.findAll();
	}

}
