package com.groupbuysg.portal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.groupbuysg.portal.valueobject.ResponseObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	public List<User> getUserList() {
		log.info("Inside getUserList method of PortalApiService");

		User[] userResponse =  restTemplate.getForObject("http://USER-SERVICE/users/"
				, User[].class);
		log.info(String.valueOf(userResponse.length));
		return Arrays.asList(userResponse);
	}
	
	
	public User signUp(@RequestBody User user) {
		log.info("Inside signUp method of PortalApiService");
		ResponseObject obj = new ResponseObject();
		
		user = restTemplate.getForObject("http://USER-SERVICE/signup/"
				, User.class);
		
		obj.setUser(user);
		
		return user;
	}


	public void saveUser(@RequestBody User user) {

		log.info("Inside saveUser method of PortalApiService");
		
		String userRequest ="";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		try{
		ObjectMapper objectMapper = new ObjectMapper();		
		userRequest = objectMapper.writeValueAsString(user);
		}
		catch(Exception e){
			log.info("fail Request: "+ userRequest);
		}
		HttpEntity<String> request = new HttpEntity<>(userRequest,headers);
				
		log.info("Request: "+ request);

		restTemplate.postForObject(
				"http://USER-SERVICE/users/create/",
				request,
				User.class);
		
	}

	public User logInViaGoogle(@RequestBody User user) {

		// TODO : issue unable to respond to output value of this function from frontend
		//should return valid user instance/profile of himself
		log.info("Inside logInViaGoogle method of PortalApiService");

		User[] userResponse =  restTemplate.getForObject("http://USER-SERVICE/users/"
				, User[].class);
		log.info(String.valueOf(userResponse.length));

		for(int i = 0 ; i < userResponse.length ; ++i)
			if(userResponse[i].getEmail().equals( user.getEmail() ) )
				return userResponse[i];

		return null;//new User();
	}
	

}
