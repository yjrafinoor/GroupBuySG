package com.groupbuysg.portal.valueobject;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {

	private Listing listing;
	private User user;
	//private List<User> users;
	private Product product;
	private Comment comment;
}
