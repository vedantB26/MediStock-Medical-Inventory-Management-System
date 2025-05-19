package com.akash.medistock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akash.medistock.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findUserByEmailAndPassword(String email, String password);

	User findUserByMobileAndPassword(long mobile, String password);

}
