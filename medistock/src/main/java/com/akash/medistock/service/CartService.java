package com.akash.medistock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.medistock.entity.Cart;
import com.akash.medistock.repository.CartRepository;

@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;

	public Cart updateCart(Cart cart) {
		if (cart != null) {
			return cartRepository.save(cart);
		} else {
			return null;
		}
	}

}
