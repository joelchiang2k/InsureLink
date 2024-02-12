package com.synex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.synex.domain.Company;
import com.synex.domain.Driver;
import com.synex.domain.InsurancePlan;


@Service
public interface DriverService {
	public Driver save(Driver driver);
    public Driver findById(Long driverId);
    public List<Driver> findAll();

   
}
