/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kraine.app.eq_inventory.controller;

import jakarta.validation.Valid;

import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.service.EquipmentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;





/**
 *
 * @author Kraine
 */
@Controller
public class EquipmentController {


    @Autowired
    EquipmentService es;






    @PostMapping("/add-equipment")
    public String addEquipment(@Valid Equipment equipment, BindingResult bindingResult, Model model)
            throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        if (equipment.getSerialNumber() == "") {
            equipment.setSerialNumber(null);
        }
        es.addEquipment(equipment);
        return "redirect:/";
    }


    @PostMapping("/update-equipment")
    public Equipment updateEquipment(@Valid Equipment equipment) {
        return es.updateEquipment(equipment);
    }


    @PostMapping("/delete-equipment")
    public String deleteEquipment(@RequestParam(name="id") String id) {
        if (es.deleteEquipment(Long.parseLong(id))) {
            System.out.println("############################## == DELETED   ");
        } else {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ NOT DELETED");
        }
        return "redirect:/";
    }

}
