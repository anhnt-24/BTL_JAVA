package com.ecommerce.sopi.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.sopi.entity.CartItemEntity;
import com.ecommerce.sopi.entity.Size_ColorEntity;
import com.ecommerce.sopi.entity.UserEntity;


@Repository
public interface CartRepository extends JpaRepository<CartItemEntity, Long>{
	long countByUser(UserEntity userEntity);
	List<CartItemEntity> findAllCartItemByUser(UserEntity userEntity);
	@Query("select c from CartItemEntity c where c.user = :user and c.size_color = :sc ")
	CartItemEntity findCartItemEntityBySize_ColorAndUser(@Param("user")	UserEntity userEntity, @Param("sc") Size_ColorEntity size_ColorEntity);
}
