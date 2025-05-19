package com.akash.medistock.request;

import lombok.Getter;

@Getter
public class OrderRequest {

	private String status;
	private long medicineId;
	private long userId;

}
