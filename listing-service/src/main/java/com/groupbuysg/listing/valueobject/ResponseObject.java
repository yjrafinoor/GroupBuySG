package com.groupbuysg.listing.valueobject;

import com.groupbuysg.listing.entity.Listing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {

	private Listing listing;
	private Product product;
	private User user;
}
