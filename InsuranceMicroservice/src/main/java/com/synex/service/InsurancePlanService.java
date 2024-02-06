package com.synex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.synex.domain.InsurancePlan;


@Service
public interface InsurancePlanService {
	public InsurancePlan save(InsurancePlan insurancePlan);
    public InsurancePlan findById(Integer insurancePlanId);
    public List<InsurancePlan> findAll();
    public void deleteById(Integer insurancePlanId);
    boolean existsById(Integer insurancePlanId);
    public int getNextUserId();
    public List<InsurancePlan> getPlansByType(String insuranceType);
    List<InsurancePlan> getPlansByCompany(String company);
}
