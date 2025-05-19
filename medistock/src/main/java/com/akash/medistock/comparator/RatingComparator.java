package com.akash.medistock.comparator;

import java.util.Comparator;
import java.util.List;

import com.akash.medistock.entity.Medicine;
import com.akash.medistock.entity.Review;

public class RatingComparator implements Comparator<Medicine> {

	@Override
	public int compare(Medicine o1, Medicine o2) {
		int sum1 = 0;
		List<Review> reviews = o1.getReviews();
		for (Review review : reviews) {
			sum1 += review.getRating();
		}
		int sum2 = 0;
		List<Review> reviews2 = o2.getReviews();
		for (Review review : reviews2) {
			sum2 += review.getRating();
		}
		if (sum1 / reviews.size() == sum2 / reviews2.size()) {
			return 0;
		} else if (sum1 / reviews.size() == sum2 / reviews2.size()) {
			return 1;
		} else {
			return -1;
		}
	}

}
