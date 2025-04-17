package com.example.garden.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.garden.entity.MaintenanceActivity;

@Repository
public interface MaintenanceActivityDao extends JpaRepository<MaintenanceActivity, Long> {
  
// Find all maintenance activities for a specific plant
    List<MaintenanceActivity> findByPlantId(Long plantId);
}