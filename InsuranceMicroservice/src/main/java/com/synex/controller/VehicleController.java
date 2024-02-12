package com.synex.controller;


import com.synex.domain.Driver;
import com.synex.domain.Vehicle;
import com.synex.service.DriverService;
import com.synex.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleController {

	@Autowired VehicleService vehicleService;
	@Autowired DriverService driverService;
    
	@PostMapping("/submitVehicleForm")
    @CrossOrigin(origins = "http://localhost:8282")
    public void submitVehicleForm(@RequestBody Vehicle vehicle) {
    	
		Driver driver = driverService.findById(vehicle.getDriverId());

    	driver.setVehicle(vehicle);
      
        vehicleService.save(vehicle);
    }

    
   
}
