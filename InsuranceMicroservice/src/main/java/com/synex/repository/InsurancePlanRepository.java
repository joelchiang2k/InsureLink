package com.synex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synex.domain.InsurancePlan;

public interface InsurancePlanRepository extends JpaRepository<InsurancePlan, Integer> {
	List<InsurancePlan> findByInsuranceType(String insuranceType);
	List<InsurancePlan> findByCompany(String company);
}
