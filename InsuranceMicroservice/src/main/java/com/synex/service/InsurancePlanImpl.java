package com.synex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synex.domain.InsurancePlan;
import com.synex.repository.InsurancePlanRepository;

@Service
public class InsurancePlanImpl implements InsurancePlanService {
	
	@Autowired
    private InsurancePlanRepository insurancePlanRepository;
	
	@Override
	public InsurancePlan save(InsurancePlan insurancePlan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InsurancePlan findById(Integer insurancePlanId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InsurancePlan> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Integer insurancePlanId) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean existsById(Integer insurancePlanId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNextUserId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<InsurancePlan> getPlansByType(String insuranceType) {
        // Implement logic to fetch plans based on the provided insurance type
        // For example, you can use the repository to query the database
        // This is a placeholder implementation
        
        // Assuming you have a method in your repository to fetch plans by type
        List<InsurancePlan> plans = insurancePlanRepository.findByInsuranceType(insuranceType);
        
        return plans;
    }
	
	 @Override
	    public List<InsurancePlan> getPlansByCompany(String company) {
	        // Implement logic to fetch plans based on the provided company
	        // For example, you can use the repository to query the database
	        // This is a placeholder implementation
	        
	        // Assuming you have a method in your repository to fetch plans by company
	        List<InsurancePlan> plans = insurancePlanRepository.findByCompany(company);
	        
	        return plans;
	    }

}
