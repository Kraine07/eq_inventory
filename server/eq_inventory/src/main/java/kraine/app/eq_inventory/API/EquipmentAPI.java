package kraine.app.eq_inventory.API;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import kraine.app.eq_inventory.service.EquipmentService;
import kraine.app.eq_inventory.DTO.EquipmentDTO;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.Property;
import kraine.app.eq_inventory.model.User;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api/v1")
public class EquipmentAPI {
    @Autowired
    private EquipmentService equipmentService;

    @PostMapping("/save-equipment")
    public EquipmentDTO addEquipment(@RequestBody Equipment equipment) {
        // change blank serial number to null
        if (equipment.getSerialNumber() == "") {
            equipment.setSerialNumber(null);
        }
        return equipmentService.saveEquipment(equipment);
    }


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
    public Page<Equipment> getEquipmentByProperty(@RequestBody User user) {
        return equipmentService.findByUser(user);
    }



    @PostMapping("/delete-equipment/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable Long id) {
        try {
            boolean deleted = equipmentService.deleteEquipment(id);

            if (deleted) {
                return ResponseEntity.noContent().build(); // 204 No Content
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Equipment with id " + id + " not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete equipment: " + e.getMessage()));
        }
}



    // @PostMapping("/delete-equipment")
    // public void deleteEquipment(@RequestParam("id") Long id) {
    //     equipmentService.deleteEquipment(id);
    // }

}
