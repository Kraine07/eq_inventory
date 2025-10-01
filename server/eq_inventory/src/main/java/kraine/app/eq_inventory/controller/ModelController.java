package kraine.app.eq_inventory.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;

import kraine.app.eq_inventory.model.ModelId;
import kraine.app.eq_inventory.service.ModelService;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class ModelController {


    @Autowired
    private ModelService modelService;




    private ModelId createModelId(String id) {
        String[] idParts = id.split(",");
        ModelId modelId = new ModelId(Long.valueOf(idParts[0]), idParts[1]);
        return modelId;
    }


    @PostMapping("/add-model")
    public String saveModel(@Param("manufacturer") String manufacturerId, @Param("description") String description,
            @Param("id") String id) {

        ModelId modelId = createModelId(id);
        modelService.saveModel(manufacturerId, description, modelId);
        return "redirect:/";
    }



    @PostMapping("/delete-model")
    public String deleteModel(@Param("id") String id) {
        // get modelid
        ModelId modelId = createModelId(id);
        modelService.deleteModel(modelId);
        return "redirect:/";
    }

}
