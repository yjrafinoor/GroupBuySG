package com.groupbuysg.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.groupbuysg.comment.entity.Comment;
import com.groupbuysg.comment.repository.CommentRepository;
import com.groupbuysg.comment.valueobject.ResponseObject;
import com.groupbuysg.comment.valueobject.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	public List<Comment> getCommentById(Long productId) {
		log.info("Inside getCommentById method of CommnetService");
		ResponseObject obj=new ResponseObject();
		List<Comment> comment=commentRepository.findByProductId(productId);	
		
		/*
		User user=
				restTemplate.getForObject("http://USER-SERVICE/users/list/" + comment.getUserId()
				, User.class);
		
		obj.setUser(user);
		*/
		
		return commentRepository.findByProductId(productId);
	}
	
	public Comment commentCreate(Long productId, Long userId, Comment comment) {
		log.info("Inside commentCreate method of CommnetService");
		comment.setProductId(productId);
		comment.setUserId(userId);
		comment.setCommentDate("DEFAULT DATE");
		return commentRepository.save(comment);
	}

}