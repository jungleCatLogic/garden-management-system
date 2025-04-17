package com.example.garden.dao;

// Interface DAOs handle db operations

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.garden.entity.GardenBed;

@Repository
public interface GardenBedDao extends JpaRepository<GardenBed, Long> {
// Spring Data JPA provides basic CRUD operations by default
}