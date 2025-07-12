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

@Service
@Transactional
@CacheConfig(cacheNames = "location")
public class LocationService {

    @Autowired
    private LocationRepositoryInterface locationRepository;

    @CacheEvict(cacheNames = {"location","equipment"}, allEntries = true)
    public Location addLocation(Location location) {
        return locationRepository.save(location);
    }

    @Cacheable
    public List<Location> getAllLocations() {
        return locationRepository.findAllWithFullDetails();
    }

    public Location findByPropertyIdAndName(LocationId locationId) {
        return locationRepository.findByPropertyIdAndName(locationId.getProperty(), locationId.getName());
    }

}
