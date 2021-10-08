package com.groupbuysg.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupbuysg.user.entity.User;
import com.groupbuysg.user.service.UserService;
import com.groupbuysg.user.valueobject.ResponseObject;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/")
	public User saveUser (@RequestBody User user){
		log.info("Inside saveUser method of UserController");
		return userService.saveUser(user);
	}
	
	@GetMapping("/{id}")
	public ResponseObject getUserWithListing(@PathVariable("id") long UserId){
		log.info("Inside getUserWithListing method of UserController");
		return userService.getUserWithListing(UserId);
	}

	  @GetMapping("/health")
    public ResponseEntity<?> healthCheck()
    {
        return ResponseEntity.ok("Controller works in aws");
    }
	
}
