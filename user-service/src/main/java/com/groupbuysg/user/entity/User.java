package com.groupbuysg.user.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
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
	
}
