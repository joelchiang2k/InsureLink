package com.synex.repository;

import java.sql.Blob;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.synex.domain.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
	
}
