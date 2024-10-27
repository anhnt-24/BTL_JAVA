package com.ecommerce.sopi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.sopi.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	Optional<?> getUserById(Long id);
	Optional<UserEntity> findByUsername(String username);
	boolean existsUserByUsername(String username);
	boolean existsUserByEmail(String email);
	boolean existsUserByPhone(String phone);
	UserEntity findUserByEmail(String email);
	UserEntity findUserByPhone(String phone);
	Page<UserEntity> findAll(Pageable pageable);
}
