package com.synex.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AutoInsurance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int autoInsuranceId;
	 // Personal Information
    private String name;
    private String dateOfBirth;
    private String gender;
    private String address;
    private String phoneNumber;
    private String email;

    // Driver Information
    private String driverLicenseNumber;

    // Vehicle Information
    private String vehicleMakeAndModel;
    private String vehicleVIN;
    private int vehicleYear;
    private int vehicleMileage;

    // Insurance Coverage Details
    private boolean liabilityCoverage;
    private boolean collisionCoverage;
    private boolean comprehensiveCoverage;
    private boolean uninsuredMotoristCoverage;


}
