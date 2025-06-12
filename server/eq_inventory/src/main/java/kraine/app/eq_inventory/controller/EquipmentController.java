/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kraine.app.eq_inventory.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import kraine.app.eq_inventory.SessionHandler;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Kraine
 */
@Controller
public class EquipmentController {


    @Autowired
    EquipmentService es;




    @GetMapping("/dashboard")
    public String loadDashboard(Model model, HttpServletRequest request) {
        // check if session timed out
        if (!SessionHandler.hasSessionAttribute(request, "authUser")) {
            return "redirect:/";
        }

        // get list of equipment
        List<Equipment> equipmentList = es.getAllEquipment();

        // user equipment
        User authUser = SessionHandler.getAttribute(request, "authUser", User.class);
        List<Equipment> userEquipmentList = equipmentList.stream()
        .filter(equipment -> equipment.getLocation().getProperty().getUser().getId().equals(authUser.getId()))
        .collect(Collectors.toList());

System.out.println("###############################################" +authUser.toString());
System.out.println("###############################################" +userEquipmentList.toString());
System.out.println("###############################################" +equipmentList.toString());
        model.addAttribute("userEquipmentList", userEquipmentList);

        return "main";
    }





    @PostMapping("/add-equipment")
    public String addEquipment(@Valid Equipment equipment, BindingResult bindingResult, Model model) throws BindException{
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        es.addEquipment(equipment);
        return "redirect:/";
    }
}
