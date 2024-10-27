package com.ecommerce.sopi.entity;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "order_items")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Long quantity;
	Long total;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(40) default 'PENDING'")
	StatusDelivery status;
	
	String receiver;
	String phone;
	String province;
	String district;
	String commune;
	String detail;
	
	@UpdateTimestamp
	Date deliveredAt;
	
	@ManyToOne
	@JoinColumn(name="address_id")
	@OnDelete(action =OnDeleteAction.SET_NULL)
	AddressEntity address;
	
	@ManyToOne
	@JoinColumn(name = "order_id") 
	@OnDelete(action =OnDeleteAction.SET_NULL)
	OrderEntity order;
	
	@ManyToOne
	@JoinColumn(name = "size_color")
	@OnDelete(action =OnDeleteAction.CASCADE)
	Size_ColorEntity sizecolor;
}