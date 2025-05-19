package com.akash.medistock.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.medistock.entity.Cart;
import com.akash.medistock.entity.Medicine;
import com.akash.medistock.entity.Order;
import com.akash.medistock.entity.User;
import com.akash.medistock.repository.CartRepository;
import com.akash.medistock.repository.UserRepository;
import com.akash.medistock.request.UserRequest;
import com.akash.medistock.response.UserResponse;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	public UserResponse addUser(UserRequest userRequest) {
		User user = new User();
		user.setEmail(userRequest.getEmail());
		user.setMobile(userRequest.getMobile());
		user.setPassword(userRequest.getPassword());
		if (userRequest.getRole().equalsIgnoreCase("USER"))
			user.setRole("USER");
		else if (userRequest.getRole().equalsIgnoreCase("ADMIN"))
			user.setRole("ADMIN");
		user.setOrders(new ArrayList<Order>());
		Cart cart = new Cart();
		cart.setMedicines(new ArrayList<Medicine>());
		Cart addedCart = cartRepository.save(cart);
		user.setCart(addedCart);
		User addedUser = userRepository.save(user);
		return mapUserToUserResponse(addedUser);
	}

	public User findUserById(long id) {
		Optional<User> optional = userRepository.findById(id);
		if (optional.isPresent()) {
			User user = optional.get();
			return user;
		} else {
			return null;
		}
	}

	public UserResponse findUserByEmailAndPassword(String email, String password) {
		User user = userRepository.findUserByEmailAndPassword(email, password);
		if (user != null)
			return mapUserToUserResponse(user);
		else
			return null;
	}

	public UserResponse findUserByMobileAndPassword(long mobile, String password) {
		User user = userRepository.findUserByMobileAndPassword(mobile, password);
		if (user != null)
			return mapUserToUserResponse(user);
		else
			return null;
	}

	public User updateUserCart(User user) {
		if (user != null) {
			Optional<User> optional = userRepository.findById(user.getId());
			if (optional.isPresent()) {
				user.setCart(optional.get().getCart());
				return user;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public UserResponse updateUserDetails(User user) {
		Optional<User> optional = userRepository.findById(user.getId());
		if (optional.isPresent()) {
			User oldUser = optional.get();
			user.setRole(oldUser.getRole());
			user.setCart(oldUser.getCart());
			user.setOrders(oldUser.getOrders());
			return mapUserToUserResponse(userRepository.save(user));
		} else
			return null;

	}

	public UserResponse deleteUser(long id) {
		Optional<User> optional = userRepository.findById(id);
		if (optional.isPresent()) {
			User user = optional.get();
			userRepository.delete(user);
			return mapUserToUserResponse(user);
		} else {
			return null;
		}
	}

	private UserResponse mapUserToUserResponse(User user) {
		UserResponse userResponse = new UserResponse();
		userResponse.setEmail(user.getEmail());
		userResponse.setMobile(user.getMobile());
		userResponse.setPassword(user.getPassword());
		userResponse.setRole(user.getRole());
		userResponse.setOrders(user.getOrders());
		userResponse.setCart(user.getCart());
		return userResponse;
	}

}
