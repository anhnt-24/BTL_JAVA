package com.ecommerce.sopi.entity;


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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "size_color")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Size_ColorEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Long stock;
	Long sold;
	
	@ManyToOne
	@JoinColumn(name = "size_id") 
	SizeEntity size;
	
	@ManyToOne
	@JoinColumn(name = "color_id") 
	ColorEntity color;
	
}
