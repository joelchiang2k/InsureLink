package com.synex.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synex.domain.Driver;
import com.synex.domain.InsurancePlan;
import com.synex.repository.InsurancePlanRepository;

@Service
public class InsurancePlanImpl implements InsurancePlanService {
	
	@Autowired
    private InsurancePlanRepository insurancePlanRepository;
	
	@Override
	public InsurancePlan save(InsurancePlan insurancePlan) {
		// TODO Auto-generated method stub
		return insurancePlanRepository.save(insurancePlan);
	}

	@Override
	public InsurancePlan findById(Integer insurancePlanId) {
		Optional<InsurancePlan> optInsurancePlan = insurancePlanRepository.findById(insurancePlanId);
		if(optInsurancePlan.isPresent()) {
			return optInsurancePlan.get();
		}
		return null;
	}

	@Override
	public List<InsurancePlan> findAll() {
		// TODO Auto-generated method stub
		return insurancePlanRepository.findAll();
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
      
        List<InsurancePlan> plans = insurancePlanRepository.findByInsuranceType(insuranceType);
        
        return plans;
    }
	
	 @Override
	    public List<InsurancePlan> getPlansByCompany(String company) {
	       
	        List<InsurancePlan> plans = insurancePlanRepository.findByCompany(company);
	        
	        return plans;
	    }

}
