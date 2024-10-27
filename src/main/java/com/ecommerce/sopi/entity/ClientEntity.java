package com.ecommerce.sopi.entity;

import java.util.Date;

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
@Table(name = "client")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientEntity {
	@Id
    @Temporal(TemporalType.DATE)  
    private Date date; 
	Long total;
	Long visit;
	
}
