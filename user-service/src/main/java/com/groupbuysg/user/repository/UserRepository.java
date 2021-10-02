package com.groupbuysg.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupbuysg.user.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	User findByUserId(long userId);

}
