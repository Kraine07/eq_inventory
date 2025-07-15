package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Location;
import kraine.app.eq_inventory.model.LocationId;
import kraine.app.eq_inventory.repository.LocationRepositoryInterface;
import kraine.app.eq_inventory.repository.PropertyRepositoryInterface;

@Service
@Transactional
@CacheConfig(cacheNames = "location")
public class LocationService {

    @Autowired
    private LocationRepositoryInterface locationRepository;

    @Autowired
    private PropertyRepositoryInterface propertyRepositoryInterface;




    @CacheEvict(cacheNames = {"location","equipment"}, allEntries = true)
    public Location saveLocation(String property, String name, LocationId locationId) {
        Location existingLocation = findByPropertyId(locationId);

        if (existingLocation != null) {
            locationRepository.delete(existingLocation);
        }

        Location newLocation = new Location();
        newLocation.setProperty(propertyRepositoryInterface.findPropertyById(Long.valueOf(property)));
        newLocation.setName(name);
        return locationRepository.save(newLocation);
    }



    @Cacheable
    public List<Location> getAllLocations() {
        return locationRepository.findAllWithFullDetails();
    }

    public Location findByPropertyId(LocationId locationId) {
        return locationRepository.findByPropertyIdAndName(locationId.getProperty(), locationId.getName());
    }


    @CacheEvict(cacheNames = {"location","equipment"}, allEntries = true)
    public void deleteLocation(LocationId id) {
        locationRepository.deleteById(id);
    }
}
