package com.groupbuysg.listing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupbuysg.listing.entity.Listing;

@Repository
public interface ListingRepository extends JpaRepository<Listing,Long>{

	Listing findByListingId(Long listingId);
	List<Listing> findByUserId(Long userId);
	List<Listing> findByProductId(Long productId);
	//tutorialRepository.findAll(Sort.by("published").descending().and(Sort.by("title")));
	//Page<T> findAll(Pageable pageable);
}
