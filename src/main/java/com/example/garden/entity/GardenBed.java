package com.example.garden.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "garden_beds")
@EqualsAndHashCode(callSuper = true)
public class GardenBed extends BaseEntity {
    
    @Column(nullable = false)
    private String name;
    
    private String location;
    
    @Column(name = "soil_type")
    private String soilType;
    
    @Column(name = "sun_exposure")
    private String sunExposure;
    
    private String dimensions;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @OneToMany(mappedBy = "gardenBed", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Plant> plants = new HashSet<>();
}