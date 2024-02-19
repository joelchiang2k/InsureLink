package com.synex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synex.domain.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {
	List<Driver> findByUserId(Long userId);
}
