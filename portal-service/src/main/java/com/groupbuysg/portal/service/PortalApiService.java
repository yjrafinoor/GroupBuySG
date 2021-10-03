package com.groupbuysg.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.groupbuysg.portal.valueobject.Listing;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PortalApiService {
	
	@Autowired
	private RestTemplate restTemplate;
	

	public Listing getListing() {
		log.info("Inside getListing method of PortalApiService");
		return restTemplate.getForObject("http://LISTING-SERVICE/listings/" + "1"
				, Listing.class);
	}

}
