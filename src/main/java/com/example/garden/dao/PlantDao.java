package com.example.garden.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.garden.entity.Plant;

@Repository
public interface PlantDao extends JpaRepository<Plant, Long> {

// Find all plants in a specific garden bed
    List<Plant> findByGardenBedId(Long gardenBedId);
}