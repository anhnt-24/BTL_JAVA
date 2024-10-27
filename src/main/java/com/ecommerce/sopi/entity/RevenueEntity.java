package com.ecommerce.sopi.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name = "revenue")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RevenueEntity {
	
	@Id
    @Temporal(TemporalType.DATE)  
	Date date; 
	
	Long total;
	Long todayTotal;
	Long sales;
	Long todaySales;
	
	@UpdateTimestamp
	Date updatedAt;
	
}
