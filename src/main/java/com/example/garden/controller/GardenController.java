package com.example.garden.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.garden.controller.model.GardenBedData;
import com.example.garden.service.GardenService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.garden.controller.model.MaintenanceActivityData;

import com.example.garden.controller.model.GrowingSeasonData;

import org.springframework.web.bind.annotation.DeleteMapping;


// Class handles HTTP requests

@RestController
@RequestMapping("/garden")
@Slf4j
public class GardenController {
    
    @Autowired
    private GardenService gardenService;
    
// POST endpoint to create a new garden bed
    @PostMapping("/bed")
    @ResponseStatus(code = HttpStatus.CREATED)
    public GardenBedData createGardenBed(@RequestBody GardenBedData gardenBedData) {
        log.info("Creating garden bed {}", gardenBedData);
        return gardenService.saveGardenBed(gardenBedData);
    }
    
// PUT endpoint to update an existing garden bed
    @PutMapping("/bed/{gardenBedId}")
    public GardenBedData updateGardenBed(
            @PathVariable Long gardenBedId, 
            @RequestBody GardenBedData gardenBedData) {
        
// Set the garden bed ID in the data from the URL path
        gardenBedData.setId(gardenBedId);
        
        log.info("Updating garden bed {}", gardenBedData);
        return gardenService.saveGardenBed(gardenBedData);
    }
    
// Add a plant to a garden bed
    @PostMapping("/bed/{gardenBedId}/plant")
    @ResponseStatus(code = HttpStatus.CREATED)
    public GardenBedData.GardenBedPlant addPlant(
            @PathVariable Long gardenBedId,
            @RequestBody GardenBedData.GardenBedPlant gardenBedPlant) {
        
        log.info("Adding plant to garden bed with ID={}: {}", gardenBedId, gardenBedPlant);
        return gardenService.savePlant(gardenBedId, gardenBedPlant);
    }    

// Add a maintenance activity to a plant
    @PostMapping("/plant/{plantId}/activity")
    @ResponseStatus(code = HttpStatus.CREATED)
    public MaintenanceActivityData addMaintenanceActivity(
            @PathVariable Long plantId,
            @RequestBody MaintenanceActivityData activityData) {
        
        log.info("Adding maintenance activity to plant with ID={}: {}", plantId, activityData);
        return gardenService.saveMaintenanceActivity(plantId, activityData);
    }

// Get all maintenance activities for a plant
    @GetMapping("/plant/{plantId}/activity")
    public List<MaintenanceActivityData> getMaintenanceActivitiesByPlantId(@PathVariable Long plantId) {
        log.info("Retrieving maintenance activities for plant with ID={}", plantId);
        return gardenService.getMaintenanceActivitiesByPlantId(plantId);
    }   
    
// Create a growing season
    @PostMapping("/season")
    @ResponseStatus(code = HttpStatus.CREATED)
    public GrowingSeasonData createGrowingSeason(@RequestBody GrowingSeasonData seasonData) {
        log.info("Creating growing season: {}", seasonData);
        return gardenService.saveGrowingSeason(seasonData);
    }

// Get all growing seasons
    @GetMapping("/season")
    public List<GrowingSeasonData> retrieveAllGrowingSeasons() {
        log.info("Retrieving all growing seasons");
        return gardenService.retrieveAllGrowingSeasons();
    }

// Add a plant to a growing season
    @PostMapping("/plant/{plantId}/season/{seasonId}")
    public GardenBedData.GardenBedPlant addPlantToSeason(
            @PathVariable Long plantId,
            @PathVariable Long seasonId) {
        
        log.info("Adding plant with ID={} to growing season with ID={}", plantId, seasonId);
        return gardenService.addPlantToSeason(plantId, seasonId);
    }    
    
 // GET endpoints for garden beds
    @GetMapping("/bed")
    public List<GardenBedData> retrieveAllGardenBeds() {
        log.info("Retrieving all garden beds");
        return gardenService.retrieveAllGardenBeds();
    }

    @GetMapping("/bed/{gardenBedId}")
    public GardenBedData retrieveGardenBedById(@PathVariable Long gardenBedId) {
        log.info("Retrieving garden bed with ID={}", gardenBedId);
        return gardenService.retrieveGardenBedById(gardenBedId);
    }

// DELETE endpoint for garden bed
    @DeleteMapping("/bed/{gardenBedId}")
    public Map<String, String> deleteGardenBedById(@PathVariable Long gardenBedId) {
        log.info("Deleting garden bed with ID={}", gardenBedId);
        
        gardenService.deleteGardenBedById(gardenBedId);
        
        // Return a success message
        Map<String, String> response = new HashMap<>();
        response.put("message", "Garden bed with ID=" + gardenBedId + " was deleted successfully");
        
        return response;
    }

// DELETE endpoint for plant
    @DeleteMapping("/plant/{plantId}")
    public Map<String, String> deletePlantById(@PathVariable Long plantId) {
        log.info("Deleting plant with ID={}", plantId);
        
        gardenService.deletePlantById(plantId);
        
// Return a success message
        Map<String, String> response = new HashMap<>();
        response.put("message", "Plant with ID=" + plantId + " was deleted successfully");
        
        return response;
    }    
    
}