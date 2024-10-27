package com.ecommerce.sopi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.sopi.entity.DiscountEntity;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountEntity, Long> {
	DiscountEntity findDiscountByCode(String code);
	Page<DiscountEntity> findAll(Pageable pageable);
}
