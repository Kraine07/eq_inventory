package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Model;
import kraine.app.eq_inventory.model.ModelId;
import kraine.app.eq_inventory.repository.ManufacturerRepositoryInterface;
import kraine.app.eq_inventory.repository.ModelRepositoryInterface;

@Service
@Transactional
@CacheConfig(cacheNames = "model")
public class ModelService {

    @Autowired
    private ModelRepositoryInterface modelRepository;
    
    @Autowired
    private ManufacturerRepositoryInterface mri;





    @CacheEvict(cacheNames = {"model"},allEntries = true)
    public Model saveModel(String manufacturer, String description, ModelId modelId) {
        Model existingModel = findByModelId(modelId);

        if (existingModel != null) {
            modelRepository.delete(existingModel);
        }
        Model newModel = new Model();
        newModel.setManufacturer(mri.findManufacturerById(Long.valueOf(manufacturer)));
        newModel.setDescription(description);

        return modelRepository.save(newModel);
    }





    @Cacheable
    public List<Model> getAllModels() {
        return modelRepository.findAllWithDetails();
    }




    public Model findByModelId(ModelId modelId) {
        return modelRepository.findByManufacturerIdAndDescription(modelId.getManufacturer(), modelId.getDescription());
    }




    @CacheEvict(cacheNames = { "model" }, allEntries = true)
    public void deleteModel(ModelId id){
        modelRepository.deleteById(id);
    }
}
