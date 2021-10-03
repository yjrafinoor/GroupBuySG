package com.groupbuysg.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.groupbuysg.portal.repository.PortalApiRepository;
import com.groupbuysg.portal.service.PortalApiService;
import com.groupbuysg.portal.valueobject.Listing;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PortalApiController {
	
	@Autowired
	PortalApiService portalApiService;
	
	@RequestMapping("/")
	public String indexpage (@RequestBody Model model){
		log.info("Inside indexpage method of PortalApiController");
		List<Listing> allList=new ArrayList<Listing>();
		Listing listing=portalApiService.getListing();
		allList.add(listing);
		model.addAttribute("allListing",allList);
		return "index";
	}

}
