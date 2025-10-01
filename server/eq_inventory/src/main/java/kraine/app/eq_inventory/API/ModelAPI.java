package kraine.app.eq_inventory.API;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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



    @PostMapping("/save-model")
    public ResponseEntity<?> saveModel(@RequestBody Model model) {
        ModelId modelId = new ModelId(model.getManufacturer().getId(), model.getDescription());

        try {
            Model savedModel = modelService.saveModel(model.getManufacturer(), model.getDescription(), modelId);
            return ResponseEntity.ok(savedModel);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error saving model."));
        }
    }




    @PostMapping("/delete-model")
    public ResponseEntity<?> deleteModel(@RequestBody ModelId id){

        Boolean deleted = modelService.deleteModel(id);
        if (deleted) {
            return ResponseEntity.ok(Map.of("success", "Model deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Model not found"));
        }
    }




    @PostMapping("/find-model")
    public Model getModel(@RequestBody ModelId id){
        return modelService.findByModelId(id);
    }

}
