package com.groupbuysg.user.valueobject;


import com.groupbuysg.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {
	
	private User user;
	private Listing listing;
	

}
