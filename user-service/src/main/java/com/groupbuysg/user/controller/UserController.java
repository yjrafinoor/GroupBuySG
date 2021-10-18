package com.groupbuysg.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupbuysg.user.entity.User;
import com.groupbuysg.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public List<User> userList (){
		log.info("Inside userList method of UserController");
		return userService.userList();
	}
	
	@PostMapping("/create")
	public User signup (@RequestBody User user){
		log.info("Inside signup method of UserController");
		return userService.signup(user);
	}
	
	@GetMapping("/list/{id}")
	public User getUserById(@PathVariable("id") long UserId){
		log.info("Inside getUserById method of UserController");
		return userService.getUserById(UserId);
	}
	
	@GetMapping("/list/admin")
	public User getAdmin(){
		log.info("Inside getAdmin method of UserController");
		return userService.getAdmin();
	}
	
	@PostMapping("/update/{id}")
	public User updateUser (@PathVariable("id") long UserId, @RequestBody User user){
		log.info("Inside updateUser method of UserController");
		return userService.updateUser(UserId, user);
	}
	
	@PostMapping("/requestLeader/{id}")
	public User requestLeader(@PathVariable("id") long UserId){
		log.info("Inside requestLeader method of UserController");
		return userService.requestLeader(UserId);
	}
	
	@PostMapping("/reviewLeader/{id}/{code}")
	public User reviewLeader(@PathVariable("id") long UserId, @PathVariable("code") int code){
		log.info("Inside reviewLeader method of UserController");
		return userService.reviewLeader(UserId, code);
	}
	
	@DeleteMapping("/delete/{id}")
	public User deleteUserById(@PathVariable("id") long UserId){
		log.info("Inside deleteUserbyId method of UserController");
		return userService.deleteUserById(UserId);
	}
	
	@GetMapping("/findByUserName/{uid}")
	public User findByUserName(@PathVariable("uid") String userName){
		log.info("Inside deleteUserById method of UserController");
		return userService.findByUserName(userName);
	}
	

    @GetMapping("/health")
	public ResponseEntity<?> healthCheck()	{
    	return ResponseEntity.ok("Controller works in aws");
	}
	
	
}
