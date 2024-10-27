package com.ecommerce.sopi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.sopi.entity.Size_ColorEntity;

@Repository
public interface Size_ColorRepository  extends JpaRepository<Size_ColorEntity, Long>{
	
	@Query("SELECT sc FROM Size_ColorEntity sc WHERE sc.color.id = :color AND sc.size.id = :size")
	Size_ColorEntity findSize_ColorByMTM(@Param("size") Long sizeId,@Param("color") Long colorId);
	Page<Size_ColorEntity> findAll(Pageable pageable);
}
