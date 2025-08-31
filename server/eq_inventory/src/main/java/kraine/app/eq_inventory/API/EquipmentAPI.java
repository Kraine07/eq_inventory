package kraine.app.eq_inventory.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import kraine.app.eq_inventory.service.EquipmentService;
import kraine.app.eq_inventory.DTO.EquipmentDTO;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.Property;
import kraine.app.eq_inventory.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api/v1")
public class EquipmentAPI {
    @Autowired
    private EquipmentService equipmentService;



    @GetMapping("/get-all-equipment")
    public List<Equipment> findAllEquipment() {
        return equipmentService.getAllWithFullDetails();
    }


    @GetMapping("/get-all-equipment-dto")
    public List<EquipmentDTO> getAllEquipmentDTO() {
        return equipmentService.getAllEquipmentDTOs();
    }
    
    @PostMapping("/equipment-by-property")
    public List<Equipment> getEquipmentByProperty(@RequestBody Property property){
        return equipmentService.getByProperty(property);
    }
    
    
    
    @PostMapping("/equipment-by-user")
    public Page<Equipment> getEquipmentByProperty(@RequestBody User user){
        return equipmentService.findByUser(user);
    }

}
