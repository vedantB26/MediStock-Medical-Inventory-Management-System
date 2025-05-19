package com.akash.medistock.response;

import java.util.List;

import com.akash.medistock.entity.Cart;
import com.akash.medistock.entity.Order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

	private String email;
	private long mobile;
	private String password;
	private String role;
	private Cart cart;
	private List<Order> orders;

}
