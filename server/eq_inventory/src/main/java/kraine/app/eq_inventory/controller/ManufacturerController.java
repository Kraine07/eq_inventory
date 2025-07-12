package kraine.app.eq_inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import kraine.app.eq_inventory.model.Manufacturer;
import kraine.app.eq_inventory.service.ManufacturerService;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;



@Controller
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;


    @PostMapping("/add-manufacturer")
    public String addManufacturer(@Valid Manufacturer manufacturer, BindingResult result, Model model)
            throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        manufacturerService.addManufacturer(manufacturer);
        return "redirect:/";
    }


    @PostMapping("/update-manufacturer")
    public String updateManufacturer(@Valid Manufacturer manufacturer, BindingResult result, Model model) throws BindException{
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        manufacturerService.updateManufacturer(manufacturer);
        return "redirect:/";
    }


    @PostMapping("/delete-manufacturer")
    public String deleteManufacturer(Long id) {
        manufacturerService.deleteManufacturer(id);
        return "redirect:/";
    }


}
