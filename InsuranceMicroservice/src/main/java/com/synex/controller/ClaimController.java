package com.synex.controller;

import java.util.ArrayList;
import java.util.List;

import com.synex.domain.Claim;
import com.synex.domain.Company;
import com.synex.domain.InsurancePlan;
import com.synex.service.ClaimService;
import com.synex.service.CompanyService;
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

    @PostMapping("/saveClaim")
    @CrossOrigin(origins = "http://localhost:8282")
    public void saveClaim(@RequestBody Claim claim) {
       System.out.println(claim.getAmount());
       claimService.save(claim);
    }
    
   
}
