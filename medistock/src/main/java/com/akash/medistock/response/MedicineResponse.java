package com.akash.medistock.response;

import java.util.List;

import com.akash.medistock.entity.Review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicineResponse {

	private String name;
	private String brand;
	private String about;
	private double price;
	private float avgRating;
	private List<Review> reviews;

}
