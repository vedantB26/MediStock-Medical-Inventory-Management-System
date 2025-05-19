package com.akash.medistock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akash.medistock.entity.Cart;
import com.akash.medistock.entity.Medicine;
import com.akash.medistock.entity.User;
import com.akash.medistock.exception.AccessDeniedException;
import com.akash.medistock.exception.InvalidRequestException;
import com.akash.medistock.exception.NotFoundException;
import com.akash.medistock.response.ResponseStructure;
import com.akash.medistock.response.UserResponse;
import com.akash.medistock.service.CartService;
import com.akash.medistock.service.MedicineService;
import com.akash.medistock.service.UserService;

@RestController
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@Autowired
	private MedicineService medicineService;

	@PutMapping(path = "/cart-a")
	public ResponseEntity<ResponseStructure<UserResponse>> addMedicineToCart(@RequestParam(name = "userId") long userId,
			@RequestParam(name = "medicineId") long medicineId) {
		User user = userService.findUserById(userId);
		if (user != null && user.getRole().equals("USER")) {
			Medicine medicine = medicineService.findMedicineById(medicineId);
			if (medicine != null) {
				Cart cart = user.getCart();
				List<Medicine> medicines = cart.getMedicines();
				if (!medicines.contains(medicine)) {
					medicines.add(medicine);
					cart.setMedicines(medicines);
					Cart updatedCart = cartService.updateCart(cart);
					user.setCart(updatedCart);
					User updatedUser = userService.updateUserCart(user);
					UserResponse userResponse = new UserResponse();
					userResponse.setEmail(updatedUser.getEmail());
					userResponse.setMobile(updatedUser.getMobile());
					userResponse.setPassword(updatedUser.getPassword());
					userResponse.setRole(updatedUser.getRole());
					userResponse.setCart(updatedUser.getCart());
					userResponse.setOrders(updatedUser.getOrders());
					ResponseStructure<UserResponse> responseStructure = new ResponseStructure<>();
					responseStructure.setMessage("MEDICINE ADDED TO CART");
					responseStructure.setHttpStatusCode(HttpStatus.CREATED.value());
					responseStructure.setData(userResponse);
					return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.CREATED);
				} else {
					throw new InvalidRequestException("MEDICINE ALREADY ADDED TO CART");
				}
			} else {
				throw new NotFoundException("INVALID ID OF THE MEDICINE");
			}
		} else {
			throw new AccessDeniedException("UNAUTHORIZED USER");
		}
	}

	@PutMapping(path = "/cart-d")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteMedicineFromCart(
			@RequestParam(name = "userId") long userId, @RequestParam(name = "medicineId") long medicineId) {
		User user = userService.findUserById(userId);
		if (user != null && user.getRole().equals("USER")) {
			Medicine medicine = medicineService.findMedicineById(medicineId);
			if (medicine != null) {
				Cart cart = user.getCart();
				List<Medicine> medicines = cart.getMedicines();
				medicines.remove(medicine);
				cart.setMedicines(medicines);
				Cart updatedCart = cartService.updateCart(cart);
				user.setCart(updatedCart);
				User updatedUser = userService.updateUserCart(user);
				UserResponse userResponse = new UserResponse();
				userResponse.setEmail(updatedUser.getEmail());
				userResponse.setMobile(updatedUser.getMobile());
				userResponse.setPassword(updatedUser.getPassword());
				userResponse.setRole(updatedUser.getRole());
				userResponse.setCart(updatedUser.getCart());
				userResponse.setOrders(updatedUser.getOrders());
				ResponseStructure<UserResponse> responseStructure = new ResponseStructure<>();
				responseStructure.setMessage("MEDICINE DELETED FROM CART");
				responseStructure.setHttpStatusCode(HttpStatus.OK.value());
				responseStructure.setData(userResponse);
				return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.OK);
			} else {
				throw new NotFoundException("INVALID ID OF THE MEDICINE");
			}
		} else {
			throw new AccessDeniedException("UNAUTHORIZED USER");
		}
	}

}
