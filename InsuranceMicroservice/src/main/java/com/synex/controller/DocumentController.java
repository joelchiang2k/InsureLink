package com.synex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.synex.domain.Company;
import com.synex.domain.Document;
import com.synex.domain.InsurancePlan;
import com.synex.service.CompanyService;
import com.synex.service.DocumentService;
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

	    @PostMapping("/uploadDocument")
	    @CrossOrigin(origins = "http://localhost:8282")
	    public List<String> uploadDocument(@RequestParam("license") MultipartFile license) {
	        List<String> response = new ArrayList<>();
	        if (license.isEmpty()) {
	            response.add("Please select a file to upload.");
	            return response;
	        }
	        try {
	            byte[] licenseBytes = license.getBytes();
	            String fileName = license.getOriginalFilename();
	            Document document = new Document(licenseBytes, fileName);
	            documentService.save(document);
	            response.add("Document uploaded successfully! Document in review...");
	        } catch (IOException e) {
	            response.add("Error uploading document: " + e.getMessage());
	        }
	        return response;
	    }
   
}
