package com.synex.controller;

import java.util.ArrayList;
import java.util.List;

import com.synex.domain.Address;
import com.synex.domain.Company;
import com.synex.domain.Driver;
import com.synex.domain.InsurancePlan;
import com.synex.service.CompanyService;
import com.synex.service.DriverService;
import com.synex.service.InsurancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverInfoController {

	@Autowired DriverService driverService;
	@Autowired InsurancePlanService insurancePlanService;
	
   
    @PostMapping("/submitDriverInfo")
    @CrossOrigin(origins = "http://localhost:8282")
    public ResponseEntity<Long> submitDriverInfo(@RequestParam String name, 
                                         @RequestParam int age,
                                         @RequestParam int drivingRecord,
                                         @RequestParam int vehicleValue,
                                         @RequestParam String street,
                                         @RequestParam String city,
                                         @RequestParam String state,
                                         @RequestParam String zipCode,
                                         @RequestParam String email,
                                         @RequestParam int insurancePlanId,
                                         @RequestParam Long userId) {
     	
    	Driver driver = new Driver();
    	driver.setName(name);
    	driver.setAge(age);
    	driver.setDrivingRecord(drivingRecord);
    	driver.setVehicleValue(vehicleValue);
    	driver.setUserId(userId);
    	driver.setStatus("Pending");
    	
    	Address address = new Address();
    	address.setEmail(email);
    	address.setStreet(street);
    	address.setCity(city);
    	address.setState(state);
    	address.setZipCode(zipCode);
    	
    	driver.setAddress(address);
    	System.out.println("insurancePLanId" + insurancePlanId);
    	InsurancePlan insurancePlan = insurancePlanService.findById(insurancePlanId);   
    	System.out.println("insurancePlan" + insurancePlan);
    	driver.setInsurancePlan(insurancePlan);
        
      
    
        Driver savedDriver = driverService.save(driver);
        

        return ResponseEntity.ok(savedDriver.getId());
    }
    
 

    
    

    
   
}
