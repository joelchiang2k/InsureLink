package com.synex.controller;

import java.util.List;

import com.synex.domain.Company;
import com.synex.domain.InsurancePlan;
import com.synex.service.InsurancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InsurancePlanController {

    @Autowired
    private InsurancePlanService insurancePlanService;

    @GetMapping("/getPlansByInsurance")
    @CrossOrigin(origins = "http://localhost:8282")
    public List<InsurancePlan> getPlansByInsurance(@RequestParam String insuranceType) {
        // Call service method to fetch plans based on the selected insurance type
    	System.out.println(insuranceType);
        List<InsurancePlan> plans = insurancePlanService.getPlansByType(insuranceType);
        
        for (InsurancePlan plan : plans) {
            System.out.println("Plan Name: " + plan.getPlanName());
            System.out.println("Description: " + plan.getDescription());
            System.out.println("Premium: " + plan.getPremium());
            // Add additional properties as needed
        }
        return plans;
    }
    
    @GetMapping("/getPlansByCompany")
    @CrossOrigin(origins = "http://localhost:8282")
    public List<InsurancePlan> getPlansByCompany(@RequestParam String company) {
        
    	System.out.println(company);
        List<InsurancePlan> plans = insurancePlanService.getPlansByCompany(company);
        
        for (InsurancePlan plan : plans) {
            System.out.println("Plan Name: " + plan.getPlanName());
            System.out.println("Description: " + plan.getDescription());
            System.out.println("Premium: " + plan.getPremium());
     
        }
        return plans;
    }
}
