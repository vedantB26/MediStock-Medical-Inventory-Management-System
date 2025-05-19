package com.akash.medistock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.akash.medistock.entity.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

	@Query(value = "SELECT medicine FROM Medicine medicine WHERE medicine.brand LIKE %:brand%")
	List<Medicine> findMedicinesByBrand(String brand);

}
