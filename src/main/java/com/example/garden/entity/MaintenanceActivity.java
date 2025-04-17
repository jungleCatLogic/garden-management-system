package com.example.garden.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "maintenance_activities")
@EqualsAndHashCode(callSuper = true)
public class MaintenanceActivity extends BaseEntity {
    
// Enum represents different types of maintenance activities
    public enum ActivityType {
        WATERING,
        FERTILIZING,
        PRUNING,
        HARVESTING,
        PLANTING,
        WEEDING,
        OTHER
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", nullable = false)
    private ActivityType activityType;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Plant plant;
}