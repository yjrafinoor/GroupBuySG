package com.groupbuysg.listing.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	public String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date).toString();
	}
	
	public List<Listing> findAll(){
		log.info("Inside findAll method of ListingService");
		return listingRepository.findAll();
	}
	
	public List<Listing> joinerList(long productId){
		log.info("Inside joinerList method of ListingService");
		return listingRepository.findByProductId(productId);
	}
	
	public Listing createAdmin(Listing listing, long productId) {
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
		listing.setDateJoin(getCurrentDate());
		return listingRepository.save(listing);
	}
	
	public Listing createLeader(Listing listing, long productId, long userId) {
		log.info("Inside createLeader method of ListingService");
		listing.setProductId(productId);
		listing.setUserId(userId);
		listing.setTotalQuantity(0);
		listing.setStatusLeader("OPEN");
		listing.setIsLeader(true);
		listing.setDateJoin(getCurrentDate());
		return listingRepository.save(listing);
	}

	public Listing createJoiner(Listing listing, long productId, long userId) {
		log.info("Inside createJoiner method of ListingService");
		
		int qty = listing.getTotalQuantity();
		Listing leader = new Listing();
		int leaderQty = 0;
		
		leader = getLeader(productId);
log.info("Hee11 getLeader: "+leader);
		leaderQty=leader.getTotalQuantity();
		
		Listing joinerDetails = getListingJoiner(productId, userId);
log.info("Hee11 joinerDetails: "+joinerDetails);		
		if(joinerDetails.getUserId()!=0L && joinerDetails.getUserId()==userId) {
			log.info("Hee joinerDetails!=null: "+joinerDetails);
			
			leaderQty=leaderQty-joinerDetails.getTotalQuantity()+listing.getTotalQuantity();
			
			joinerDetails.setTotalQuantity(listing.getTotalQuantity());
			joinerDetails.setDateJoin(getCurrentDate());
			listingRepository.save(joinerDetails);
		}
		else {
			log.info("Hee joinerDetails==null:");
			listing.setProductId(productId);
			listing.setUserId(userId);
			listing.setIsLeader(false);
			listing.setStatusJoiner("JOINED");
			listing.setDateJoin(getCurrentDate());
			listingRepository.save(listing);
			leaderQty+=qty;
		}
log.info("Hee11 createJoinerlisting: "+listing);
log.info("Hee11 createJoinerjoinerDetails: "+joinerDetails);

		leader.setTotalQuantity(leaderQty);
		listingRepository.save(leader);

		return listing;
	}
	
	public List<Listing>  getItemByUserId(long userId) {
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
	
	
	public Listing leaderCloseItem(Listing listingDetails, long productId) {
		log.info("Inside leaderCloseItem method of ListingService");

		Listing leader = new Listing();
		leader = getLeader(productId);
		
		Listing admin = new Listing();
		admin = getAdmin(productId);
	
		leader.setTotalAmount(listingDetails.getTotalAmount());
		leader.setStatusLeader("CONFIRMED, PENDING JOINERs PAYMENT");
		leader.setDateLeaderClose(getCurrentDate());
		listingRepository.save(leader);
		
		admin.setTotalAmount(listingDetails.getTotalAmount());
		listingRepository.save(admin);
		
		int totalQty = leader.getTotalQuantity();
		double totalAmt = listingDetails.getTotalAmount();
		double unitAmt = totalAmt/totalQty;
		
		updateJoinerStatus(productId, unitAmt, 1);
		
		Product product = new Product();
		restTemplate.postForObject("http://PRODUCT-SERVICE/products/status/" + productId + "/" + 1
		, product, Product.class);
		
		return leader;
	}
	
	public Listing getLeader (long productId) {
		log.info("Inside getLeader method of ListingService");
		Listing leader = new Listing();

		//Start: get leader
		List<Listing> listings = listingRepository.findByProductId(productId);
		if(listings.size()>0) {
			for(int i=0; i<listings.size(); i++) {
				if(listings.get(i).getIsLeader()!=null && listings.get(i).getIsLeader()!=false) {
					return listings.get(i);
				}
			}
		}
		return leader;
	}
	
	public Listing getAdmin (long productId) {
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
		/*
		ResponseObject obj=new ResponseObject();
		User user=
				restTemplate.getForObject("http://USER-SERVICE/users/list/" + admin.getUserId()
				, User.class);
		
		obj.setListing(admin);
		obj.setUser(user);
		*/
		return admin;
	}
	
	public Listing getJoiner (long productId, long userId) {
		log.info("Inside getJoiner method of ListingService");
		Listing joiner = new Listing();

		//Start: get leader
		List<Listing> listings = listingRepository.findByProductId(productId);
		if(listings.size()>0) {
			for(int i=0; i<listings.size(); i++) {
				if(listings.get(i).getUserId()==userId) {
					//if(listings.get(i).getIsLeader()!=null && listings.get(i).getIsLeader()!=true) {
						joiner = listings.get(i);
						log.info("Hee getJoiner: "+ joiner);
					//}
				}
			}
		}
		return joiner;
	}
	
	public Listing getListingAdmin (long productId) {
		log.info("Inside getListingAdmin method of ListingService");
		Listing admin = new Listing();
		admin = getAdmin(productId);
		return admin;
	}
	
	public Listing getListingLeader (long productId) {
		log.info("Inside getListingLeader method of ListingService");
		Listing leader = new Listing();
		leader = getLeader(productId);
		return leader;
	}
	
	public Listing getListingJoiner (long productId, long userId) {
		log.info("Inside getListingJoiner method of ListingService");
		Listing joiner = new Listing();
		Listing leader = getLeader(productId);
		Listing admin = getAdmin(productId);
log.info("Hee11 getListingJoinerleader: "+leader);
log.info("Hee11 getListingJoineradmin: "+admin);
		//Start: get Joiners
		List<Listing> listings = listingRepository.findByProductId(productId);
log.info("Hee11 getListingJoinerlistings: "+listings);
		if(listings.size()>0) {
			for(int i=0; i<listings.size(); i++) {
				//if((listings.get(i).getIsLeader()!=null && listings.get(i).getIsLeader()!=true)
				//		&& (listings.get(i).getUserId()==userId)) {
				if(listings.get(i).getUserId() != leader.getUserId() && listings.get(i).getUserId() != admin.getUserId()
					&&	listings.get(i).getUserId()==userId) {
					joiner = listings.get(i);
				}
			}
		}
log.info("Hee11 getListingJoiner: "+joiner);
		return joiner;
	}
	
	public List<Listing> getListingJoiners (long productId) {
		log.info("Inside getListingJoiners method of ListingService");
		List<Listing> joiners = new ArrayList<Listing>();
		
		Listing leader = getLeader(productId);
		Listing admin = getAdmin(productId);
		
		//Start: get Joiners
		List<Listing> listings = listingRepository.findByProductId(productId);
		if(listings.size()>0) {
			for(int i=0; i<listings.size(); i++) {
				//if(listings.get(i).getIsLeader()!=null && listings.get(i).getIsLeader()!=true) {
				if(listings.get(i).getUserId() != leader.getUserId() && listings.get(i).getUserId() != admin.getUserId()) {
					joiners.add(listings.get(i));
log.info("Hee getListingJoiners1: "+listings.get(i));
				}
			}
		}
		log.info("Hee getListingJoiners2: "+joiners);
		return joiners;
	}
	
	public List<Listing> getJoinersToReceive (long productId) {
		log.info("Inside getListingJoiners method of ListingService");
		List<Listing> joiners = new ArrayList<Listing>();

		//Start: get Joiners
		List<Listing> listings = getListingJoiners(productId);
		if(listings.size()>0) {
			for(int i=0; i<listings.size(); i++) {
				if(listings.get(i).getStatusJoiner().equals("TO RECEIVE")) {
					joiners.add(listings.get(i));
				}
			}
		}
		return joiners;
	}
	
	public Listing getParticularJoiner (long productId, long userId) {
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
	
	
	
	public void updateJoinerStatus(long productId, double unitAmt, int code) {
		log.info("Inside updateJoinerStatus method of ListingService");	
		List<Listing> allJoiners = listingRepository.findByProductId(productId);
		
		Listing leader = getLeader(productId);
		Listing admin = getAdmin(productId);
		
log.info("Hee allJoiners.size(): "+allJoiners.size());
		int countJoiners = allJoiners.size()-2;
		int countPAID=0;
		int countPassOver=0;
		int countReceived=0;
		
		if(allJoiners.size()>0) {
			for(int i=0; i<allJoiners.size(); i++) {
							
				int joinerQty = 0;
				Double joinerPrice = 0.00;
								
				//if(allJoiners.get(i).getIsLeader()!=null && allJoiners.get(i).getIsLeader()!=true) {
				if(allJoiners.get(i).getUserId() != leader.getUserId() && allJoiners.get(i).getUserId() != admin.getUserId()) {
					
					if(code==1) {
						updateAdmin(productId, 1); //adminCode 1: PENDING JOINERs PAYMENT
						
						joinerQty=allJoiners.get(i).getTotalQuantity();
						
						joinerPrice = joinerQty * unitAmt;
						
						allJoiners.get(i).setTotalAmount(joinerPrice);
						allJoiners.get(i).setStatusJoiner("TO PAID");
						listingRepository.save(allJoiners.get(i));
					}
					if(code==2) {
						if(allJoiners.get(i).getStatusJoiner().equals("PAID")) {
							countPAID+=1;
						}
					}
log.info("HEE countPAID==countJoiners: "+ i + " : " +countPAID+" ; "+countJoiners);	

					if(countPAID==countJoiners) {
						updateAdmin(productId, 2);  //adminCode 2: RECEIVED ALL JOINERs PAYMENT 
						updateLeader(productId, 1);//leaderCode 1: PENDING ADMIN RELEASE 10%
					}
					if(code==3) {
						updateAdmin(productId, 4); //adminCode 4: PENDING LEADER PASS TO JOINERs
						allJoiners.get(i).setStatusJoiner("TO RECEIVE");
						listingRepository.save(allJoiners.get(i));
					}
					if(code==4) {
						updateLeader(productId, 6);//leaderCode 6: SCHEDULING TO MEET JOINERs
						if(allJoiners.get(i).getStatusJoiner().equals("PASS OVER")) {
							allJoiners.get(i).setDateLeaderPassOver(getCurrentDate());
							listingRepository.save(allJoiners.get(i));
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
						if(allJoiners.get(i).getStatusJoiner().equals("RECEIVED")) {
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
	
	public Listing joinerPaid(long productId, long userId) {
		log.info("Inside joinerPaid method of ListingService");
		Listing joiner = new Listing();
		List<Listing> allJoiners = listingRepository.findByUserId(userId);
		Listing leader = getLeader(productId);
		Listing admin = getAdmin(productId);
		
		if(allJoiners.size()>0) {
			for(int i=0; i<allJoiners.size(); i++) {
								
				if(allJoiners.get(i).getProductId()==productId 
					//&& (allJoiners.get(i).getIsLeader()!=null && allJoiners.get(i).getIsLeader()!=true)
					&& allJoiners.get(i).getUserId() != leader.getUserId() && allJoiners.get(i).getUserId() != admin.getUserId()) {
					
					joiner = allJoiners.get(i);
					joiner.setStatusJoiner("PAID");
					joiner.setDateJoinerPaid(getCurrentDate());
					listingRepository.save(joiner);
					
					//checkAllJoinerPaid(productId);
					updateJoinerStatus(productId, 0.00, 2); //joinderCode 2:
				}
			}
		}	
		return joiner;
	}
	
	public Listing updateAdmin(long productId, int adminCode) {
		log.info("Inside updateAdmin method of ListingService");
		Listing admin = new Listing();
		Listing leader = new Listing();
		admin = getAdmin(productId);
		leader = getLeader(productId);
		
		double amt10 = leader.getTotalAmount()*10/100;
		double amt90 = leader.getTotalAmount()-amt10;
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
			admin.setDateAmount10(getCurrentDate());
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
			admin.setDateAmount90(getCurrentDate());
			listingRepository.save(admin);
			updateLeader(productId, 9); //leaderCode 9: ADMIN RELEASED REMIND AMOUNT
		}
		if(adminCode==8) {
			admin.setStatusAdmin("COMPLETED");
			listingRepository.save(admin);
		}
		
		return admin;
	}
	
	public Listing updateLeader(long productId, int leaderCode) {
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
			leader.setDateAmount10(getCurrentDate());
			listingRepository.save(leader);
		}
		if(leaderCode==4) {
			leader.setStatusLeader("MAKE ORDER and PENDING SHIPPING");
			leader.setDateLeaderOrder(getCurrentDate());
			listingRepository.save(leader);
		}
		if(leaderCode==5) {
			leader.setStatusLeader("RECEIVED PARCEL, TO MEET JOINERs");
			leader.setDateReceivedItem(getCurrentDate());
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
			leader.setDateLeaderPassOver(getCurrentDate());
			listingRepository.save(leader);
log.info("Hee leader leader2: "+leader);			
		}
		if(leaderCode==8) {
			//AUTO STATUS
			leader.setStatusLeader("ALL JOINERs RECEIVED, PENDING ADMIN PAY");
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
			leader.setDateAmount90(getCurrentDate());
			listingRepository.save(leader);
			updateAdmin(productId, 8); //adminCode=8
			
			Product product = new Product();
			restTemplate.postForObject("http://PRODUCT-SERVICE/products/status/" + productId + "/" + 2
			, product, Product.class);
			
		}
		return leader;
	}
	
	public Listing passedToJoiner(long productId, long userId) {
		log.info("Inside passedToJoiner method of ListingService");
		Listing joiner = new Listing();
		joiner = getParticularJoiner(productId, userId);
		
		joiner.setStatusJoiner("PASS OVER");
		listingRepository.save(joiner);
		updateJoinerStatus(productId, 0.00, 4);
		
		return joiner;
	}
	
	public Listing joinerReceived(long productId, long userId) {
		log.info("Inside joinerReceived method of ListingService");
		Listing joiner = new Listing();
		List<Listing> allJoiners = listingRepository.findByUserId(userId);
		Listing leader = getLeader(productId);
		Listing admin = getAdmin(productId);
		
		if(allJoiners.size()>0) {
			for(int i=0; i<allJoiners.size(); i++) {
								
				if(allJoiners.get(i).getProductId()==productId 
					//&& (allJoiners.get(i).getIsLeader()!=null && allJoiners.get(i).getIsLeader()!=true)
					&& allJoiners.get(i).getUserId() != leader.getUserId() 
					&& allJoiners.get(i).getUserId() != admin.getUserId()) {
					
					joiner = allJoiners.get(i);
					joiner.setStatusJoiner("RECEIVED");
					joiner.setDateReceivedItem(getCurrentDate());
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