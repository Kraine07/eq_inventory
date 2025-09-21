package kraine.app.eq_inventory.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraine.app.eq_inventory.model.Manufacturer;
import kraine.app.eq_inventory.service.ManufacturerService;

@RestController
@RequestMapping("/api/v1")
class ManufacturerAPI {

    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping("/get-all-manufacturers")
    public List<Manufacturer> getManufacturers() {
        return manufacturerService.getAllManufacturers();
    }
}
