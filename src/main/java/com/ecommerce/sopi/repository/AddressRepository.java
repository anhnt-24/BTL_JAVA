package com.ecommerce.sopi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.sopi.entity.AddressEntity;
import com.ecommerce.sopi.entity.UserEntity;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
	
	
	@Query("SELECT c FROM AddressEntity c WHERE c.isdefault = true and c.user = :user" )
    AddressEntity findDefaultAddressByUser(@Param("user") UserEntity user);
	
	@Query("SELECT c FROM AddressEntity c WHERE c.isdefault = false and c.user = :user" )
	List<AddressEntity> findAllNotDefaultAddressByUser(@Param("user") UserEntity user);
	
	AddressEntity findAllAddressByUser(UserEntity userEntity);
	
}
