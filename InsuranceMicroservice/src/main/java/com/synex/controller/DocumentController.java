package com.synex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synex.domain.Claim;
import com.synex.domain.Company;
import com.synex.domain.Document;
import com.synex.domain.Driver;
import com.synex.domain.InsurancePlan;
import com.synex.service.CompanyService;
import com.synex.service.DocumentService;
import com.synex.service.DriverService;
import com.synex.service.EmailService;
import com.synex.service.InsurancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	 
	 @Autowired EmailService emailService;
	 
	 @Autowired ObjectMapper objectMapper;
	 

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
	    public ResponseEntity<String> approveDocument(@RequestParam Long documentId, @RequestParam Long driverId) {
	    	Document document = documentService.findById(documentId);
	    	Driver driver = driverService.findById(driverId);
	    	driver.setStatus("Approved");
	        document.setStatus("Approved");                        
	        documentService.save(document);
	        
	        try {
				Driver savedDriver = driver;
				System.out.println("savedDriver" + savedDriver);
				
				System.out.println("driverObj" + savedDriver);
				if(savedDriver != null) {
					System.out.println("savedDriver" + savedDriver.getId());
					CompletableFuture.runAsync(() -> {
						try {
							emailService.sendDriverApproval(savedDriver);
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
	    
	    @PostMapping("/rejectDocument")
	    @CrossOrigin(origins = "http://localhost:8282")
	    public ResponseEntity<String> rejectDocument(@RequestParam Long documentId, @RequestParam Long driverId) {
	    	Document document = documentService.findById(documentId);
	    	Driver driver = driverService.findById(driverId);
	    	driver.setStatus("Rejected");
	        document.setStatus("Rejected");       
	        documentService.save(document);
	        
	        try {
				Driver savedDriver = driver;
				System.out.println("savedDriver" + savedDriver);
				
				System.out.println("driverObj" + savedDriver);
				if(savedDriver != null) {
					System.out.println("savedDriver" + savedDriver.getId());
					CompletableFuture.runAsync(() -> {
						try {
							emailService.sendDriverRejection(savedDriver);
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
	    
	    @PostMapping("/getDriverDetails")
	    @CrossOrigin(origins = "http://localhost:8282")
	    public Driver getDriverDetails(@RequestParam Long driverId) {
	    	Driver driver = driverService.findById(driverId);
	        
	                                             
	        return driver;
	    }
   
}
