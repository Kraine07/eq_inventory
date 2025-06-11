package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Region;
import kraine.app.eq_inventory.repository.RegionRepositoryInterface;

@Service
@Transactional
public class RegionService {


    @Autowired
    private RegionRepositoryInterface rri;



    public Region addRegion(Region region) {
        // Logic to save the region to the database
        // This could involve calling a repository method
        // For example: regionRepository.save(region);
        return rri.save(region); // Return the saved region or any other relevant information
    }

    public List<Region> getAllRegions() {
        // Logic to retrieve all regions from the database
        // This could involve calling a repository method
        // For example: return regionRepository.findAll();
        return rri.findAll(); // Return the list of regions
    }
}
