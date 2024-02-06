package com.synex.controller;

import java.util.ArrayList;
import java.util.List;

import com.synex.domain.Company;
import com.synex.domain.InsurancePlan;
import com.synex.service.CompanyService;
import com.synex.service.InsurancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/getCompanyNames")
    @CrossOrigin(origins = "http://localhost:8282")
    public List<String> getCompanyNames() {
        List<String> companyNames = new ArrayList<>();
        List<Company> companies = companyService.findAll();
        
        for (Company company : companies) {
            companyNames.add(company.getCompanyName());
        }
        
      
        for (String companyName : companyNames) {
            System.out.println("Company Name: " + companyName);
        }
        
        return companyNames;
    }
    
   
}
