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
public class PaymentController {

	@Autowired DriverService driverService;
	@Autowired InsurancePlanService insurancePlanService;
	
    
	@GetMapping("/getDriverInfo")
	@CrossOrigin(origins = "http://localhost:8282")
	public Driver getDriverInfo(@RequestParam(name = "driverId") Long driverId) {
	    
	    Driver driver = driverService.findById(driverId);
	    
	    return driver;
	}
    
    

    
   
}
