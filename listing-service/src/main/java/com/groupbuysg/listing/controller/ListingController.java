package com.groupbuysg.listing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupbuysg.listing.entity.Listing;
import com.groupbuysg.listing.service.ListingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/listings")
@Slf4j
public class ListingController {
	
	@Autowired
	private ListingService listingService;
	
	@PostMapping("/")
	public Listing saveListing (@RequestBody Listing listing){
		log.info("Inside saveListing method of ListingController");
		return listingService.saveListing(listing);
	}
	
	@GetMapping("/{id}")
	public Listing findListingById(@PathVariable("id") long listingId){
		log.info("Inside findListingById method of ListingController "+String.valueOf(listingId));
		return listingService.findListingbyId(listingId);
	}
}


