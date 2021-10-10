package com.groupbuysg.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupbuysg.comment.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{

	Comment findByCommentId(Long commentId);
	List<Comment> findByProductId(Long productId);
	
	

}
