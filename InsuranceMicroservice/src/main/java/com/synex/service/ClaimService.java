package com.synex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.synex.domain.Claim;
import com.synex.domain.Company;
import com.synex.domain.InsurancePlan;


@Service
public interface ClaimService {
	public Claim save(Claim claim);
    public Claim findById(Long claimId);
    public List<Claim> findAll();
   
}
