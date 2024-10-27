package com.ecommerce.sopi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.sopi.entity.SliderEntity;

@Repository
public interface SliderRepository extends JpaRepository<SliderEntity, Long> {

}
