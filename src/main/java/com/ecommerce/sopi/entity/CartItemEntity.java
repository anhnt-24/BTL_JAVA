package com.ecommerce.sopi.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_items")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Long quantity;
	Long price;
	Long total;
	@CreationTimestamp
	Date createdAt;
	@UpdateTimestamp
	Date updatedAt;
	
	@ManyToOne
	@JoinColumn(name = "user_id") 
	@OnDelete(action =OnDeleteAction.CASCADE)
	UserEntity user;
	
	
	@ManyToOne
	@JoinColumn(name = "size_color_id") 
	@OnDelete(action =OnDeleteAction.CASCADE)
	Size_ColorEntity size_color;
}