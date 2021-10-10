package com.groupbuysg.listing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.groupbuysg.listing.entity.Listing;
import com.groupbuysg.listing.repository.ListingRepository;
import com.groupbuysg.listing.valueobject.Product;
import com.groupbuysg.listing.valueobject.ResponseObject;
import com.groupbuysg.listing.valueobject.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ListingService {

	@Autowired
	private ListingRepository listingRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public List<Listing> findAll(){
		log.info("Inside findAll method of ListingService");
		return listingRepository.findAll();
	}
	
	public List<Listing> joinerList(Long productId){
		log.info("Inside joinerList method of ListingService");
		return listingRepository.findByProductId(productId);
	}
	
	public Listing createAdmin(Listing listing, Long productId) {
		log.info("Inside createLeader method of ListingService");
		
		User admin =
				restTemplate.getForObject("http://USER-SERVICE/users/list/admin"
				, User.class);
		
		listing.setProductId(productId);
		listing.setUserId(admin.getUserId());
		listing.setTotalQuantity(0);
		listing.setStatusAdmin("OPEN");
		listing.setIsLeader(null);
		listing.setAllJoinerPaid(false);
		return listingRepository.save(listing);
	}
	
	public Listing createLeader(Listing listing, Long productId, Long userId) {
		log.info("Inside createLeader method of ListingService");
		listing.setProductId(productId);
		listing.setUserId(userId);
		listing.setTotalQuantity(0);
		listing.setStatusLeader("OPEN");
		listing.setIsLeader(true);
		return listingRepository.save(listing);
	}

	public Listing createJoiner(Listing listing, Long productId, Long userId) {
		log.info("Inside createJoiner method of ListingService");
		listing.setProductId(productId);
		listing.setUserId(userId);
		listing.setIsLeader(false);
		listing.setStatusJoiner("JOINED");
		listingRepository.save(listing);
		
		int qty = listing.getTotalQuantity();
		Listing leader = new Listing();
		int leaderQty = 0;
	
		leader = getLeader(productId);
		leaderQty=leader.getTotalQuantity();
		leaderQty+=qty;
		leader.setTotalQuantity(leaderQty);
		
		listingRepository.save(leader);
		
		return listing;
	}
	
	public List<Listing>  getItemByUserId(Long userId) {
		log.info("Inside getItemByUserId method of ListingService");
		return listingRepository.findByUserId(userId);	
		/*
		ResponseObject obj=new ResponseObject();
		Listing listing=listingRepository.findByUserId(userId);	
		
		User user =
				restTemplate.getForObject("http://USER-SERVICE/users/list/" + listing.getUserId()
				, User.class);
		
		obj.setListing(listing);
		obj.setUser(user);
		
		return obj;
		*/
	}
	
	
	public Listing leaderCloseItem(Listing listingDetails, Long productId, Long userId) {
		log.info("Inside leaderCloseItem method of ListingService");
		
		Listing leader = new Listing();
		leader = getLeader(productId);
		
		leader.setTotalAmout(listingDetails.getTotalAmout());
		leader.setStatusLeader("CONFIRMED");
		listingRepository.save(leader);
		
		int totalQty = leader.getTotalQuantity();
		double totalAmt = listingDetails.getTotalAmout();
		double unitAmt = totalAmt/totalQty;
		
		updateJoinerStatus(productId, unitAmt, 1);
		
		return leader;
	}
	
	public Listing getLeader (Long productId) {
		log.info("Inside getLeader method of ListingService");
		Listing leader = new Listing();

		//Start: get leader
		List<Listing> listings = listingRepository.findByProductId(productId);
		if(listings.size()>0) {
			for(int i=0; i<listings.size(); i++) {
				if(listings.get(i).getIsLeader()!=null && listings.get(i).getIsLeader()!=false) {
					leader = listings.get(i);
				}
			}
		}
		return leader;
	}
	
	public Listing getAdmin (Long productId) {
		log.info("Inside getAdmin method of ListingService");
		Listing admin = new Listing();
		//Start: get leader
		List<Listing> listings = listingRepository.findByProductId(productId);
		if(listings.size()>0) {
			for(int i=0; i<listings.size(); i++) {
				if(listings.get(i).getIsLeader()==null) {
					admin = listings.get(i);
				}
			}
		}
		return admin;
	}
	
	public Listing getParticularJoiner (Long productId, Long userId) {
		log.info("Inside getParticularJoiner method of ListingService");
		Listing joiner = new Listing();
		//Start: get leader
		List<Listing> listings = listingRepository.findByProductId(productId);
		if(listings.size()>0) {
			for(int i=0; i<listings.size(); i++) {
				if(listings.get(i).getUserId()==userId) {
					joiner = listings.get(i);
				}
			}
		}
		return joiner;
	}
	
	
	
	public void updateJoinerStatus(Long productId, double unitAmt, int code) {
		log.info("Inside updateJoinerStatus method of ListingService");	
		List<Listing> allJoiners = listingRepository.findByProductId(productId);
log.info("Hee allJoiners.size(): "+allJoiners.size());
		int countJoiners = allJoiners.size()-2;
		int countPAID=0;
		int countPassOver=0;
		int countReceived=0;
		
		if(allJoiners.size()>0) {
			for(int i=0; i<allJoiners.size(); i++) {
							
				int joinerQty = 0;
				Double joinerPrice = 0.00;
								
				if(allJoiners.get(i).getIsLeader()!=null && allJoiners.get(i).getIsLeader()!=true) {
					
					if(code==1) {
						updateAdmin(productId, 1); //adminCode 1: PENDING JOINERs PAYMENT
						
						joinerQty=allJoiners.get(i).getTotalQuantity();
						
						joinerPrice = joinerQty * unitAmt;
						
						allJoiners.get(i).setTotalAmout(joinerPrice);
						allJoiners.get(i).setStatusJoiner("PENDING PAYMENT");
						listingRepository.save(allJoiners.get(i));
					}
					if(code==2) {
						if(allJoiners.get(i).getStatusJoiner().equals("PAID")) {
							countPAID+=1;
						}
					}
log.info("HEE countPAID==countJoiners: "+ i + " : " +countPAID+" ; "+countJoiners);	

					if(countPAID==countJoiners) {
						updateAdmin(productId, 2);
						updateLeader(productId, 1);
					}
					if(code==3) {
						updateAdmin(productId, 4); //adminCode 4: PENDING LEADER PASS TO JOINERs
						allJoiners.get(i).setStatusJoiner("TO RECEIVE");
						listingRepository.save(allJoiners.get(i));
					}
					if(code==4) {
						updateLeader(productId, 6);//leaderCode 6: SCHEDULING TO MEET JOINERs
						if(allJoiners.get(i).getStatusJoiner().equals("PASS OVER")) {
							countPassOver+=1;
						}
log.info("HEE countPassOver==countJoiners: "+ i + " : " +countPassOver+" ; "+countJoiners);	
						if(countPassOver==countJoiners) {
							updateAdmin(productId, 5);
log.info("Hee updateLeader(productId, 7): "+productId);
							updateLeader(productId, 7); //leaderCode 7: COMPLETED PASS TO ALL JOINERs
						}
					}					
					if(code==5) {
log.info("Hee code==5: ");
						if(allJoiners.get(i).getStatusJoiner().equals("CONFIRMED RECEIVE")) {
							countReceived+=1;
						}
log.info("Hee countReceived==countJoiners: "+i + " : " +countReceived+" ; "+countJoiners);
						if(countReceived==countJoiners) {
							updateAdmin(productId, 6);
							updateLeader(productId, 8);
						}
					}
				}
			}
		}		
	}
	
	public Listing joinerPaid(Long productId, Long userId) {
		log.info("Inside joinerPaid method of ListingService");
		Listing joiner = new Listing();
		List<Listing> allJoiners = listingRepository.findByUserId(userId);
		if(allJoiners.size()>0) {
			for(int i=0; i<allJoiners.size(); i++) {
								
				if(allJoiners.get(i).getProductId()==productId 
					&& (allJoiners.get(i).getIsLeader()!=null && allJoiners.get(i).getIsLeader()!=true)) {
					
					joiner = allJoiners.get(i);
					joiner.setStatusJoiner("PAID");
					listingRepository.save(joiner);
					
					//checkAllJoinerPaid(productId);
					updateJoinerStatus(productId, 0.00, 2);
				}
			}
		}	
		return joiner;
	}
	
	public Listing updateAdmin(Long productId, int adminCode) {
		log.info("Inside updateAdmin method of ListingService");
		Listing admin = new Listing();
		Listing leader = new Listing();
		admin = getAdmin(productId);
		leader = getLeader(productId);
		
		double amt10 = leader.getTotalAmout()*10/100;
		double amt90 = leader.getTotalAmout()-amt10;
		leader.setAmount10(amt10);
		leader.setAmount90(amt90);
		listingRepository.save(leader);
		
		if(adminCode==1) {
			//AUTO STATUS
			admin.setAmount10(amt10);
			admin.setAmount90(amt90);
			admin.setStatusAdmin("PENDING JOINERs PAYMENT");
			listingRepository.save(admin);
		}
		if(adminCode==2) {
			//AUTO STATUS
			admin.setStatusAdmin("RECEIVED ALL JOINERs PAYMENT");
			listingRepository.save(admin);
		}
		if(adminCode==3) {
			admin.setStatusAdmin("RELEASED 10% TO LEADER");
			listingRepository.save(admin);
			updateLeader(productId, 2);
		}
		if(adminCode==4) {
			//AUTO STATUS
			admin.setStatusAdmin("PENDING LEADER PASS TO JOINERs");
			listingRepository.save(admin);
		}
		if(adminCode==5) {
			//AUTO STATUS
			admin.setStatusAdmin("PENDING JOINERs CONFIM RECEIVE");
			listingRepository.save(admin);
		}
		if(adminCode==6) {
			//AUTO STATUS
			admin.setStatusAdmin("PLEASE RELEASE REMAINDING AMOUNT");
			listingRepository.save(admin);
		}
		if(adminCode==7) {
			admin.setStatusAdmin("RELEASED REMAIND AMOUNT TO LEADER");
			listingRepository.save(admin);
			updateLeader(productId, 9); //leaderCode 9: ADMIN RELEASED REMIND AMOUNT
		}
		if(adminCode==8) {
			admin.setStatusAdmin("COMPLETED");
			listingRepository.save(admin);
		}
		
		return admin;
	}
	
	public Listing updateLeader(Long productId, int leaderCode) {
		log.info("Inside updateLeader method of ListingService");
		Listing leader = new Listing();
		leader = getLeader(productId);
		
		if(leaderCode==1) {
			//AUTO STATUS
			leader.setStatusLeader("PENDING ADMIN RELEASE 10%");
			listingRepository.save(leader);
		}
		if(leaderCode==2) {
			//AUTO STATUS
			leader.setStatusLeader("ADMIN RELEASED 10% AMOUNT");
			listingRepository.save(leader);
		}
		if(leaderCode==3) {
			leader.setStatusLeader("RECEIVED 10% FROM ADMIN");
			listingRepository.save(leader);
		}
		if(leaderCode==4) {
			leader.setStatusLeader("MAKE ORDER and PENDING SHIPPING");
			listingRepository.save(leader);
		}
		if(leaderCode==5) {
			leader.setStatusLeader("RECEIVED ITEMS");
			listingRepository.save(leader);
			updateJoinerStatus(productId, 0.00, 3);
		}
		if(leaderCode==6) {
			leader.setStatusLeader("SCHEDULING TO MEET JOINERs");
			listingRepository.save(leader);
		}
		if(leaderCode==7) {
			//AUTO STATUS
log.info("Hee leaderCode==7: "+productId);
			leader.setStatusLeader("COMPLETED PASS TO ALL JOINERs");
log.info("Hee leader leader1: "+leader);
			listingRepository.save(leader);
log.info("Hee leader leader2: "+leader);			
		}
		if(leaderCode==8) {
			//AUTO STATUS
			leader.setStatusLeader("ALL JOINERs UPDATED RECEIVED");
			listingRepository.save(leader);
		}
		//9
		if(leaderCode==9) {
			//AUTO STATUS
			leader.setStatusLeader("ADMIN RELEASED REMIND AMOUNT");
			listingRepository.save(leader);
		}
		if(leaderCode==10) {
			//leader.setStatusLeader("RECEIVED 90% FROM ADMIN");
			leader.setStatusLeader("COMPLETED");
			listingRepository.save(leader);
			updateAdmin(productId, 8);
			
			Product product = new Product();
			restTemplate.postForObject("http://PRODUCT-SERVICE/products/close/" + productId
			, product, Product.class);
			
		}
		return leader;
	}
	
	public Listing passedToJoiner(Long productId, Long userId) {
		log.info("Inside passedToJoiner method of ListingService");
		Listing joiner = new Listing();
		joiner = getParticularJoiner(productId, userId);
		
		joiner.setStatusJoiner("PASS OVER");
		listingRepository.save(joiner);
		updateJoinerStatus(productId, 0.00, 4);
		
		return joiner;
	}
	
	public Listing joinderReceived(Long productId, Long userId) {
		log.info("Inside joinderReceived method of ListingService");
		Listing joiner = new Listing();
		List<Listing> allJoiners = listingRepository.findByUserId(userId);
		if(allJoiners.size()>0) {
			for(int i=0; i<allJoiners.size(); i++) {
								
				if(allJoiners.get(i).getProductId()==productId 
					&& (allJoiners.get(i).getIsLeader()!=null && allJoiners.get(i).getIsLeader()!=true)) {
					
					joiner = allJoiners.get(i);
					joiner.setStatusJoiner("RECEIVED");
					listingRepository.save(joiner);
				}
			}
		}	
		//checkAllJoinerReceived(productId);
		updateJoinerStatus(productId, 0.00, 5);
log.info("Hee updateJoinerStatus(productId, 0.00, 5);: "+ productId +" ; 0.00 ; 5");
		
		return joiner;
	}

}