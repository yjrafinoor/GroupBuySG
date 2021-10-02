package com.groupbuysg.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupbuysg.listing.entity.Listing;

@Repository
public interface ListingRepository extends JpaRepository<Listing,Long>{

	Listing findByListingId(long listingId);
	
	

}
