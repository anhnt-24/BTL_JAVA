package com.ecommerce.sopi.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.sopi.entity.OrderEntity;
import com.ecommerce.sopi.entity.OrderItemEntity;
import com.ecommerce.sopi.entity.StatusDelivery;


public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
	List<OrderItemEntity> findAllOrderItemByOrder(OrderEntity orderEntity,Sort sort);
	List<OrderItemEntity> findAllOrderItemByStatusAndOrder(StatusDelivery status,OrderEntity orderEntity);
	Page<OrderItemEntity> findAll(Pageable pageable);
	Long countByStatus(StatusDelivery status);
}
