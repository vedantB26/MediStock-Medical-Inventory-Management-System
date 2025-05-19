package com.akash.medistock.response;

import java.util.Date;

import com.akash.medistock.entity.Medicine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

	private int number;
	private Date date;
	private String status;
	private Medicine medicine;

}
