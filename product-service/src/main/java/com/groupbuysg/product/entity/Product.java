package com.groupbuysg.product.entity;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long productId;
	private String productName;
	private String description;
	private String details;
	private String url;
	private String dueDate;
	private String category;
	private String status;
	private String firstQuantity;
	private String firstPrice;
	private String secondQuantity;
	private String secondPrice;
	private String thirdQuantity;
	private String thirdPrice;
	private String createdDate;
	private String closeDate;
	private long userId;
	

}
