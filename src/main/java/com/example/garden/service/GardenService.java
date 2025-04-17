package com.example.garden.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.garden.controller.model.GardenBedData;
import com.example.garden.dao.GardenBedDao;
import com.example.garden.dao.PlantDao;
import com.example.garden.entity.GardenBed;
import com.example.garden.entity.Plant;

import java.util.List;
import com.example.garden.controller.model.MaintenanceActivityData;
import com.example.garden.dao.MaintenanceActivityDao;
import com.example.garden.entity.MaintenanceActivity;

import com.example.garden.controller.model.GrowingSeasonData;
import com.example.garden.dao.GrowingSeasonDao;
import com.example.garden.entity.GrowingSeason;

@Service
public class GardenService {
    
    @Autowired
    private GardenBedDao gardenBedDao;
    
    @Autowired
    private PlantDao plantDao;
    
    @Autowired
    private MaintenanceActivityDao maintenanceActivityDao;
    
    @Autowired
    private GrowingSeasonDao growingSeasonDao;
    
    @Transactional
    public GardenBedData saveGardenBed(GardenBedData gardenBedData) {

// Find existing garden bed or create a new one
        Long gardenBedId = gardenBedData.getId();
        GardenBed gardenBed = findOrCreateGardenBed(gardenBedId);
        
// Copy fields from DTO to entity
        copyGardenBedFields(gardenBed, gardenBedData);
        
// Save the entity to the database
        GardenBed dbGardenBed = gardenBedDao.save(gardenBed);
        
// Return a new DTO with the updated data
        return new GardenBedData(dbGardenBed);
    }
    
    private GardenBed findOrCreateGardenBed(Long gardenBedId) {
        if(gardenBedId == null) {
// If no ID is provided, create a new garden bed
            return new GardenBed();
        } else {
// If an ID is provided, find the existing garden bed
            return findGardenBedById(gardenBedId);
        }
    }
    
    private GardenBed findGardenBedById(Long gardenBedId) {
        return gardenBedDao.findById(gardenBedId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Garden bed not found with ID=" + gardenBedId));
    }
    
    private void copyGardenBedFields(GardenBed gardenBed, GardenBedData gardenBedData) {
        gardenBed.setName(gardenBedData.getName());
        gardenBed.setLocation(gardenBedData.getLocation());
        gardenBed.setSoilType(gardenBedData.getSoilType());
        gardenBed.setSunExposure(gardenBedData.getSunExposure());
        gardenBed.setDimensions(gardenBedData.getDimensions());
        gardenBed.setNotes(gardenBedData.getNotes());       
// Note: plants field is not copied here, it is done separately
    }

// Find a plant by ID and verify it belongs to the specified garden bed
    private Plant findPlantById(Long gardenBedId, Long plantId) {

// Retrieve the plant by ID or throw an exception if not found
        Plant plant = plantDao.findById(plantId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Plant with ID=" + plantId + " was not found"));
        
// Verify the plant belongs to the specified garden bed
        if (plant.getGardenBed() == null || !plant.getGardenBed().getId().equals(gardenBedId)) {
            throw new IllegalArgumentException(
                    "Plant with ID=" + plantId + " does not belong to garden bed with ID=" + gardenBedId);
        }
        
        return plant;
    }

// Find or create a plant
    private Plant findOrCreatePlant(Long gardenBedId, Long plantId) {
        if (plantId == null) {
// If no ID is provided, create a new plant
            return new Plant();
        } else {
// If an ID is provided, find the existing plant
            return findPlantById(gardenBedId, plantId);
        }
    }

// Copy fields from DTO to entity
    private void copyPlantFields(Plant plant, GardenBedData.GardenBedPlant gardenBedPlant) {
        plant.setName(gardenBedPlant.getName());
        plant.setSpecies(gardenBedPlant.getSpecies());
        plant.setPlantingDepth(gardenBedPlant.getPlantingDepth());
        plant.setSunRequirements(gardenBedPlant.getSunRequirements());
        plant.setWaterRequirements(gardenBedPlant.getWaterRequirements());
        plant.setDaysToMaturity(gardenBedPlant.getDaysToMaturity());
        plant.setNotes(gardenBedPlant.getNotes());
    }

// Save a plant to a garden bed
    @Transactional(readOnly = false)
    public GardenBedData.GardenBedPlant savePlant(Long gardenBedId, GardenBedData.GardenBedPlant gardenBedPlant) {

    	// Find the garden bed by ID
        GardenBed gardenBed = findGardenBedById(gardenBedId);
        
// Find or create the plant
        Long plantId = gardenBedPlant.getId();
        Plant plant = findOrCreatePlant(gardenBedId, plantId);
        
// Copy fields from DTO to entity
        copyPlantFields(plant, gardenBedPlant);
        
// Set the garden bed for the plant
        plant.setGardenBed(gardenBed);
        
// Add the plant to the garden bed's collection
        gardenBed.getPlants().add(plant);
        
// Save the plant to the database
        Plant savedPlant = plantDao.save(plant);
        
// Return the plant data as a DTO
        return new GardenBedData.GardenBedPlant(savedPlant);
    }
    
// Save a maintenance activity for a plant
    @Transactional(readOnly = false)
    public MaintenanceActivityData saveMaintenanceActivity(Long plantId, MaintenanceActivityData activityData) {

// Find the plant
        Plant plant = plantDao.findById(plantId)
                .orElseThrow(() -> new NoSuchElementException("Plant not found with ID=" + plantId));
        
// Find existing activity or create a new one
        MaintenanceActivity activity;
        
        if (activityData.getId() == null) {
// Creating a new activity
            activity = new MaintenanceActivity();
        } else {
// Updating existing activity
            activity = maintenanceActivityDao.findById(activityData.getId())
                    .orElseThrow(() -> new NoSuchElementException(
                            "Maintenance activity not found with ID=" + activityData.getId()));
            
// Verify the activity belongs to the specified plant
            if (!activity.getPlant().getId().equals(plantId)) {
                throw new IllegalArgumentException(
                        "Activity with ID=" + activityData.getId() + 
                        " does not belong to plant with ID=" + plantId);
            }
        }
        
// Copy fields from DTO to entity
        activity.setActivityType(activityData.getActivityType());
        activity.setDate(activityData.getDate());
        activity.setNotes(activityData.getNotes());
        activity.setPlant(plant);
        
// Save the entity
        MaintenanceActivity savedActivity = maintenanceActivityDao.save(activity);
        
// Return DTO with updated data
        return new MaintenanceActivityData(savedActivity);
    }

// Get all maintenance activities for a plant
    @Transactional(readOnly = true)
    public List<MaintenanceActivityData> getMaintenanceActivitiesByPlantId(Long plantId) {

// Verify the plant exists
        if (!plantDao.existsById(plantId)) {
            throw new NoSuchElementException("Plant not found with ID=" + plantId);
        }
        
// Get activities and convert to DTOs
        return maintenanceActivityDao.findByPlantId(plantId).stream()
                .map(MaintenanceActivityData::new)
                .toList();
    }    
    
 // Find a growing season by ID
    private GrowingSeason findGrowingSeasonById(Long seasonId) {
        return growingSeasonDao.findById(seasonId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Growing season not found with ID=" + seasonId));
    }

// Add a plant to a growing season
    @Transactional(readOnly = false)
    public GardenBedData.GardenBedPlant addPlantToSeason(Long plantId, Long seasonId) {
        
// Find the plant and season
        Plant plant = plantDao.findById(plantId)
                .orElseThrow(() -> new NoSuchElementException("Plant not found with ID=" + plantId));
        
        GrowingSeason season = findGrowingSeasonById(seasonId);
        
// Associate them
        plant.getGrowingSeasons().add(season);
        season.getPlants().add(plant);
        
// Save changes
        Plant savedPlant = plantDao.save(plant);
        
// Return updated plant DTO
        return new GardenBedData.GardenBedPlant(savedPlant);
    }

// Save a growing season
    @Transactional(readOnly = false)
    public GrowingSeasonData saveGrowingSeason(GrowingSeasonData seasonData) {

// Find existing growing season or create a new one
        Long seasonId = seasonData.getId();
        GrowingSeason season;
        
        if (seasonId == null) {
// Creating a new season
            season = new GrowingSeason();
        } else {
// Updating existing season
            season = findGrowingSeasonById(seasonId);
        }
        
// Copy fields from DTO to entity
        season.setName(seasonData.getName());
        season.setStartDate(seasonData.getStartDate());
        season.setEndDate(seasonData.getEndDate());
        season.setAverageTemperature(seasonData.getAverageTemperature());
        
// Save the entity
        GrowingSeason savedSeason = growingSeasonDao.save(season);
        
// Return DTO with updated data
        return new GrowingSeasonData(savedSeason);
    }

// Retrieve all growing seasons with summary data
    @Transactional(readOnly = true)
    public List<GrowingSeasonData> retrieveAllGrowingSeasons() {

// Retrieve all seasons from the database
        List<GrowingSeason> seasons = growingSeasonDao.findAll();
        
// Convert to DTOs and remove plant data
        return seasons.stream()
                .map(season -> {
                    GrowingSeasonData seasonData = new GrowingSeasonData(season);
                    seasonData.getPlants().clear(); // Clear plants for summary view
                    return seasonData;
                })
                .toList();
    }

 // Retrieve all garden beds with summary data (no plants)
    @Transactional(readOnly = true)
    public List<GardenBedData> retrieveAllGardenBeds() {
        // Retrieve all garden beds from the database
        List<GardenBed> gardenBeds = gardenBedDao.findAll();
        
        // Convert to DTOs and remove plant data
        return gardenBeds.stream()
                .map(gardenBed -> {
                    GardenBedData gardenBedData = new GardenBedData(gardenBed);
                    gardenBedData.getPlants().clear(); // Clear plants for summary view
                    return gardenBedData;
                })
                .toList();
    }

// Retrieve a single garden bed by ID with full details
    @Transactional(readOnly = true)
    public GardenBedData retrieveGardenBedById(Long gardenBedId) {

// Find the garden bed by ID
        GardenBed gardenBed = findGardenBedById(gardenBedId);
        
// Convert to DTO (includes all plants)
        return new GardenBedData(gardenBed);
    }

// Delete a garden bed by ID
    @Transactional(readOnly = false)
    public void deleteGardenBedById(Long gardenBedId) {

// Find the garden bed by ID
        GardenBed gardenBed = findGardenBedById(gardenBedId);
        
// Delete the garden bed (this will cascade to plants)
        gardenBedDao.delete(gardenBed);
    }

// Delete a plant by ID
    @Transactional(readOnly = false)
    public void deletePlantById(Long plantId) {

// Find the plant
        Plant plant = plantDao.findById(plantId)
                .orElseThrow(() -> new NoSuchElementException("Plant not found with ID=" + plantId));
        
// Delete the plant
        plantDao.delete(plant);
    }
    
    
}    
    
    
    