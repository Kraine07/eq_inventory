package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Location;
import kraine.app.eq_inventory.repository.LocationRepositoryInterface;

@Service
@Transactional
public class LocationService {

    @Autowired
    private LocationRepositoryInterface locationRepository;

    public Location addLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

}
