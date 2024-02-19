package com.synex.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synex.domain.Claim;
import com.synex.domain.Company;
import com.synex.repository.ClaimRepository;

@Service
public class ClaimServiceImpl implements ClaimService {
	
	@Autowired ClaimRepository claimRepository;
	
	@Override
	public Claim save(Claim claim) {
		return claimRepository.save(claim);
	}

	@Override
	public Claim findById(Long claimId) {
		Optional<Claim> optClaim= claimRepository.findById(claimId);
		if(optClaim.isPresent()) {
			return optClaim.get();
		}
		return null;
	}

	@Override
	public List<Claim> findAll() {
		return claimRepository.findAll();
	}

}
