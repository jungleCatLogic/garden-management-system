package com.example.garden.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.garden.entity.GrowingSeason;

@Repository
public interface GrowingSeasonDao extends JpaRepository<GrowingSeason, Long> {
// Spring Data JPA provides basic CRUD operations by default
}