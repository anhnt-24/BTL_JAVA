package com.ecommerce.sopi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.sopi.entity.OrderEntity;
import com.ecommerce.sopi.entity.UserEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>{
	List<OrderEntity> findAllOrderByUser(UserEntity userEntity,Sort sort);
	OrderEntity findOrderByCode(String code);
	Page<OrderEntity> findAll(Pageable pageable);

}
