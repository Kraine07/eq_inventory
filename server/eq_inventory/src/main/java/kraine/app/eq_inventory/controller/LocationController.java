package kraine.app.eq_inventory.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;


import jakarta.validation.Valid;
import kraine.app.eq_inventory.model.Location;
import kraine.app.eq_inventory.service.LocationService;


@Controller
public class LocationController {
    @Autowired
    private LocationService locationService;

    @PostMapping("/add-location")
    public String addLocation(@Valid Location location, BindingResult bindingResult, Model model) throws BindException {

        if (bindingResult.hasErrors()) {
            System.out.println("LOCATION BINDING ERROR");
            throw new BindException(bindingResult);
        }
        locationService.addLocation(location);
        return "redirect:/";
    }


}
