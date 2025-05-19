package com.akash.medistock.request;

import lombok.Getter;

@Getter
public class ReviewRequest {

	private int rating;
	private String comment;
	private long medicineId;

}
