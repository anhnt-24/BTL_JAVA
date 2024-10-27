package com.ecommerce.sopi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.sopi.entity.ProductEntity;
import com.ecommerce.sopi.entity.SizeEntity;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity, Long>{
	
	@Query("SELECT s from SizeEntity s WHERE s.product.id = :product ")
	List<SizeEntity> findAllSizeByProductId(@Param("product") Long id );
	
	List<SizeEntity> findAllSizeByProduct(ProductEntity productEntity);
}

