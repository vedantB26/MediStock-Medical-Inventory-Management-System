package com.akash.medistock.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.medistock.comparator.PriceComparator;
import com.akash.medistock.comparator.RatingComparator;
import com.akash.medistock.entity.Cart;
import com.akash.medistock.entity.Medicine;
import com.akash.medistock.entity.Order;
import com.akash.medistock.entity.Review;
import com.akash.medistock.repository.CartRepository;
import com.akash.medistock.repository.MedicineRepository;
import com.akash.medistock.repository.OrderRepository;
import com.akash.medistock.request.MedicineRequest;
import com.akash.medistock.response.MedicineResponse;

@Service
public class MedicineService {

	@Autowired
	private MedicineRepository medicineRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartRepository cartRepository;

	public MedicineResponse addMedicine(MedicineRequest medicineRequest) {
		Medicine medicine = new Medicine();
		medicine.setName(medicineRequest.getName());
		medicine.setBrand(medicineRequest.getBrand());
		medicine.setAbout(medicineRequest.getAbout());
		medicine.setPrice(medicineRequest.getPrice());
		medicine.setReviews(new ArrayList<Review>());
		Medicine addedMedicine = medicineRepository.save(medicine);
		return mapMedicineToMedicineResponse(addedMedicine);
	}

	public List<MedicineResponse> findAllMedicines() {
		List<Medicine> medicines = medicineRepository.findAll();
		if (medicines.size() > 0) {
			return mapMedicinesToMedicineResponses(medicines);
		} else {
			return null;
		}
	}

	public Medicine findMedicineById(long id) {
		Optional<Medicine> optional = medicineRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}

	}

	public List<MedicineResponse> sortMedicinesByPrice() {
		List<Medicine> medicines = medicineRepository.findAll();
		if (medicines.size() > 0) {
			Collections.sort(medicines, new PriceComparator());
			return mapMedicinesToMedicineResponses(medicines);
		} else {
			return null;
		}
	}

	public List<MedicineResponse> sortMedicinesByRating() {
		List<Medicine> medicines = medicineRepository.findAll();
		if (medicines.size() > 0) {
			Collections.sort(medicines, new RatingComparator());
			return mapMedicinesToMedicineResponses(medicines);
		} else {
			return null;
		}
	}

	public MedicineResponse updateMedicine(Medicine medicine) {
		if (medicine != null) {
			Optional<Medicine> optional = medicineRepository.findById(medicine.getId());
			if (optional.isPresent()) {
				Medicine oldMedicine = optional.get();
				medicine.setReviews(oldMedicine.getReviews());
				return mapMedicineToMedicineResponse(medicineRepository.save(medicine));
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public MedicineResponse deleteMedicine(long id) {
		Optional<Medicine> optional = medicineRepository.findById(id);
		if (optional.isPresent()) {
			Medicine medicine = optional.get();
			List<Order> orders = orderRepository.findAll();
			for (Order order : orders) {
				if (order.getMedicine().getId() == id)
					order.setMedicine(null);
				orderRepository.save(order);
			}
			List<Cart> carts = cartRepository.findAll();
			for (Cart cart : carts) {
				List<Medicine> medicines = cart.getMedicines();
				Medicine medicineToBeDeleted = null;
				for (Medicine m : medicines) {
					if (m.getId() == id)
						medicineToBeDeleted = m;
				}
				if (medicineToBeDeleted != null)
					medicines.remove(medicineToBeDeleted);
				cart.setMedicines(medicines);
				cartRepository.save(cart);
			}
			medicineRepository.delete(medicine);
			return mapMedicineToMedicineResponse(medicine);
		} else {
			return null;
		}
	}

	public List<MedicineResponse> findMedicinesByBrand(String brand) {
		List<Medicine> medicines = medicineRepository.findMedicinesByBrand(brand);
		if (medicines.size() > 0) {
			return mapMedicinesToMedicineResponses(medicines);
		} else {
			return null;
		}
	}

	private MedicineResponse mapMedicineToMedicineResponse(Medicine medicine) {
		MedicineResponse medicineResponse = new MedicineResponse();
		medicineResponse.setName(medicine.getName());
		medicineResponse.setBrand(medicine.getBrand());
		medicineResponse.setAbout(medicine.getAbout());
		medicineResponse.setPrice(medicine.getPrice());
		medicineResponse.setReviews(medicine.getReviews());
		return medicineResponse;
	}

	private List<MedicineResponse> mapMedicinesToMedicineResponses(List<Medicine> medicines) {
		List<MedicineResponse> medicineResponses = new ArrayList<MedicineResponse>();
		for (Medicine medicine : medicines) {
			MedicineResponse medicineResponse = new MedicineResponse();
			medicineResponse.setName(medicine.getName());
			medicineResponse.setBrand(medicine.getBrand());
			medicineResponse.setAbout(medicine.getAbout());
			medicineResponse.setPrice(medicine.getPrice());
			List<Review> reviews = medicine.getReviews();
			float sum = 0;
			for (Review review : reviews) {
				sum += review.getRating();
			}
			medicineResponse.setAvgRating(sum / reviews.size());
			medicineResponse.setReviews(medicine.getReviews());
			medicineResponses.add(medicineResponse);
		}
		return medicineResponses;
	}
}
