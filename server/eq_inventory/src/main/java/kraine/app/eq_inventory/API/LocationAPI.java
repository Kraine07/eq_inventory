package kraine.app.eq_inventory.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraine.app.eq_inventory.model.Location;
import kraine.app.eq_inventory.service.LocationService;

@RestController
@RequestMapping("/api/v1")
public class LocationAPI {

    @Autowired
    LocationService locationService;

    @GetMapping("/get-all-locations")
    public List<Location> getLocations() {
        return locationService.getAllLocations();
    }
}
