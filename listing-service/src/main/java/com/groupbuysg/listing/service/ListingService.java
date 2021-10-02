package com.groupbuysg.listing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupbuysg.listing.controller.ListingController;
import com.groupbuysg.listing.entity.Listing;
import com.groupbuysg.listing.repository.ListingRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ListingService {

	@Autowired
	private ListingRepository listingRepository;

	public Listing saveListing(Listing listing) {

		log.info("Inside saveListing method of ListingService");
		return listingRepository.save(listing);
	}

	public Listing findListingbyId(long listingId) {
		log.info("Inside findListingbyId method of ListingService");		
		return listingRepository.findByListingId(listingId);
		
	}

	
}
