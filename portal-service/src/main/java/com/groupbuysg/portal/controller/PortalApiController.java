package com.groupbuysg.portal.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.groupbuysg.portal.service.PortalApiService;
import com.groupbuysg.portal.valueobject.User;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PortalApiController {
	
	@Autowired
	PortalApiService portalApiService;
	
	@RequestMapping("/")	
	public String indexpage (Model model){
		log.info("Inside indexpage method of PortalApiController");
		//List<User> userList =portalApiService.getUserList();

		return "index";
	}
	
	@RequestMapping("/user_list")
	public String userpage (Model model){
		log.info("Inside userpage method of PortalApiController");
		List<User> listUsers =portalApiService.getUserList();
		model.addAttribute("listUsers",listUsers);
		return "user_list";
	}
	
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute User user)
	{
		
		portalApiService.saveUser(user);
		/*
		 * Here you can write the code to save the user information in database
		 */
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user_list");
		List<User> listUsers =portalApiService.getUserList();
		modelAndView.addObject("listUsers",listUsers);
		

		return modelAndView;
	}
	

}
