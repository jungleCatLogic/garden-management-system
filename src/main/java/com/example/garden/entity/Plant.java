package com.example.garden.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "plants")
@EqualsAndHashCode(callSuper = true)
public class Plant extends BaseEntity {
    
    @Column(nullable = false)
    private String name;
    
    private String species;
    
    @Column(name = "planting_depth")
    private Double plantingDepth;
    
    @Column(name = "sun_requirements")
    private String sunRequirements;
    
    @Column(name = "water_requirements")
    private String waterRequirements;
    
    @Column(name = "days_to_maturity")
    private Integer daysToMaturity;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garden_bed_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private GardenBed gardenBed;
    
    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<MaintenanceActivity> maintenanceActivities = new HashSet<>();
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "plant_growing_seasons",
        joinColumns = @JoinColumn(name = "plant_id"),
        inverseJoinColumns = @JoinColumn(name = "growing_season_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<GrowingSeason> growingSeasons = new HashSet<>();
}