package com.groupbuysg.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WebApiController {
	
	@RequestMapping("/")
	public String test() {
		log.info("Inside test method of PortalController");
		return "index";
	}
}
