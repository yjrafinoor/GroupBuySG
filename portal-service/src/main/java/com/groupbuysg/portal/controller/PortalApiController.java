package com.groupbuysg.portal.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public String test() {
		log.info("Inside test method of PortalController");
		return "index";
	}
	
	@RequestMapping("/user_list")
	public ModelAndView userList() {
		log.info("Inside userList method of PortalController");
		portalApiService.getUserList();
		log.info("Hee test1: "+portalApiService.getUserList());
		
		
		//model.addAttribute("listUsers", portalApiService.getUserList());
		//model.addAttribute("listUsers", Arrays.asList(portalApiService.getUserList()));
		//return "redirect:/books/all";
		return null;
	}
	
	

}
