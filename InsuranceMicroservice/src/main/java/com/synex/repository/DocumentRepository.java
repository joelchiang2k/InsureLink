package com.synex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synex.domain.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

}
