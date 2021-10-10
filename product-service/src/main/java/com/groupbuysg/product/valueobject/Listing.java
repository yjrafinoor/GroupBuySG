package com.groupbuysg.product.valueobject;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Listing {
	
	@Id
	private Long listingId;
	private Integer totalQuantity;
	private Double  totalAmout;
	private Double amount10;
	private Double amount90;
	private String DateAmount10;
	private String DateAmount90;
	private String DateJoinerPaid;
	private String statusLeader;
	private String statusJoiner;
	private String DateJoin;
	private String statusAdmin;
	private Boolean isLeader;
	private Boolean allJoinerPaid;
	private Long productId;
	private Long userId;
	

}
