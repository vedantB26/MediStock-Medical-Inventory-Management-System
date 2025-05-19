package com.akash.medistock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akash.medistock.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
