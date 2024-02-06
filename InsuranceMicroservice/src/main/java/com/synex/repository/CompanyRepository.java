package com.synex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synex.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
