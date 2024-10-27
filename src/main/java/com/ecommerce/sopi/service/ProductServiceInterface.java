package com.ecommerce.sopi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ecommerce.sopi.DTO.response.ProductResponse;
import com.ecommerce.sopi.entity.ProductEntity;
import com.ecommerce.sopi.entity.Size_ColorEntity;

public interface ProductServiceInterface {
	
	void updateProductSoldMono(Size_ColorEntity size_ColorEntity,Long quantity); // Cập nhật số lượt bán của sản phẩm theo mỗi lượt đặt hàng
	
	void updateProductSoldMultil(); // Cập nhật số lượng bán của sản phẩm sau khi import vào csdl (nút cập nhật trong mục sản phẩm admin site)
	
	Page<?> findProductByName(String name,int offset,String field,String order); // Chức năng tìm kiếm theo tên sản phẩm
	
	List<?> getProductForDashboard(); // Lấy ra các thông tin về sản phẩm cho trang dashboard
	
	Page<?> getAllProduct(int offset,String field,String order); // Lấy ra các thông tin về sản phẩm cho mục Sản phẩm trong admin site
	
	ProductResponse getProductByIdDetail(Long id); // Lấy ra thông tin về sản phẩm để hiện thị sản phẩm có những màu, size nào 
	
	ProductResponse getProductById(Long id); // Lấy ra sản phẩm bằng id
	
	List<?> getAllProductByCategoryId(Long id);  // Lấy ra tất cả sản phẩm bởi Category_id
	
	List<ProductEntity> getAll(); // Lây ra tất cả các sản phẩm
}
