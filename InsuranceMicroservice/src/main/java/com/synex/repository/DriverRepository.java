package com.synex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synex.domain.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {
	
}
