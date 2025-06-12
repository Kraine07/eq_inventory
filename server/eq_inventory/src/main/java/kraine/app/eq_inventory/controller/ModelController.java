package kraine.app.eq_inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import kraine.app.eq_inventory.model.Model;
import kraine.app.eq_inventory.service.ModelService;

import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;


@Controller
public class ModelController {

    @Autowired
    private ModelService modelService;

    @PostMapping("add-model")
    public String addModel(@Valid Model model, BindingResult bindingResult) throws BindException {

        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        modelService.createModel(model);
        return "redirect:/";
    }

}
