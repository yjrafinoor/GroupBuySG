package com.groupbuysg.portal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.groupbuysg.portal.valueobject.ResponseObject;
import com.groupbuysg.portal.valueobject.Listing;
import com.groupbuysg.portal.valueobject.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PortalApiService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	/*
	public Listing getListing() {
		log.info("Inside getListing method of PortalApiService");
		return restTemplate.getForObject("http://LISTING-SERVICE/listings/" + "1"
				, Listing.class);
	}*/
	
	public User getUserList() {
		log.info("Inside getUserList method of PortalApiService");
		List<User> users = new ArrayList<User>();
		User user =  restTemplate.getForObject("http://USER-SERVICE/users/"
				, User.class);
		log.info("Heee User: "+ user);
		return user;
	}
	
	
	public User signUp(@RequestBody User user) {
		log.info("Inside signUp method of PortalApiService");
		ResponseObject obj = new ResponseObject();
		
		user = restTemplate.getForObject("http://USER-SERVICE/signup/"
				, User.class);
		
		obj.setUser(user);
		
		return user;
	}
	
	

}
