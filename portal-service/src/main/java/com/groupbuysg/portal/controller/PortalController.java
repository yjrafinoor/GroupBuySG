package com.groupbuysg.portal.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.groupbuysg.portal.valueobject.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PortalController {
	
	@GetMapping("/register")
	public String registerForm(Model model){
		log.info("Inside indexpage method of PortalApiController");
		model.addAttribute("user", new User());
		return "profile_update";
	}
	
	@PostMapping("/profile_update")
	public String profileUpdate(Model model) {
		log.info("Inside profileUpdate method of PortalApiController");
		User user = new User();
		return "home";
	}

}
