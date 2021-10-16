package com.groupbuysg.portal.valueobject;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	
	@Id
	private long commentId;
	private String commentDate;
	private String commentText;
	private long productId;
	private long userId;
	
}
