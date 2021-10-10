package com.groupbuysg.comment.valueobject;

import com.groupbuysg.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {

	private Comment comment;
	private User user;
}
