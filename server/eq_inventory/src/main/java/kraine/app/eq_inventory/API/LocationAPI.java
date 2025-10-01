package kraine.app.eq_inventory.API;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraine.app.eq_inventory.model.Location;
import kraine.app.eq_inventory.model.LocationId;
import kraine.app.eq_inventory.service.LocationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1")
public class LocationAPI {

    @Autowired
    private LocationService locationService;

    @GetMapping("/get-all-locations")
    public List<Location> getLocations() {
        return locationService.getAllLocations();
    }

    @PostMapping("/save-location")
    public ResponseEntity<?> saveLocation(@RequestBody Location location) {
        LocationId locationId = new LocationId(location.getProperty().getId(), location.getName());

        try {
            Location savedLocation = locationService.saveLocation(location.getProperty().getName(), location.getName(),
                    locationId);
            return ResponseEntity.ok(savedLocation);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", " Error saving location."));
        }
    }




    @PostMapping("/delete-location")
    public ResponseEntity<?> deleteLocation(@RequestBody LocationId id) {
        Boolean deleted = locationService.deleteLocation(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Error deleting location."));
        }
    }

}
