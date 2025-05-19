package com.akash.medistock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akash.medistock.entity.Medicine;
import com.akash.medistock.entity.User;
import com.akash.medistock.exception.AccessDeniedException;
import com.akash.medistock.exception.NotFoundException;
import com.akash.medistock.request.MedicineRequest;
import com.akash.medistock.response.MedicineResponse;
import com.akash.medistock.response.ResponseStructure;
import com.akash.medistock.service.MedicineService;
import com.akash.medistock.service.UserService;

@RestController
public class MedicineController {

	@Autowired
	private MedicineService medicineService;

	@Autowired
	private UserService userService;

	@GetMapping(path = "/medicines")
	public ResponseEntity<ResponseStructure<List<MedicineResponse>>> findAllMedicines() {
		List<MedicineResponse> medicineResponses = medicineService.findAllMedicines();
		if (medicineResponses != null) {
			ResponseStructure<List<MedicineResponse>> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("MEDICINES FOUND");
			responseStructure.setHttpStatusCode(HttpStatus.FOUND.value());
			responseStructure.setData(medicineResponses);
			return new ResponseEntity<ResponseStructure<List<MedicineResponse>>>(responseStructure, HttpStatus.FOUND);
		} else {
			throw new NotFoundException("MEDICINES NOT AVAILABLE");
		}
	}

	@GetMapping(path = "/medicines/sort/price")
	public ResponseEntity<ResponseStructure<List<MedicineResponse>>> sortMedicinesByPrice() {
		List<MedicineResponse> medicineResponses = medicineService.sortMedicinesByPrice();
		if (medicineResponses != null) {
			ResponseStructure<List<MedicineResponse>> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("MEDICINES FETCHED");
			responseStructure.setHttpStatusCode(HttpStatus.FOUND.value());
			responseStructure.setData(medicineResponses);
			return new ResponseEntity<ResponseStructure<List<MedicineResponse>>>(responseStructure, HttpStatus.FOUND);
		} else {
			throw new NotFoundException("MEDICINES NOT AVAILABLE");
		}
	}

	@GetMapping(path = "/medicines/sort/rating")
	public ResponseEntity<ResponseStructure<List<MedicineResponse>>> sortMedicinesByRating() {
		List<MedicineResponse> medicineResponses = medicineService.sortMedicinesByRating();
		if (medicineResponses != null) {
			ResponseStructure<List<MedicineResponse>> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("MEDICINES FETCHED");
			responseStructure.setHttpStatusCode(HttpStatus.FOUND.value());
			responseStructure.setData(medicineResponses);
			return new ResponseEntity<ResponseStructure<List<MedicineResponse>>>(responseStructure, HttpStatus.FOUND);
		} else {
			throw new NotFoundException("MEDICINES NOT AVAILABLE");
		}
	}

	@GetMapping(path = "/medicines/{brand}")
	public ResponseEntity<ResponseStructure<List<MedicineResponse>>> findMedicinesByBrand(@PathVariable String brand) {
		List<MedicineResponse> medicineResponses = medicineService.findMedicinesByBrand(brand);
		if (medicineResponses != null) {
			ResponseStructure<List<MedicineResponse>> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("MEDICINES FETCHED");
			responseStructure.setHttpStatusCode(HttpStatus.FOUND.value());
			responseStructure.setData(medicineResponses);
			return new ResponseEntity<ResponseStructure<List<MedicineResponse>>>(responseStructure, HttpStatus.FOUND);
		} else {
			throw new NotFoundException("MEDICINES NOT AVAILABLE");
		}
	}

	@PostMapping(path = "/medicines")
	public ResponseEntity<ResponseStructure<MedicineResponse>> addMedicine(
			@RequestBody MedicineRequest medicineRequest) {
		User user = userService.findUserById(medicineRequest.getUserId());
		if (user != null && user.getRole().equals("ADMIN")) {
			MedicineResponse medicineResponse = medicineService.addMedicine(medicineRequest);
			ResponseStructure<MedicineResponse> responseStructure = new ResponseStructure<>();
			responseStructure.setMessage("MEDICINE ADDED");
			responseStructure.setHttpStatusCode(HttpStatus.CREATED.value());
			responseStructure.setData(medicineResponse);
			return new ResponseEntity<ResponseStructure<MedicineResponse>>(responseStructure, HttpStatus.CREATED);
		} else {
			throw new AccessDeniedException("UNAUTHORIZED USER");
		}
	}

	@PutMapping(path = "/medicines")
	public ResponseEntity<ResponseStructure<MedicineResponse>> updateMedicine(
			@RequestParam(name = "userId") long userId, @RequestBody Medicine medicine) {
		User user = userService.findUserById(userId);
		if (user != null && user.getRole().equals("ADMIN")) {
			MedicineResponse updatedMedicine = medicineService.updateMedicine(medicine);
			if (updatedMedicine != null) {
				ResponseStructure<MedicineResponse> responseStructure = new ResponseStructure<>();
				responseStructure.setMessage("MEDICINE UPDATED");
				responseStructure.setHttpStatusCode(HttpStatus.CREATED.value());
				responseStructure.setData(updatedMedicine);
				return new ResponseEntity<ResponseStructure<MedicineResponse>>(responseStructure, HttpStatus.CREATED);
			} else {
				throw new NotFoundException("INVALID ID OF THE MEDICINE");
			}
		} else {
			throw new AccessDeniedException("UNAUTHORIZED USER");
		}
	}

	@DeleteMapping(path = "/medicines")
	public ResponseEntity<ResponseStructure<MedicineResponse>> deleteMedicine(
			@RequestParam(name = "userId") long userId, @RequestParam(name = "id") long id) {
		User user = userService.findUserById(userId);
		if (user != null && user.getRole().equals("ADMIN")) {
			MedicineResponse deletedMedicine = medicineService.deleteMedicine(id);
			if (deletedMedicine != null) {
				ResponseStructure<MedicineResponse> responseStructure = new ResponseStructure<>();
				responseStructure.setMessage("MEDICINE DELETED");
				responseStructure.setHttpStatusCode(HttpStatus.OK.value());
				responseStructure.setData(deletedMedicine);
				return new ResponseEntity<ResponseStructure<MedicineResponse>>(responseStructure, HttpStatus.OK);
			} else {
				throw new NotFoundException("INVALID ID OF THE MEDICINE");
			}
		} else {
			throw new AccessDeniedException("UNAUTHORIZED USER");
		}
	}

}
