package com.groupbuysg.listing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupbuysg.listing.entity.Listing;
import com.groupbuysg.listing.service.ListingService;
import com.groupbuysg.listing.valueobject.ResponseObject;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/listings")
@Slf4j
public class ListingController {
	
	@Autowired
	private ListingService listingService;
	
	@GetMapping("/")
	public List<Listing> findAll (){
		log.info("Inside findAll method of ProductController");
		return listingService.findAll();
	}
	
	@GetMapping("/listProduct/{pid}")
	public List<Listing> joinerList (@PathVariable("pid") Long productId){
		log.info("Inside joinerList method of LisintgController");
		return listingService.joinerList(productId);
	}
	
	@PostMapping("/createAdmin/{pid}")
	public Listing createAdmin (@RequestBody Listing listing, @PathVariable("pid") Long productId){
		log.info("Inside createAdmin method of LisintgController");
		return listingService.createAdmin(listing, productId);
	}
	
	@PostMapping("/createLeader/{pid}/{uid}")
	public Listing createLeader (@RequestBody Listing listing, @PathVariable("pid") Long productId, @PathVariable("uid") Long userId){
		log.info("Inside createLeader method of LisintgController");
		return listingService.createLeader(listing, productId, userId);
	}
	
	@PostMapping("/createJoiner/{pid}/{uid}")
	public Listing createJoiner (@RequestBody Listing listing, @PathVariable("pid") Long productId, @PathVariable("uid") Long userId){
		log.info("Inside createJoiner method of LisintgController");
		return listingService.createJoiner(listing, productId, userId);
	}
	
	@GetMapping("/listUser/{uid}")
	public List<Listing> getItemByUserId(@PathVariable("uid") Long userId){
		log.info("Inside getItemByUserId method of LisintgController");
		return listingService.getItemByUserId(userId);
	}
	
	@PutMapping("/leaderCloseItem/{pid}/{uid}")
	public Listing leaderCloseItem (@RequestBody Listing listing, @PathVariable("pid") Long productId, @PathVariable("uid") Long userId){
		log.info("Inside leaderCloseItem method of LisintgController");
		return listingService.leaderCloseItem(listing, productId, userId);
	}
	
	@PutMapping("/joinerPaid/{pid}/{uid}")
	public Listing joinerPaid (@PathVariable("pid") Long productId, @PathVariable("uid") Long userId){
		log.info("Inside joinerPaid method of LisintgController");
		return listingService.joinerPaid(productId, userId);
	}
	
	@PutMapping("/updateAdmin/{pid}/{code}")
	public Listing updateAdmin (@PathVariable("pid") Long productId, @PathVariable("code") int code){
		log.info("Inside updateAdmin method of LisintgController");
		//CODE:3 - COMFIRMED, RELEASED 10% TO LEADER
		//CODE:7 - RELEASED REMAIND AMOUNT TO LEADER
		return listingService.updateAdmin(productId, code);
	}
	
	@PutMapping("/updateLeader/{pid}/{code}")
	public Listing updateLeader (@PathVariable("pid") Long productId, @PathVariable("code") int code){
		log.info("Inside updateLeader method of LisintgController");
		//CODE: 3 - RECEIVED 10% FROM ADMIN
		//CODE: 4 - MAKE ORDER and PENDING SHIPPING
		//CODE: 5 - RECEIVED
		//CODE: 10- RECEIVED 90% FROM ADMIN
		return listingService.updateLeader(productId, code);
	}
	
	@PutMapping("/passedToJoiner/{pid}/{uid}")
	public Listing passedToJoiner (@PathVariable("pid") Long productId, @PathVariable("uid") Long userId){
		log.info("Inside passedToJoiner method of LisintgController");
		return listingService.passedToJoiner(productId, userId);
	}
	
	@PutMapping("/joinderReceived/{pid}/{uid}")
	public Listing joinderReceived (@PathVariable("pid") Long productId, @PathVariable("uid") Long userId){
		log.info("Inside joinderReceived method of LisintgController");
		return listingService.joinderReceived(productId, userId);
	}


}


