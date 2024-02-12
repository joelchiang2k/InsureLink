package com.synex.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synex.domain.Driver;
import com.synex.domain.Vehicle;
import com.synex.repository.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService {
	
	@Autowired VehicleRepository vehicleRepository;
	
	@Override
	public Vehicle save(Vehicle vehicle) {
		// TODO Auto-generated method stub
		return vehicleRepository.save(vehicle);
	}

	@Override
	public Vehicle findById(Long vehicleId) {
		// TODO Auto-generated method stub
		Optional<Vehicle> optVehicle= vehicleRepository.findById(vehicleId);
		if(optVehicle.isPresent()) {
			return optVehicle.get();
		}
		return null;
	}

	@Override
	public List<Vehicle> findAll() {
		// TODO Auto-generated method stub
		return vehicleRepository.findAll();
	}

}
