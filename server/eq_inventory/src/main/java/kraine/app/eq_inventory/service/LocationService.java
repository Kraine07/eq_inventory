package kraine.app.eq_inventory.service;

import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import kraine.app.eq_inventory.DTO.LocationDTO;
import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.Location;
import kraine.app.eq_inventory.model.LocationId;
import kraine.app.eq_inventory.model.Property;
import kraine.app.eq_inventory.repository.EquipmentRepositoryInterface;
import kraine.app.eq_inventory.repository.LocationRepositoryInterface;
import kraine.app.eq_inventory.repository.PropertyRepositoryInterface;
import lombok.RequiredArgsConstructor;



@Service
@Transactional
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepositoryInterface locationRepository;

    private final PropertyRepositoryInterface propertyRepositoryInterface;

    private final EquipmentRepositoryInterface equipmentRepositoryInterface;



    @Caching(
        evict = {
            @CacheEvict(cacheNames = "locationList", allEntries = true),
            @CacheEvict(cacheNames = "locationDTOs", allEntries = true),
        },
        put = {
            @CachePut(cacheNames = "location", key = "#result.id")
        }
    )
    public Location saveLocation(String property, String name, LocationId locationId) {
        Location existingLocation = findByLocationId(locationId);
        Location newLocation = new Location(propertyRepositoryInterface.findPropertyById(Long.valueOf(property)), name);
        Location result = locationRepository.saveAndFlush(newLocation);

        List<Equipment> equipmentList = equipmentRepositoryInterface.findByLocation(existingLocation);
        if (existingLocation != null) {
            Property existingProperty = existingLocation.getProperty();
            equipmentList.forEach(equipment -> {
                equipment.setLocation(newLocation);
            });

            equipmentRepositoryInterface.saveAllAndFlush(equipmentList);
            existingProperty.getLocations().remove(existingLocation);
            locationRepository.delete(existingLocation);
        }
        return result;
    }




    @Caching(evict = {
            @CacheEvict(cacheNames = "locationList", allEntries = true),
            @CacheEvict(cacheNames = "locationDTOs", allEntries = true),
    }, put = {
            @CachePut(cacheNames = "location", key = "#result.id")
    })
    public Location saveLocation(Property property, String name, LocationId locationId) {
        Location existingLocation = findByLocationId(locationId);
        Location newLocation = new Location(property, name);
        Location result = locationRepository.saveAndFlush(newLocation);

        List<Equipment> equipmentList = equipmentRepositoryInterface.findByLocation(existingLocation);
        if (existingLocation != null) {
            Property existingProperty = existingLocation.getProperty();
            equipmentList.forEach(equipment -> {
                equipment.setLocation(newLocation);
            });

            equipmentRepositoryInterface.saveAllAndFlush(equipmentList);
            existingProperty.getLocations().remove(existingLocation);
            locationRepository.delete(existingLocation);
        }
        return result;
    }







    @Cacheable(cacheNames = "locationList")
    public List<Location> getAllLocations() {
        return locationRepository.findAllWithFullDetails();
    }





    @Cacheable(cacheNames = "location", key = "#locationId", unless = "#result == null")
    public Location findByLocationId(LocationId locationId) {
        return locationRepository.findById(locationId).orElse(null);
    }




    @Caching(evict = {
            @CacheEvict(cacheNames = "locationList", allEntries = true),
            @CacheEvict(cacheNames = "locationDTOs", allEntries = true),
            @CacheEvict(cacheNames = "location", key = "#id")
    })
    public Boolean deleteLocation(LocationId id) {

        // Find the location and remove it from the property's location list
        Location location = findByLocationId(id);
        Property property = location.getProperty();
        property.getLocations().remove(location);

        try{
            locationRepository.deleteById(id);
            if(locationRepository.findById(id).isPresent()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    // DTO Methods


    @Cacheable(cacheNames = "locationDTOs")
    public List<LocationDTO> getAllLocationDTOs() {
        return locationRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    private LocationDTO convertToDTO(Location location) {
        return LocationDTO.from(location);
    }
}
