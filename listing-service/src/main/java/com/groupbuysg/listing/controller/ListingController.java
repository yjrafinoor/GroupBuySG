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
	public List<Listing> joinerList (@PathVariable("pid") long productId){
		log.info("Inside joinerList method of LisintgController");
		return listingService.joinerList(productId);
	}
	
	@PostMapping("/createAdmin/{pid}")
	public Listing createAdmin (@RequestBody Listing listing, @PathVariable("pid") long productId){
		log.info("Inside createAdmin method of LisintgController");
		return listingService.createAdmin(listing, productId);
	}
	
	@PostMapping("/createLeader/{pid}/{uid}")
	public Listing createLeader (@RequestBody Listing listing, @PathVariable("pid") long productId, @PathVariable("uid") long userId){
		log.info("Inside createLeader method of LisintgController");
		return listingService.createLeader(listing, productId, userId);
	}
	
	@GetMapping("/getJoiner/{pid}/{uid}")
	public Listing getJoiner (@PathVariable("pid") long productId, @PathVariable("uid") long userId){
		log.info("Inside getJoiner method of LisintgController");
		return listingService.getJoiner(productId, userId);
	}
	
	@PostMapping("/createJoiner/{pid}/{uid}")
	public Listing createJoiner (@RequestBody Listing listing, @PathVariable("pid") long productId, @PathVariable("uid") long userId){
		log.info("Inside createJoiner method of LisintgController");
		return listingService.createJoiner(listing, productId, userId);
	}
	
	@GetMapping("/listUser/{uid}")
	public List<Listing> getItemByUserId(@PathVariable("uid") long userId){
		log.info("Inside getItemByUserId method of LisintgController");
		return listingService.getItemByUserId(userId);
	}
	
	@PostMapping("/leaderCloseItem/{pid}")
	public Listing leaderCloseItem (@RequestBody Listing listing, @PathVariable("pid") long productId){
		log.info("Inside leaderCloseItem method of LisintgController");

		return listingService.leaderCloseItem(listing, productId);
	}
	
	@PostMapping("/joinerPaid/{pid}/{uid}")
	public Listing joinerPaid (@PathVariable("pid") long productId, @PathVariable("uid") long userId){
		log.info("Inside joinerPaid method of LisintgController");
		return listingService.joinerPaid(productId, userId);
	}
	
	@PostMapping("/updateAdmin/{pid}/{code}")
	public Listing updateAdmin (@PathVariable("pid") long productId, @PathVariable("code") int code){
		log.info("Inside updateAdmin method of LisintgController");
		//CODE:3 - COMFIRMED, RELEASED 10% TO LEADER
		//CODE:7 - RELEASED REMAIND AMOUNT TO LEADER
		return listingService.updateAdmin(productId, code);
	}
	
	@PostMapping("/updateLeader/{pid}/{code}")
	public Listing updateLeader (@PathVariable("pid") long productId, @PathVariable("code") int code){
		log.info("Inside updateLeader method of LisintgController");
		//CODE: 3 - RECEIVED 10% FROM ADMIN
		//CODE: 4 - MAKE ORDER and PENDING SHIPPING
		//CODE: 5 - RECEIVED
		//CODE: 10- RECEIVED 90% FROM ADMIN
		return listingService.updateLeader(productId, code);
	}
	
	@PostMapping("/passedToJoiner/{pid}/{uid}")
	public Listing passedToJoiner (@PathVariable("pid") long productId, @PathVariable("uid") long userId){
		log.info("Inside passedToJoiner method of LisintgController");
		return listingService.passedToJoiner(productId, userId);
	}
	
	@PostMapping("/joinerReceived/{pid}/{uid}")
	public Listing joinerReceived (@PathVariable("pid") long productId, @PathVariable("uid") long userId){
		log.info("Inside joinerReceived method of LisintgController");
		return listingService.joinerReceived(productId, userId);
	}
	
	@GetMapping("/getLeader/{pid}")
	public Listing getLeader (@PathVariable("pid") long productId){
		log.info("Inside getLeader method of LisintgController");
		return listingService.getLeader(productId);
	}

	@GetMapping("/getAdmin/{pid}")
	public Listing getAdmin (@PathVariable("pid") long productId){
		log.info("Inside getLeader method of LisintgController");
		return listingService.getAdmin(productId);
	}
	
	@GetMapping("/getJoiners/{pid}")
	public List<Listing> getJoiners (@PathVariable("pid") long productId){
		log.info("Inside getJoiners method of LisintgController");
		return listingService.getListingJoiners(productId);
	}
	
	@GetMapping("/getJoinersToReceive/{pid}")
	public List<Listing> getJoinersToReceive (@PathVariable("pid") long productId){
		log.info("Inside getJoinersToReceive method of LisintgController");
		return listingService.getJoinersToReceive(productId);
	}

}


