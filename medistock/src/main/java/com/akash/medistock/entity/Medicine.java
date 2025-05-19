package com.akash.medistock.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "medicines")
@Getter
@Setter
public class Medicine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, unique = true)
	private String name;
	@Column(nullable = false, unique = false)
	private String brand;
	@Column(nullable = false, unique = false)
	private String about;
	@Column(nullable = false, unique = false)
	private double price;
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Review> reviews;

}
