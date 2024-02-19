package com.synex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import com.synex.domain.Claim;
import com.synex.domain.Company;
import com.synex.domain.Document;
import com.synex.domain.Driver;
import com.synex.domain.InsurancePlan;
import com.synex.service.CompanyService;
import com.synex.service.DocumentService;
import com.synex.service.DriverService;
import com.synex.service.InsurancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DocumentController {

	 @Autowired
	 private DocumentService documentService;
	 
	 @Autowired
	 private DriverService driverService;
	 

	    @PostMapping("/uploadDocument")
	    @CrossOrigin(origins = "http://localhost:8282")
	    public List<String> uploadDocument(@RequestParam("license") MultipartFile license, @RequestParam("driverId") Long driverId) {
	        List<String> response = new ArrayList<>();
	        if (license.isEmpty()) {
	            response.add("Please select a file to upload.");
	            return response;
	        }
	        try {
	        	byte[] licenseBytes = license.getBytes();
	            String fileName = license.getOriginalFilename();
	            Document document = new Document(licenseBytes, fileName);
	            document.setStatus("Pending");
	            Driver driver = driverService.findById(driverId);
	            document.setDriver(driver);
	            documentService.save(document);
	            response.add("Document uploaded successfully! Document in review...");
	        } catch (IOException e) {
	            response.add("Error uploading document: " + e.getMessage());
	        }
	        return response;
	    }
	    
	    
	    @GetMapping("/getPendingDocuments")
	    @CrossOrigin(origins = "http://localhost:8282")
	    public List<Document> getPendingDocuments() {
	        
	        List<Document> allDocuments = documentService.findAll();
	        
	        
	        List<Document> pendingDocuments = allDocuments.stream()
	                                             .filter(document -> document.getStatus().equals("Pending"))
	                                             .collect(Collectors.toList());
	                                             
	        return pendingDocuments;
	    }
	    
	    @GetMapping("/getRejectedDocuments")
	    @CrossOrigin(origins = "http://localhost:8282")
	    public List<Document> getRejectedClaims() {
	        
	        List<Document> allDocuments = documentService.findAll();
	        
	        
	        List<Document> rejectedDocuments = allDocuments.stream()
	                                             .filter(document -> document.getStatus().equals("Rejected"))
	                                             .collect(Collectors.toList());
	                                             
	        return rejectedDocuments;
	    }
	    
	    @GetMapping("/getApprovedDocuments")
	    @CrossOrigin(origins = "http://localhost:8282")
	    public List<Document> getApprovedClaims() {
	        
	        List<Document> allDocuments = documentService.findAll();
	        
	        
	        List<Document> approvedDocuments = allDocuments.stream()
	                                             .filter(document -> document.getStatus().equals("Approved"))
	                                             .collect(Collectors.toList());
	                                             
	        return approvedDocuments;
	    }
	    
	    @PostMapping("/approveDocument")
	    @CrossOrigin(origins = "http://localhost:8282")
	    public void approveDocument(@RequestParam Long documentId) {
	    	Document document = documentService.findById(documentId);
	        document.setStatus("Approved");
	                                             
	        documentService.save(document);
	    }
	    
	    @PostMapping("/rejectDocument")
	    @CrossOrigin(origins = "http://localhost:8282")
	    public void rejectDocument(@RequestParam Long documentId) {
	    	Document document = documentService.findById(documentId);
	        document.setStatus("Rejected");
	                                             
	        documentService.save(document);
	    }
	    
	    @PostMapping("/getDriverDetails")
	    @CrossOrigin(origins = "http://localhost:8282")
	    public Driver getDriverDetails(@RequestParam Long driverId) {
	    	Driver driver = driverService.findById(driverId);
	        
	                                             
	        return driver;
	    }
   
}
