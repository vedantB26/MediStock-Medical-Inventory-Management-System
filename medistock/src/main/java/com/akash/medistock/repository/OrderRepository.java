package com.akash.medistock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akash.medistock.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
