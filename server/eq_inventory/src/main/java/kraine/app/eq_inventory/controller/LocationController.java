package kraine.app.eq_inventory.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


import kraine.app.eq_inventory.model.LocationId;
import kraine.app.eq_inventory.service.LocationService;



@Controller
public class LocationController {


    @Autowired
    private LocationService locationService;


    private LocationId createLocationId(String id) {
        String[] idParts = id.split(",");
        LocationId locationId = new LocationId(Long.valueOf(idParts[0]), idParts[1]);
        return locationId;
    }



    @PostMapping("/add-location")
    public String addLocation(@Param("property") String property, @Param("name") String name,
            @Param("id") String id) {

        LocationId locationId = createLocationId(id);
        locationService.saveLocation(property, name, locationId);
        return "redirect:/";
    }



    @PostMapping("/delete-location")
    public String deleteLocation(@Param("id") String id) {

        LocationId locationId = createLocationId(id);
        locationService.deleteLocation(locationId);
        return "redirect:/";
    }


}
