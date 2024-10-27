package com.ecommerce.sopi.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.sopi.entity.RevenueEntity;

@Repository
public interface RevenueRepository extends JpaRepository<RevenueEntity, Date> {

}
