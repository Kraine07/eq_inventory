package kraine.app.eq_inventory.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraine.app.eq_inventory.DTO.PropertyDTO;
import kraine.app.eq_inventory.service.PropertyService;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/v1")
public class PropertyAPI {


    @Autowired
    private PropertyService propertyService;



    @GetMapping("/get-all-properties")
    public List<PropertyDTO> findAllProperties() {
        return propertyService.getAllPropertyDTOs();
    }


}
