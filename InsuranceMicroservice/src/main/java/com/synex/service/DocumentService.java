package com.synex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.synex.domain.Company;
import com.synex.domain.Document;
import com.synex.domain.InsurancePlan;


@Service
public interface DocumentService {
	public Document save(Document document);
    public Document findById(Long documentId);
    public List<Document> findAll();
   
}
