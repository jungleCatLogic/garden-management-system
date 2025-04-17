package com.example.garden.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "growing_seasons")
@EqualsAndHashCode(callSuper = true)
public class GrowingSeason extends BaseEntity {
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @Column(name = "average_temperature")
    private Double averageTemperature;
    
    @ManyToMany(mappedBy = "growingSeasons")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Plant> plants = new HashSet<>();
}