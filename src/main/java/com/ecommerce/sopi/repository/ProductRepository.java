package com.ecommerce.sopi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.sopi.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	@Query("SELECT p FROM ProductEntity p WHERE p.category.id= :category")
	List<ProductEntity> findAllProductByCategoryId(@Param("category") Long id) ;
	
	Page<ProductEntity> findProdcutByNameContainingIgnoreCase(String name, Pageable pageable);
	Page<ProductEntity> findAll(Pageable pageable);
}
