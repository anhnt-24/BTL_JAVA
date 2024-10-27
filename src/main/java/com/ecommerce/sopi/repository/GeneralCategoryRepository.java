package com.ecommerce.sopi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.sopi.entity.GeneralCategoryEntity;

@Repository
public interface GeneralCategoryRepository extends JpaRepository<GeneralCategoryEntity, Long> {

}
