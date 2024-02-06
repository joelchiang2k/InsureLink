package com.synex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.synex.domain.Company;
import com.synex.domain.InsurancePlan;


@Service
public interface CompanyService {
	public Company save(Company company);
    public Company findById(Integer companyId);
    public List<Company> findAll();
   
}
