package com.akash.medistock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.akash.medistock.exception.InvalidRequestException;
import com.akash.medistock.request.OrderRequest;
import com.akash.medistock.response.OrderResponse;
import com.akash.medistock.response.ResponseStructure;
import com.akash.medistock.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping(path = "/orders")
	public ResponseEntity<ResponseStructure<OrderResponse>> addOrder(@RequestBody OrderRequest orderRequest) {
		OrderResponse orderResponse = orderService.addOrder(orderRequest);
		if (orderResponse != null) {
			ResponseStructure<OrderResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("ORDER ADDED");
			responseStructure.setHttpStatusCode(HttpStatus.CREATED.value());
			responseStructure.setData(orderResponse);
			return new ResponseEntity<ResponseStructure<OrderResponse>>(responseStructure, HttpStatus.CREATED);
		} else {
			throw new InvalidRequestException("INVALID USER ID OR MEDICINE ID");
		}
	}

}
