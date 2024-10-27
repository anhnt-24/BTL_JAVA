package com.ecommerce.sopi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.sopi.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
	
	@Query("SELECT c FROM CategoryEntity c WHERE c.generalCategory.id= :id")
	List<CategoryEntity> findALlCategoryByGeneralCategoryId(@Param("id") Long id);
}
