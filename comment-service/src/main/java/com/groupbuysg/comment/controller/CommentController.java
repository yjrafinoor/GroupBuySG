package com.groupbuysg.comment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupbuysg.comment.entity.Comment;
import com.groupbuysg.comment.service.CommentService;
import com.groupbuysg.comment.valueobject.ResponseObject;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/comments")
@Slf4j
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	@GetMapping("/{pid}")
	public List<Comment> getCommentById(@PathVariable("pid") Long productId){
		log.info("Inside getCommentById method of CommentController");
		return commentService.getCommentById(productId);
	}
	
	@PostMapping("/create/{pid}/{uid}")
	public Comment commentCreate (@PathVariable("pid") Long productId, @PathVariable("uid") Long userId, @RequestBody Comment comment){
		log.info("Inside commentCreate method of CommentController");
		return commentService.commentCreate(productId, userId, comment);
	}
	
}


