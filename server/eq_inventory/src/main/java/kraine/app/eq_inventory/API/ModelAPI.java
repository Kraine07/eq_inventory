package kraine.app.eq_inventory.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraine.app.eq_inventory.model.Model;
import kraine.app.eq_inventory.model.ModelId;
import kraine.app.eq_inventory.service.ModelService;

@RestController
@RequestMapping("/api/v1")
public class ModelAPI {


    @Autowired
    ModelService modelService;



    @GetMapping("/get-all-models")
    public List<Model> getAllModels() {
        return modelService.getAllModels();
    }

    @PostMapping("/find-model")
    public Model getModel(@RequestBody ModelId id){
        return modelService.findByModelId(id);
    }

}
