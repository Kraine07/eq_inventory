package kraine.app.eq_inventory.API;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraine.app.eq_inventory.DTO.ManufacturerDTO;
import kraine.app.eq_inventory.exception.DeleteManufacturerException;
import kraine.app.eq_inventory.model.Manufacturer;
import kraine.app.eq_inventory.service.ManufacturerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1")
class ManufacturerAPI {

    @Autowired
    private ManufacturerService manufacturerService;






    @GetMapping("/get-all-manufacturers")
    public List<ManufacturerDTO> getManufacturersDTO() {
        return manufacturerService.getAllManufacturerDTOs();
    }






    @PostMapping("/save-manufacturer")
    public ResponseEntity<?> addManufacturer(@RequestBody Manufacturer manufacturer) {

        try {
            Manufacturer savedManufacturer = manufacturerService.saveManufacturer(manufacturer);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedManufacturer);// 201 Created

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));// 400 Bad Request
        }
    }






    @PostMapping("/delete-manufacturer/{id}")
    public ResponseEntity<?> deleteManufacturer(@PathVariable Long id) {

        try {
            manufacturerService.deleteManufacturer(id);
            if (manufacturerService.findById(id) != null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Failed to delete manufacturer with id: " + id));
            }
            return ResponseEntity.ok().build();
        }
        catch (DeleteManufacturerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }




}


