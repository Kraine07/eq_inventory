package kraine.app.eq_inventory.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import kraine.app.eq_inventory.service.EquipmentService;
import kraine.app.eq_inventory.DTO.EquipmentDTO;
import kraine.app.eq_inventory.model.Equipment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api/v1")
public class EquipmentAPI {
    @Autowired
    private EquipmentService equipmentService;



    @GetMapping("/get-all-equipment")
    List<Equipment> findAllEquipment() {
        return equipmentService.getAllWithFullDetails();
    }


    @GetMapping("/get-all-equipment-dto")
    public List<EquipmentDTO> getAllEquipmentDTO() {
        return equipmentService.getAllEquipmentDTOs();
    }

}
