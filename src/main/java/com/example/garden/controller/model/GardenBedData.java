package com.example.garden.controller.model;

import java.util.HashSet;
import java.util.Set;

import com.example.garden.entity.GardenBed;
import com.example.garden.entity.Plant;

import lombok.Data;
import lombok.NoArgsConstructor;

// Class creates DTOs to handle input/output from API

@Data
@NoArgsConstructor
public class GardenBedData {
    private Long id;
    private String name;
    private String location;
    private String soilType;
    private String sunExposure;
    private String dimensions;
    private String notes;
    
    private Set<GardenBedPlant> plants = new HashSet<>();
    
// Constructor that converts from Entity to DTO
    public GardenBedData(GardenBed gardenBed) {
        this.id = gardenBed.getId();
        this.name = gardenBed.getName();
        this.location = gardenBed.getLocation();
        this.soilType = gardenBed.getSoilType();
        this.sunExposure = gardenBed.getSunExposure();
        this.dimensions = gardenBed.getDimensions();
        this.notes = gardenBed.getNotes();
        
// Convert plants from entities to DTOs
        if(gardenBed.getPlants() != null) {
            for(Plant plant : gardenBed.getPlants()) {
                this.plants.add(new GardenBedPlant(plant));
            }
        }
    }
    
// Inner class for Plant DTO
    @Data
    @NoArgsConstructor
    public static class GardenBedPlant {
        private Long id;
        private String name;
        private String species;
        private Double plantingDepth;
        private String sunRequirements;
        private String waterRequirements;
        private Integer daysToMaturity;
        private String notes;
        
// Constructor that converts from Entity to DTO
        public GardenBedPlant(Plant plant) {
            this.id = plant.getId();
            this.name = plant.getName();
            this.species = plant.getSpecies();
            this.plantingDepth = plant.getPlantingDepth();
            this.sunRequirements = plant.getSunRequirements();
            this.waterRequirements = plant.getWaterRequirements();
            this.daysToMaturity = plant.getDaysToMaturity();
            this.notes = plant.getNotes();
        }
    }
}