package com.synex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synex.domain.AutoInsurance;
import com.synex.domain.HealthInsurance;

public interface HealthInsuranceRepository extends JpaRepository<HealthInsurance, Integer> {

}
