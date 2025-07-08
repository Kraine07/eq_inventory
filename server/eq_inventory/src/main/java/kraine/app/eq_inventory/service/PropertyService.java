package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Property;
import kraine.app.eq_inventory.repository.PropertyRepositoryInterface;

@Service
@Transactional
@CacheConfig(cacheNames = "property")
public class PropertyService {

    @Autowired
    private PropertyRepositoryInterface propertyRepository;


    @CacheEvict(allEntries = true)
    public Property addProperty(Property property) {
        return propertyRepository.save(property);
    }

    @Cacheable
    public List<Property> getAllProperties() {
        return propertyRepository.findAllWithDetails();
    }
}