package com.ecommerce.sopi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.sopi.entity.ColorEntity;
import com.ecommerce.sopi.entity.ProductEntity;

@Repository
public interface ColorRepository extends JpaRepository<ColorEntity, Long>{
	List<ColorEntity> findAllColorByProduct(ProductEntity productEntity);
	@Query("SELECT c FROM ColorEntity c WHERE c.product.id = :product ")
	List<ColorEntity> findAllColorByProductId(@Param("product") Long id);
}
