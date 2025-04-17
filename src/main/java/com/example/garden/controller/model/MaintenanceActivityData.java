package com.example.garden.controller.model;

import java.time.LocalDate;

import com.example.garden.entity.MaintenanceActivity;
import com.example.garden.entity.MaintenanceActivity.ActivityType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MaintenanceActivityData {
    private Long id;
    private ActivityType activityType;
    private LocalDate date;
    private String notes;
    private Long plantId;
    private String plantName; // For display purposes only
    
// Constructor that converts from Entity to DTO
    public MaintenanceActivityData(MaintenanceActivity activity) {
        this.id = activity.getId();
        this.activityType = activity.getActivityType();
        this.date = activity.getDate();
        this.notes = activity.getNotes();
        
        if (activity.getPlant() != null) {
            this.plantId = activity.getPlant().getId();
            this.plantName = activity.getPlant().getName();
        }
    }
}