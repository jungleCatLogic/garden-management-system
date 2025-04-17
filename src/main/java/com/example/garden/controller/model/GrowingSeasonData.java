package com.example.garden.controller.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.example.garden.entity.GrowingSeason;
import com.example.garden.entity.Plant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GrowingSeasonData {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double averageTemperature;
    
    private Set<GrowingSeasonPlant> plants = new HashSet<>();
    
// Constructor that converts from Entity to DTO
    public GrowingSeasonData(GrowingSeason growingSeason) {
        this.id = growingSeason.getId();
        this.name = growingSeason.getName();
        this.startDate = growingSeason.getStartDate();
        this.endDate = growingSeason.getEndDate();
        this.averageTemperature = growingSeason.getAverageTemperature();
        
// Convert plants from entities to DTOs
        if(growingSeason.getPlants() != null) {
            for(Plant plant : growingSeason.getPlants()) {
                this.plants.add(new GrowingSeasonPlant(plant));
            }
        }
    }
    
// Inner class for Plant DTO
    @Data
    @NoArgsConstructor
    public static class GrowingSeasonPlant {
        private Long id;
        private String name;
        private String species;
        private Long gardenBedId;
        
// Constructor that converts from Entity to DTO
        public GrowingSeasonPlant(Plant plant) {
            this.id = plant.getId();
            this.name = plant.getName();
            this.species = plant.getSpecies();
            
            if(plant.getGardenBed() != null) {
                this.gardenBedId = plant.getGardenBed().getId();
            }
        }
    }
}