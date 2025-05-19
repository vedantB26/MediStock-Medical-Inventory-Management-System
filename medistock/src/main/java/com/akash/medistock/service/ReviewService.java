package com.akash.medistock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.medistock.entity.Medicine;
import com.akash.medistock.entity.Review;
import com.akash.medistock.repository.MedicineRepository;
import com.akash.medistock.repository.ReviewRepository;
import com.akash.medistock.request.ReviewRequest;
import com.akash.medistock.response.ReviewResponse;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private MedicineRepository medicineRepository;

	public ReviewResponse addReview(ReviewRequest reviewRequest, Medicine medicine) {
		Review review = new Review();
		review.setRating(reviewRequest.getRating());
		review.setComment(reviewRequest.getComment());
		Review addedReview = reviewRepository.save(review);
		List<Review> reviews = medicine.getReviews();
		reviews.add(addedReview);
		medicine.setReviews(reviews);
		medicineRepository.save(medicine);
		ReviewResponse reviewResponse = new ReviewResponse();
		reviewResponse.setComment(addedReview.getComment());
		reviewResponse.setRating(addedReview.getRating());
		return reviewResponse;
	}

}
