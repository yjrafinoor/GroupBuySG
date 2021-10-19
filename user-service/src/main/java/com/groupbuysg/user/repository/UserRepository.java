package com.groupbuysg.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupbuysg.user.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	User findByUserId(Long userId);
	List<User> findByRole(String role);
	User findByUserName(String userName);
	User findByEmail(String email);
}
