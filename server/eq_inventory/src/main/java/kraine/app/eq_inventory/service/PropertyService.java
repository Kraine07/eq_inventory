package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kraine.app.eq_inventory.model.Property;
import kraine.app.eq_inventory.repository.PropertyRepositoryInterface;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepositoryInterface propertyRepository;



    public Property addProperty(Property property) {
        return propertyRepository.save(property);
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }
}