package com.synex.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.synex.domain.Claim;
import com.synex.domain.Company;
import com.synex.domain.Driver;
import com.synex.domain.InsurancePlan;
import com.synex.service.ClaimService;
import com.synex.service.CompanyService;
import com.synex.service.DriverService;
import com.synex.service.InsurancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClaimController {

    @Autowired
    private ClaimService claimService;
    
    @Autowired
    private DriverService driverService;

    @PostMapping("/saveClaim")
    @CrossOrigin(origins = "http://localhost:8282")
    public void saveClaim(@RequestBody Claim claim) {
       System.out.println(claim.getAmount());
       Driver driver = driverService.findById(claim.getPolicyNumber());
       driver.setClaimStatus("Pending");
       
       claim.setClaimStatus("Pending");
       
       claimService.save(claim);
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
    public void approveClaim(@RequestParam Long claimId) {
    	Claim claim = claimService.findById(claimId);
        claim.setClaimStatus("Approved");
                                             
        claimService.save(claim);
    }
    
    @PostMapping("/rejectClaim")
    @CrossOrigin(origins = "http://localhost:8282")
    public void rejectClaim(@RequestParam Long claimId) {
    	Claim claim = claimService.findById(claimId);
        claim.setClaimStatus("Rejected");
                                             
        claimService.save(claim);
    }
    
    @GetMapping("/findDriverByPolicyNumber")
    @CrossOrigin(origins = "http://localhost:8282")
    public Driver findDriverByPolicyNumber(@RequestParam Long policyNumber) {
    
       return driverService.findById(policyNumber);
    }
    
    
    
   
}