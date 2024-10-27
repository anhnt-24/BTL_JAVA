package com.ecommerce.sopi.entity;

import java.security.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(unique = true)
	String username;
	
	String password;
	
	@Column(unique = true)
	String email;
	
	String name;
	
	@Enumerated(EnumType.STRING)
	Gender gender;
	
	@Column(unique = true)
	String phone;
	String img;
	LocalDate birth;
	
	Long total;
	Long orderSuccess;
	
	@Enumerated(EnumType.STRING)
	Roles role;
	
	@CreationTimestamp
	Date createdAt;
	@UpdateTimestamp
	Date updatedAt;
	@CurrentTimestamp
	Date lastLogin;
	
}
