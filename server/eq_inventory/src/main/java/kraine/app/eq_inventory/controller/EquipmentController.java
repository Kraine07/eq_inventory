/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kraine.app.eq_inventory.controller;


import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.Location;
import kraine.app.eq_inventory.model.LocationId;
import kraine.app.eq_inventory.model.ModelId;
import kraine.app.eq_inventory.service.EquipmentService;
import kraine.app.eq_inventory.service.LocationService;
import kraine.app.eq_inventory.service.ModelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;





/**
 *
 * @author Kraine
 */
@Controller
public class EquipmentController {


    @Autowired
    EquipmentService es;

    @Autowired
    LocationService ls;

    @Autowired
    ModelService ms;


    @PostMapping("/add-equipment")
    public String addEquipment(
        @RequestParam("location") String locationComposite,
        @RequestParam("model") String modelComposite,
        @ModelAttribute Equipment equipment,
        BindingResult result,
        Model model) throws BindException, InterruptedException {


        // Parse location from string "propertyId,locationName"
        String[] locationParts = locationComposite.split(",");
        LocationId locationId = new LocationId(Long.valueOf(locationParts[0]) , locationParts[1]);

        String[] modelParts = modelComposite.split(",");
        ModelId modelId = new ModelId(Long.valueOf(modelParts[0]), modelParts[1]);

        Location equimentLocation = ls.findByPropertyIdAndName(locationId);
        kraine.app.eq_inventory.model.Model equipmentModel = ms.findByManufacturerIdAndDescription(modelId);


        // set model and location
        equipment.setLocation(equimentLocation);
        equipment.setModel(equipmentModel);
        // change blank serial number to null
        if (equipment.getSerialNumber() == "") {
            equipment.setSerialNumber(null);
        }


        es.saveEquipment(equipment);
        return "redirect:/";
    }








    @PostMapping("/delete-equipment")
    public String deleteEquipment(@RequestParam(name="id") String id) {
        es.deleteEquipment(Long.parseLong(id));
        return "redirect:/";
    }

}
