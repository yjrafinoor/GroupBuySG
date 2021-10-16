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
import com.groupbuysg.portal.valueobject.Comment;
import com.groupbuysg.portal.valueobject.Listing;
import com.groupbuysg.portal.valueobject.Product;
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
<<<<<<< HEAD
	
	public User getUserById(long userId) {
		log.info("Inside getUserById method of PortalApiService");
		
		return restTemplate.getForObject("http://USER-SERVICE/users/list/"+userId, 
				User.class);
	}
	
	public void updateUser(@RequestBody User userDetails, long userId) {
		log.info("Inside updateUser method of PortalApiService");
		
		String userRequest ="";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		try{
		ObjectMapper objectMapper = new ObjectMapper();		
		userRequest = objectMapper.writeValueAsString(userDetails);
		}
		catch(Exception e){
			log.info("fail Request: "+ userRequest);
		}
		HttpEntity<String> request = new HttpEntity<>(userRequest,headers);
				
		log.info("Request: "+ request);

		restTemplate.postForObject(
				"http://USER-SERVICE/users/update/"+userId,
				request,
				User.class);
	}
	
	public void deleteUserById(long userId) {
		log.info("Inside deleteUserById method of PortalApiService");
		restTemplate.delete("http://USER-SERVICE/users/delete/"+userId,
				User.class);
	}
	
	public void requestLeader(long userId) {
		log.info("Inside requestLeader method of PortalApiService");
log.info("HEE requestLeaderService: "+userId);	
				
		String userRequest ="";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		try{
		//ObjectMapper objectMapper = new ObjectMapper();		
		//listingRequest = objectMapper.writeValueAsString(listingDetails);
		}
		catch(Exception e){
			log.info("fail Request: "+ userRequest);
		}
		HttpEntity<String> request = new HttpEntity<>(userRequest,headers);
				
		log.info("Request: "+ request);
		
		restTemplate.postForObject(
				"http://USER-SERVICE/users/requestLeader/"+userId,
				request,
				User.class);
	}
	
	public void adminReviewLeaderRequest(long userId, int code) {
		log.info("Inside adminReviewLeaderRequest method of PortalApiService");
		
		String userRequest ="";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		try{
		//ObjectMapper objectMapper = new ObjectMapper();		
		//listingRequest = objectMapper.writeValueAsString(listingDetails);
		}
		catch(Exception e){
			log.info("fail Request: "+ userRequest);
		}
		HttpEntity<String> request = new HttpEntity<>(userRequest,headers);
				
		log.info("Request: "+ request);
		
		if(code==1) {
			restTemplate.postForObject(
					"http://USER-SERVICE/users/reviewLeader/"+userId+"/"+1,
					request,
					User.class);
		}
		if(code==2) {
			restTemplate.postForObject(
					"http://USER-SERVICE/users/reviewLeader/"+userId+"/"+2,
					request,
					User.class);
		}
	}
	
	
	public List<Product> getProductList() {
		log.info("Inside getProductList method of PortalApiService");

		Product[] productResponse =  restTemplate.getForObject("http://PRODUCT-SERVICE/products/"
				, Product[].class);
		
		log.info(String.valueOf(productResponse.length));
		return Arrays.asList(productResponse);
	}
	
	
	public Product saveProduct(@RequestBody Product product, long userId) {
		log.info("Inside saveProduct method of PortalApiService");
		
		String produtRequest ="";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		try{
		ObjectMapper objectMapper = new ObjectMapper();		
		produtRequest = objectMapper.writeValueAsString(product);
		}
		catch(Exception e){
			log.info("fail Request: "+ produtRequest);
		}
		HttpEntity<String> request = new HttpEntity<>(produtRequest,headers);
				
		log.info("Request: "+ request);

		return restTemplate.postForObject(
				"http://PRODUCT-SERVICE/products/create/"+userId,
				request,
				Product.class);
	}
	
	public void updateProduct(@RequestBody Product productDetails, long productId) {
		log.info("Inside updateProduct method of PortalApiService");
		
		String produtRequest ="";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		try{
		ObjectMapper objectMapper = new ObjectMapper();		
		produtRequest = objectMapper.writeValueAsString(productDetails);
		}
		catch(Exception e){
			log.info("fail Request: "+ produtRequest);
		}
		HttpEntity<String> request = new HttpEntity<>(produtRequest,headers);
				
		log.info("Request: "+ request);

		restTemplate.postForObject(
				"http://PRODUCT-SERVICE/products/update/"+productId,
				request,
				Product.class);
	}
	
	public void deleteProductById(long productId) {
		log.info("Inside deleteProductById method of PortalApiService");
		restTemplate.delete("http://PRODUCT-SERVICE/products/delete/"+productId,
				Product.class);
	}
	
	
	public Product getProductById(long productId) {
		log.info("Inside getProductById method of PortalApiService");
		
		return restTemplate.getForObject("http://PRODUCT-SERVICE/products/list/"+productId, 
				Product.class);
	}
	
	public Listing getLeader(long productId) {
		log.info("Inside getLeader method of PortalApiService");
		
		return restTemplate.getForObject("http://LISTING-SERVICE/listings/getLeader/"+productId, 
				Listing.class);
	}
	
	public Listing getAdmin(long productId) {
		log.info("Inside getAdmin method of PortalApiService");
		
		return restTemplate.getForObject("http://LISTING-SERVICE/listings/getAdmin/"+productId, 
				Listing.class);
	}
	
	public User getUserDetails(long userId) {
		log.info("Inside getUserDetails method of PortalApiService");
		
		return restTemplate.getForObject("http://USER-SERVICE/users/list/"+userId, 
				User.class);
	}
	
	public List<Listing> getJoiners(long productId){
		log.info("Inside getJoiners method of PortalApiService");
		Listing[] joinersResponse =  restTemplate.getForObject("http://LISTING-SERVICE/listings/getJoiners/"+productId
				, Listing[].class);
		
		log.info(String.valueOf(joinersResponse.length));
		return Arrays.asList(joinersResponse);

	}
	
	public List<Comment> getComments(long productId){
		log.info("Inside getCommnets method of PortalApiService");
		Comment[] commentsResponse =  restTemplate.getForObject("http://COMMENT-SERVICE/comments/"+productId
				, Comment[].class);
		
		log.info(String.valueOf(commentsResponse.length));
		return Arrays.asList(commentsResponse);
	}
	
	
	public List<Listing> toPassJoiners(long productId){
		log.info("Inside getJoiners method of PortalApiService");
		
		Listing[] joinersResponse =  restTemplate.getForObject("http://LISTING-SERVICE/listings/getJoinersToReceive/"+productId
				, Listing[].class);
		
		log.info(String.valueOf(joinersResponse.length));
		return Arrays.asList(joinersResponse);

	}
	
	
	public void leaderConfirm(Listing listingDetails, long productId) {
		log.info("Inside leaderConfirm method of PortalApiService");
log.info("HEE leaderConfrimService: "+productId + " : "+ listingDetails);	
		
		String listingRequest ="";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		try{
		ObjectMapper objectMapper = new ObjectMapper();		
		listingRequest = objectMapper.writeValueAsString(listingDetails);
		}
		catch(Exception e){
			log.info("fail Request: "+ listingRequest);
		}
		HttpEntity<String> request = new HttpEntity<>(listingRequest,headers);
				
		log.info("Request: "+ request);
		
		restTemplate.postForObject(
				"http://LISTING-SERVICE/listings/leaderCloseItem/"+productId,
				request,
				Product.class);
	}
	
	public void adminUpdate(long productId, int adminCode) {
		log.info("Inside adminUpdate method of PortalApiService");
log.info("HEE adminUpdateService: "+productId + " : "+adminCode);	
		
		String listingRequest ="";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		try{
		//ObjectMapper objectMapper = new ObjectMapper();		
		//listingRequest = objectMapper.writeValueAsString(listingDetails);
		}
		catch(Exception e){
			log.info("fail Request: "+ listingRequest);
		}
		HttpEntity<String> request = new HttpEntity<>(listingRequest,headers);
				
		log.info("Request: "+ request);
		
		restTemplate.postForObject(
				"http://LISTING-SERVICE/listings/updateAdmin/"+productId+"/"+adminCode,
				request,
				Listing.class);
	}
	
	public void leaderUpdate(long productId, int leaderCode) {
		log.info("Inside leaderUpdate method of PortalApiService");
log.info("HEE leaderUpdateService: "+productId + " : "+leaderCode);	
		
		String listingRequest ="";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		try{
		//ObjectMapper objectMapper = new ObjectMapper();		
		//listingRequest = objectMapper.writeValueAsString(listingDetails);
		}
		catch(Exception e){
			log.info("fail Request: "+ listingRequest);
		}
		HttpEntity<String> request = new HttpEntity<>(listingRequest,headers);
				
		log.info("Request: "+ request);
		
		restTemplate.postForObject(
				"http://LISTING-SERVICE/listings/updateLeader/"+productId+"/"+leaderCode,
				request,
				Listing.class);
	}
	
	public void passToJoiner(long productId, long userId) {
		log.info("Inside leaderUpdate method of PortalApiService");
log.info("HEE passToJoinerService: "+productId + " : "+userId);	
		
		String listingRequest ="";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		try{
		//ObjectMapper objectMapper = new ObjectMapper();		
		//listingRequest = objectMapper.writeValueAsString(listingDetails);
		}
		catch(Exception e){
			log.info("fail Request: "+ listingRequest);
		}
		HttpEntity<String> request = new HttpEntity<>(listingRequest,headers);
				
		log.info("Request: "+ request);
		
		restTemplate.postForObject(
				"http://LISTING-SERVICE/listings/passedToJoiner/"+productId+"/"+userId,
				request,
				Listing.class);
	}
	
	public Comment postComment(@RequestBody Comment comment, long productId, long userId) {
		log.info("Inside postComment method of PortalApiService");
log.info("HEE postCommentService: "+productId + " : "+userId);	
		
		String commentRequest ="";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		try{
		ObjectMapper objectMapper = new ObjectMapper();		
		commentRequest = objectMapper.writeValueAsString(comment);
		}
		catch(Exception e){
			log.info("fail Request: "+ commentRequest);
			
		}
		HttpEntity<String> request = new HttpEntity<>(commentRequest,headers);
				
		log.info("Request: "+ request);
		
		return restTemplate.postForObject(
				"http://COMMENT-SERVICE/comments/create/"+productId+"/"+userId,
				request,
				Comment.class);
=======

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
>>>>>>> 6fea20e7caaa3cf76f2a1a0347673ca0ae819dea
	}
	

}
