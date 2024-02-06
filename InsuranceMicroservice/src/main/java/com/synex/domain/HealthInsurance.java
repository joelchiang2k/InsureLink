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
public class HealthInsurance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int healthInsuranceId;
	// Personal Information
    private String name;
    private String dateOfBirth;
    private String gender;
    private String address;
    private String phoneNumber;
    private String email;

    // Employment Information
    private String employmentStatus;
    private String employerName;
    private String occupation;

    // Health Information
    private boolean smoker;
    private boolean preExistingConditions;
    private boolean chronicIllnesses;

    // Insurance Coverage Details
    private boolean individualCoverage;
    private boolean familyCoverage;
    private boolean dentalCoverage;
    private boolean visionCoverage;
}
