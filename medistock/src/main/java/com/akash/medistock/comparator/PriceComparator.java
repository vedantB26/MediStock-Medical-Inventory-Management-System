package com.akash.medistock.comparator;

import java.util.Comparator;

import com.akash.medistock.entity.Medicine;

public class PriceComparator implements Comparator<Medicine> {

	@Override
	public int compare(Medicine o1, Medicine o2) {
		if (o1.getPrice() == o2.getPrice()) {
			return 0;
		} else if (o1.getPrice() > o2.getPrice()) {
			return 1;
		} else {
			return -1;
		}
	}

}
