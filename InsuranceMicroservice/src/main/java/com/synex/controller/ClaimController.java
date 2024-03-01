package com.synex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synex.domain.Claim;
import com.synex.domain.Company;
import com.synex.domain.Driver;
import com.synex.domain.InsurancePlan;
import com.synex.service.ClaimService;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ClaimController {

    @Autowired
    private ClaimService claimService;
    
    @Autowired
    private DriverService driverService;
    
    @Autowired EmailService emailService;
	 
	@Autowired ObjectMapper objectMapper;

    @PostMapping("/saveClaim")
    @CrossOrigin(origins = "http://localhost:8282")
    public ResponseEntity<String> saveClaim(@RequestParam("policyNumber") Long policyNumber,
                                            @RequestParam("amount") Long amount,
                                            @RequestParam("reason") String reason,
                                            @RequestPart("mishapImages") MultipartFile mishapImages) {
        try {
       
            Driver driver = driverService.findById(policyNumber);
            if (driver == null) {
               
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Driver not found");
            }
            driver.setClaimStatus("Pending");
            driverService.save(driver);
            
            
            byte[] mishapImagesBytes = mishapImages.getBytes();
            Claim claim = new Claim(mishapImagesBytes);
            claim.setPolicyNumber(policyNumber);
            claim.setAmount(amount);
            System.out.println(reason);
            claim.setReason(reason);
            claim.setClaimStatus("Pending");


            claimService.save(claim);
            
            return ResponseEntity.ok("Claim saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing mishap images");
        }
    }
    
    @GetMapping("/getPendingClaims")
    @CrossOrigin(origins = "http://localhost:8282")
    public List<Claim> getPendingClaims() {
      
        List<Claim> allClaims = claimService.findAll();
        
        
        List<Claim> pendingClaims = allClaims.stream()
                                             .filter(claim -> claim.getClaimStatus().equals("Pending"))
                                             .collect(Collectors.toList());
                                             
        return pendingClaims;
    }
    
    @GetMapping("/getRejectedClaims")
    @CrossOrigin(origins = "http://localhost:8282")
    public List<Claim> getRejectedClaims() {
        
        List<Claim> allClaims = claimService.findAll();
        
        
        List<Claim> rejectedClaims = allClaims.stream()
                                             .filter(claim -> claim.getClaimStatus().equals("Rejected"))
                                             .collect(Collectors.toList());
                                             
        return rejectedClaims;
    }
    
    @GetMapping("/getApprovedClaims")
    @CrossOrigin(origins = "http://localhost:8282")
    public List<Claim> getApprovedClaims() {
        
        List<Claim> allClaims = claimService.findAll();
        
        
        List<Claim> approvedClaims = allClaims.stream()
                                             .filter(claim -> claim.getClaimStatus().equals("Approved"))
                                             .collect(Collectors.toList());
                                             
        return approvedClaims;
    }
    
    @PostMapping("/approveClaim")
    @CrossOrigin(origins = "http://localhost:8282")
    public ResponseEntity<String> approveClaim(@RequestParam Long claimId, @RequestParam Long driverId) {
    	Driver driver = driverService.findById(driverId);
    	System.out.println("Driver" + driver.toString());
    	Long claimIdLong = Long.valueOf(claimId);
    	Claim claim = claimService.findById(claimIdLong);
        claim.setClaimStatus("Approved");
        System.out.println("Amount" + claim.getAmount());
        if(claim.getAmount() instanceof Long) {
			System.out.println("coverageAmount is Long");
		}
        claimService.save(claim);
        try {
			Driver savedDriver = driver;
			if(savedDriver != null) {
				System.out.println("savedDriver" + savedDriver.getId());
				CompletableFuture.runAsync(() -> {
					try {
						emailService.sendEmailBodyClaimApproval(savedDriver, claim.getAmount());
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
    
    @PostMapping("/rejectClaim")
    @CrossOrigin(origins = "http://localhost:8282")
    public ResponseEntity<String>  rejectClaim(@RequestParam Long claimId, @RequestParam Long driverId) {
    	Driver driver = driverService.findById(driverId);
    	Claim claim = claimService.findById(claimId);
        claim.setClaimStatus("Rejected");                              
        claimService.save(claim);
        try {
			Driver savedDriver = driver;
			if(savedDriver != null) {
				System.out.println("savedDriver" + savedDriver.getId());
				CompletableFuture.runAsync(() -> {
					try {
						emailService.sendEmailBodyClaimRejection(savedDriver, claim.getAmount());
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
    
    @GetMapping("/findDriverByPolicyNumber")
    @CrossOrigin(origins = "http://localhost:8282")
    public Driver findDriverByPolicyNumber(@RequestParam Long policyNumber) {
    
       return driverService.findById(policyNumber);
    }
    
    
    
   
}
