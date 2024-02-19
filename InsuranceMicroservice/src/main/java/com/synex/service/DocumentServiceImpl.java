package com.synex.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synex.domain.Document;
import com.synex.repository.DocumentRepository;

@Service
public class DocumentServiceImpl implements DocumentService {
	
	@Autowired DocumentRepository documentRepository;
	
	@Override
	public Document save(Document document) {
		return documentRepository.save(document);
	}

	@Override
	public Document findById(Long documentId) {
		Optional<Document> optDocument = documentRepository.findById(documentId);
		if(optDocument.isPresent()) {
			return optDocument.get();
		}
		return null;
	}

	@Override
	public List<Document> findAll() {
		return documentRepository.findAll();
	}

}
