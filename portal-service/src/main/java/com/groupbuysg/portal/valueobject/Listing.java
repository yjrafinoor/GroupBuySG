package com.groupbuysg.portal.valueobject;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Listing {
	private long listingId;
	private String listingTitle;
	private String createdBy;
	private String createdDate;
}
