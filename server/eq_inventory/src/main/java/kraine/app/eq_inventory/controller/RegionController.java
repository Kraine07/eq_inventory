package kraine.app.eq_inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import kraine.app.eq_inventory.model.Region;
import kraine.app.eq_inventory.service.RegionService;


@Controller
public class RegionController {

    @Autowired
    private RegionService regionService;

    @PostMapping("/add-region")
    public String addRegion(@Valid Region region, BindingResult bindingResult, Model model) {


        if (bindingResult.hasErrors()) {
            model.addAttribute("region", region);
        }

        regionService.addRegion(region);

        // TODO: Add a success message to the model
        return "redirect:/";
    }

}
