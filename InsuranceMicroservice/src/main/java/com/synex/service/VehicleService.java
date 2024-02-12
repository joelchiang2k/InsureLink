package com.synex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.synex.domain.Company;
import com.synex.domain.InsurancePlan;
import com.synex.domain.Vehicle;


@Service
public interface VehicleService {
	public Vehicle save(Vehicle vehicle);
    public Vehicle findById(Long vehicleId);
    public List<Vehicle> findAll();
   
}
