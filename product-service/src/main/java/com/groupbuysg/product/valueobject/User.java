package com.groupbuysg.product.valueobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	private long userId;
	private String userName;
	private String fullName;
	private String email;
	private String password;
	private String address;
	private String mrt;
	private String role;
	private String paynowName;
	private String paynowMobile;
	private String contact;
	//private LocalDateTime dateRegister;
	private String dateRegister;
	private Boolean requestLeader;
	private String dateBeLeader;
	//private Date dateBeLeader;
	private Boolean isActive;
	
}
