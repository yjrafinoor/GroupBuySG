package com.groupbuysg.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.groupbuysg.user.entity.User;
import com.groupbuysg.user.repository.UserRepository;
import com.groupbuysg.user.valueobject.Listing;
import com.groupbuysg.user.valueobject.ResponseObject;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	public User saveUser(User user) {
		log.info("Inside saveUser method of UserService");
		return userRepository.save(user);
	}

	public ResponseObject getUserWithListing(long userId) {
		log.info("Inside getUserWithListing method of UserService");
		ResponseObject obj=new ResponseObject();
		User user=userRepository.findByUserId(userId);
		
		Listing listing=
				restTemplate.getForObject("http://LISTING-SERVICE/listings/" + user.getListingId()
				, Listing.class);
				
		obj.setUser(user);
		obj.setListing(listing);
		
		return obj;
	}
}
