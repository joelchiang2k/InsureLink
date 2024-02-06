package com.synex.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synex.domain.Company;
import com.synex.repository.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired CompanyRepository companyRepository;
	
	@Override
	public Company save(Company company) {
		// TODO Auto-generated method stub
		return companyRepository.save(company);
	}

	@Override
	public Company findById(Integer companyId) {
		Optional<Company> optCompany= companyRepository.findById(companyId);
		if(optCompany.isPresent()) {
			return optCompany.get();
		}
		return null;
	}

	@Override
	public List<Company> findAll() {
		// TODO Auto-generated method stub
		return companyRepository.findAll();
	}

}
