package com.synex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synex.domain.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
