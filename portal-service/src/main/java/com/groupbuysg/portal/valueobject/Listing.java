package com.groupbuysg.portal.valueobject;


import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Listing {
	
	@Id
	private long listingId;
	private Integer totalQuantity;
	private Double  totalAmount;
	private Double amount10;
	private Double amount90;
	private String DateAmount10;
	private String DateAmount90;
	private String statusLeader;
	private String statusJoiner;
	private String DateJoin;
	private String DateLeaderClose;
	private String DateJoinerPaid;
	private String DateLeaderOrder;
	private String DateReceivedItem;
	private String DateLeaderPassOver;
	private String statusAdmin;
	private Boolean isLeader;
	private Boolean allJoinerPaid;
	private String collectionPoint;
	private long productId;
	private long userId;
}
