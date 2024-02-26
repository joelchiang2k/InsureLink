package com.synex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synex.domain.Address;
import com.synex.domain.Company;
import com.synex.domain.Driver;
import com.synex.domain.InsurancePlan;
import com.synex.service.CompanyService;
import com.synex.service.DriverService;
import com.synex.service.EmailService;
import com.synex.service.InsurancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PaymentController {

	@Autowired DriverService driverService;
	@Autowired InsurancePlanService insurancePlanService;
	@Autowired RestTemplate restTemplate;
	@Autowired EmailService emailService;
	@Autowired ObjectMapper objectMapper;
    
	@GetMapping("/getDriverInfo")
	@CrossOrigin(origins = "http://localhost:8282")
	public Driver getDriverInfo(@RequestParam(name = "driverId") Long driverId) {
	    
	    Driver driver = driverService.findById(driverId);
	    
	    return driver;
	}
	
	@PostMapping("/createEmail")
	@CrossOrigin(origins = "http://localhost:8282")
	public ResponseEntity<String> createEmail(@RequestBody Driver driver){
		try {
			Driver savedDriver = driver;
			System.out.println("savedDriver" + savedDriver);
			
			System.out.println("driverObj" + savedDriver);
			if(savedDriver != null) {
				System.out.println("savedDriver" + savedDriver.getId());
				CompletableFuture.runAsync(() -> {
					try {
						emailService.sendDriverConfirmation(savedDriver);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				
				String savedDriverJson = objectMapper.writeValueAsString(savedDriver);
				return ResponseEntity.status(HttpStatus.CREATED).body(savedDriverJson);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No drivers for the specified criteria.");
			}
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON data: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating driver: " + e.getMessage());
		}
	}
    
    

    
   
}
