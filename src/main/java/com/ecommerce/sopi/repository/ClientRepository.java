package com.ecommerce.sopi.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.sopi.entity.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, Date> {
	
}
