package kraine.app.eq_inventory.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import kraine.app.eq_inventory.model.Property;
import kraine.app.eq_inventory.service.PropertyService;

@Controller
public class PropertyController {


    @Autowired
    private PropertyService propertyService;

    @PostMapping("/add-property")
    public String addProperty(@Valid Property property, BindingResult result, Model model) throws BindException {

        if (result.hasErrors()) {
            throw new BindException(result);
        }
        propertyService.addProperty(property);
        // TODO: Add a success message to the model if needed
        return "redirect:/"; // Redirect to a page that lists all properties
    }

}
