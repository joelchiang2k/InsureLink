package com.synex.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.synex.domain.Driver;
import com.synex.repository.DriverRepository;

@Service
public class DriverServiceImpl implements DriverService {
	
	@Autowired DriverRepository driverRepository;
	
	@Override
	public Driver save(Driver driver) {
		// TODO Auto-generated method stub
		return driverRepository.save(driver);
	}

	@Override
	public Driver findById(Long driverId) {
		
		Optional<Driver> optDriver= driverRepository.findById(driverId);
		if(optDriver.isPresent()) {
			return optDriver.get();
		}
		return null;
	}

	@Override
	public List<Driver> findAll() {
		// TODO Auto-generated method stub
		return driverRepository.findAll();
	}
	


}
