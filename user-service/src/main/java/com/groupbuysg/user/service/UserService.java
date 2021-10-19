package com.groupbuysg.user.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.groupbuysg.user.entity.User;
import com.groupbuysg.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	
	public List<User> userList(){
		log.info("Inside userList method of UserService");
		return userRepository.findAll();
	}
	
	public User signup(User user) {
		log.info("Inside saveUser method of UserService");
		user.setRole("USER");
		//user.setDateRegister(LocalDateTime.now());
		user.setRequestLeader(false);
		user.setDateBeLeader(null);
		user.setIsActive(true);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        user.setDateRegister(dateFormat.format(date).toString());
		
		userRepository.save(user);
		
		if(user.getUserId()==1) {
			user.setRole("ADMIN");
			userRepository.save(user);
		}
		
		return user;
	}
	
	public User getUserById(long userId) {
		log.info("Inside getUserById method of UserService");
		return userRepository.findByUserId(userId);
	}
	
	public User getAdmin() {
		log.info("Inside getAdmin method of UserService");
		List<User> users = userRepository.findByRole("ADMIN");
		
		User admin = new User();
		admin = users.get(0);
		
		return admin;
	}
	
	public User updateUser(long userId, User userDetails) {
		log.info("Inside updateUser method of UserService");
		User user = userRepository.findByUserId(userId);
		user.setUserName(user.getUserName());
		user.setFullName(userDetails.getFullName());
		user.setEmail	(userDetails.getEmail());
		
		if (userDetails.getPassword()!=null) {
			user.setPassword(userDetails.getPassword());
		}
		else {
			user.setPassword(user.getPassword());
		}
		user.setAddress	(userDetails.getAddress());
		user.setMrt		(userDetails.getMrt());
		user.setRole	(user.getRole());
		user.setContact	(userDetails.getContact());
		user.setPaynowName	(userDetails.getPaynowName());
		user.setPaynowMobile(userDetails.getPaynowMobile());
		user.setDateRegister(user.getDateRegister());
		user.setRequestLeader(user.getRequestLeader());
		user.setDateBeLeader(userDetails.getDateBeLeader());
		user.setIsActive(user.getIsActive());
		return userRepository.save(user);
	}
	
	public User requestLeader(long userId) {
		log.info("Inside requestLeader method of UserService");
		User user = userRepository.findByUserId(userId);
		user.setRequestLeader(true);
		return userRepository.save(user);
	}
	
	public User reviewLeader(long userId, int code) {
		log.info("Inside approveLeader method of UserService");
		User user = userRepository.findByUserId(userId);
log.info("Hee reviewLeader: "+userId +" : "+code);
		if(code==1) {
			user.setRequestLeader(null);
			user.setRole("LEADER");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = new Date();
	        user.setDateBeLeader(dateFormat.format(date).toString());
		}
		if(code==2) {
			user.setRequestLeader(false);
			user.setRole("USER");
		}
			
		return userRepository.save(user);
	}
	
	public User deleteUserById(long userId) {
		log.info("Inside deleteUserbyId method of UserService");
		User user = userRepository.findByUserId(userId);
		user.setIsActive(false);
		return userRepository.save(user);
	}
	
	public User findByUserName(String userName) {
		log.info("Inside findByUserName method of UserService");
		return userRepository.findByUserName(userName);
	}
	public User findByEmail(String email) {
		log.info("Inside findByEmail method of UserService");
		return userRepository.findByEmail(email);
	}
/*	
	public User deleteUserById(long userId) {
		log.info("Inside deleteUserbyId method of UserService");
		userRepository.deleteById(userId);
		return null;
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
	}*/
}
