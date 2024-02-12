package com.synex.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Insured {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String dob;
	
	private String phone;
	
	private Integer drivingRecord;
	
	private String status;
	
	@OneToOne()
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Document document;
	
	@OneToOne()
	@JoinColumn(name = "autoInsurance_id")
    private AutoInsurance autoInsurance;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getDrivingRecord() {
		return drivingRecord;
	}

	public void setDrivingRecord(Integer drivingRecord) {
		this.drivingRecord = drivingRecord;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public AutoInsurance getAutoInsurance() {
		return autoInsurance;
	}

	public void setAutoInsurance(AutoInsurance autoInsurance) {
		this.autoInsurance = autoInsurance;
	}
	
}
