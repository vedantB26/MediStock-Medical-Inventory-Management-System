package com.akash.medistock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akash.medistock.entity.User;
import com.akash.medistock.exception.NotFoundException;
import com.akash.medistock.request.UserRequest;
import com.akash.medistock.response.ResponseStructure;
import com.akash.medistock.response.UserResponse;
import com.akash.medistock.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(path = "/users")
	public ResponseEntity<ResponseStructure<UserResponse>> addUser(@RequestBody UserRequest userRequest) {
		UserResponse userResponse = userService.addUser(userRequest);
		ResponseStructure<UserResponse> responseStructure = new ResponseStructure<>();
		responseStructure.setMessage("USER ADDED");
		responseStructure.setHttpStatusCode(HttpStatus.CREATED.value());
		responseStructure.setData(userResponse);
		return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.CREATED);
	}

	@GetMapping(path = "/users")
	public ResponseEntity<ResponseStructure<UserResponse>> findUserByEmailAndPassword(
			@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {
		UserResponse userResponse = userService.findUserByEmailAndPassword(email, password);
		if (userResponse != null && userResponse.getEmail().equals(email) && userResponse.getPassword().equals(password)) {
			ResponseStructure<UserResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("USER FETCHED");
			responseStructure.setHttpStatusCode(HttpStatus.FOUND.value());
			responseStructure.setData(userResponse);
			return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.FOUND);
		} else {
			throw new NotFoundException("INVALID EMAIL OR PASSWORD");
		}
	}

	@GetMapping(path = "/users/{mobile}/{password}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUserByMobileAndPassword(@PathVariable long mobile,
			@PathVariable String password) {
		UserResponse userResponse = userService.findUserByMobileAndPassword(mobile, password);
		if (userResponse != null && userResponse.getMobile() == mobile && userResponse.getPassword().equals(password)) {
			ResponseStructure<UserResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("USER FETCHED");
			responseStructure.setHttpStatusCode(HttpStatus.FOUND.value());
			responseStructure.setData(userResponse);
			return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.FOUND);
		} else {
			throw new NotFoundException("INVALID MOBILE NUMBER OR PASSWORD");
		}
	}

	@PutMapping(path = "/users")
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(@RequestBody User user) {
		UserResponse userResponse = userService.updateUserDetails(user);
		if (userResponse != null) {
			ResponseStructure<UserResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("USER UPDATED");
			responseStructure.setHttpStatusCode(HttpStatus.CREATED.value());
			responseStructure.setData(userResponse);
			return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.CREATED);
		} else {
			throw new NotFoundException("INVALID ID OF THE USER");
		}
	}

	@DeleteMapping(path = "/users")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@RequestParam(name = "id") long id) {
		UserResponse userResponse = userService.deleteUser(id);
		if (userResponse != null) {
			ResponseStructure<UserResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("USER DELETED");
			responseStructure.setHttpStatusCode(HttpStatus.OK.value());
			responseStructure.setData(userResponse);
			return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure, HttpStatus.OK);
		} else {
			throw new NotFoundException("INVALID ID OF THE USER");
		}
	}

}
