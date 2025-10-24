package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.DTO.PropertyDTO;
import kraine.app.eq_inventory.DTO.RegionDTO;
import kraine.app.eq_inventory.DTO.UserDTO;
import kraine.app.eq_inventory.exception.DeletePropertyException;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.Property;
import kraine.app.eq_inventory.repository.PropertyRepositoryInterface;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepositoryInterface propertyRepository;
    private final EquipmentService equipmentService;



    @Caching(
        evict = {
            @CacheEvict(cacheNames = "propertyList", allEntries = true),
            @CacheEvict(cacheNames = "propertyDTOs", allEntries = true)
        },
        put = {
            @CachePut(cacheNames = "property", key = "#result.id")
        }
    )
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



    @Cacheable(cacheNames = "propertyList")
    public List<Property> getAllProperties() {
        return propertyRepository.findAllWithDetails();
    }




    @Cacheable(cacheNames = "property", key = "#id", unless = "#result == null")
    public Property findById(Long id) {
        return propertyRepository.findById(id).orElse(null);
    }




    @Caching(
        evict = {
            @CacheEvict(cacheNames = "propertyList", allEntries = true),
            @CacheEvict(cacheNames = "propertyDTOs", allEntries = true),
            @CacheEvict(cacheNames = "property", key = "#id")
        }
    )
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






    @Cacheable(cacheNames = "propertyDTOs")
    public List<PropertyDTO> getAllPropertyDTOs() {
        return propertyRepository.findAllWithDetails().stream()
                .map(this::convertToDTO)
                .toList();
    }

    private PropertyDTO convertToDTO(Property property) {
        return new PropertyDTO(
                property.getId(),
                property.getName(),
                RegionDTO.from(property.getRegion()),
                UserDTO.from(property.getUser())
                );
    }

}