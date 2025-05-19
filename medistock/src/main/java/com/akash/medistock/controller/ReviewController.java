package com.akash.medistock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.akash.medistock.entity.Medicine;
import com.akash.medistock.exception.NotFoundException;
import com.akash.medistock.request.ReviewRequest;
import com.akash.medistock.response.ResponseStructure;
import com.akash.medistock.response.ReviewResponse;
import com.akash.medistock.service.MedicineService;
import com.akash.medistock.service.ReviewService;

@RestController
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private MedicineService medicineService;

	@PostMapping(path = "/reviews")
	public ResponseEntity<ResponseStructure<ReviewResponse>> addReview(@RequestBody ReviewRequest reviewRequest) {
		Medicine medicine = medicineService.findMedicineById(reviewRequest.getMedicineId());
		if (medicine != null) {
			ReviewResponse reviewResponse = reviewService.addReview(reviewRequest, medicine);
			ResponseStructure<ReviewResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("REVIEW ADDED");
			responseStructure.setHttpStatusCode(HttpStatus.CREATED.value());
			responseStructure.setData(reviewResponse);
			return new ResponseEntity<ResponseStructure<ReviewResponse>>(responseStructure, HttpStatus.CREATED);
		} else {
			throw new NotFoundException("MEDICINE NOT FOUND");
		}
	}

}
