package com.groupbuysg.product.valueobject;

import com.groupbuysg.product.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {

	private Product product;
	private Listing listing;
	private User user;
}
