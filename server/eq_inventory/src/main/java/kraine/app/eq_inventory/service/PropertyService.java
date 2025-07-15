package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.exception.DeletePropertyException;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.Property;
import kraine.app.eq_inventory.repository.EquipmentRepositoryInterface;
import kraine.app.eq_inventory.repository.PropertyRepositoryInterface;

@Service
@Transactional
@CacheConfig(cacheNames = "property")
public class PropertyService {

    @Autowired
    private PropertyRepositoryInterface propertyRepository;

    @Autowired
    private EquipmentService equipmentService;




    @CacheEvict(cacheNames = {"property","location", "equipment"}, allEntries = true)
    public Property save(Property property) {
        Property existingProperty = null;
        if (property.getId() != null) {
            existingProperty = findById(property.getId());
        }
        if (existingProperty != null) {
            existingProperty.setRegion(property.getRegion());
            existingProperty.setName(property.getName());
            existingProperty.setUser(property.getUser());
            return propertyRepository.saveAndFlush(existingProperty);
        }
        return propertyRepository.saveAndFlush(property);
    }



    @Cacheable
    public List<Property> getAllProperties() {
        return propertyRepository.findAllWithDetails();
    }




    public Property findById(Long id) {
        return propertyRepository.findById(id).orElse(null);
    }




    @CacheEvict(cacheNames = { "property", "location", "equipment" }, allEntries = true)
    public void deleteProperty(Long id) throws DeletePropertyException {

        // check if property has equipment present
        List<Equipment> equipment = equipmentService.getAllWithFullDetails();
        equipment.forEach(eq -> {
            if (eq.getLocation().getProperty().getId().equals(id)) {
                throw new DeletePropertyException("Cannot delete property because equipment exists there.");
            }
        });
        propertyRepository.deleteById(id);
    }
}