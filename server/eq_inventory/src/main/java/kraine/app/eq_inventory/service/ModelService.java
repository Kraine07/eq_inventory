package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.Manufacturer;
import kraine.app.eq_inventory.model.Model;
import kraine.app.eq_inventory.model.ModelId;
import kraine.app.eq_inventory.repository.EquipmentRepositoryInterface;
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

    @Autowired
    EquipmentRepositoryInterface equipmentRepositoryInterface;





    @CacheEvict(cacheNames = {"model","equipment","manufacturer"},allEntries = true)
    public Model saveModel(String manufacturer, String description, ModelId modelId) {

        Model existingModel = findByModelId(modelId);

        Model newModel = new Model(mri.findManufacturerById(Long.valueOf(manufacturer)), description);
        Model result = modelRepository.saveAndFlush(newModel);
        if (existingModel != null) {

            List<Equipment> equipmentList = equipmentRepositoryInterface.findByModel(existingModel);

            for (Equipment eq : equipmentList) {
                eq.setModel(newModel);
            }
            equipmentRepositoryInterface.saveAllAndFlush(equipmentList);
            modelRepository.delete(existingModel);

        }

        return result;
    }




    @Cacheable
    public List<Model> getAllModels() {
        return modelRepository.findAllWithDetails();
    }




    public Model findByModelId(ModelId modelId) {
        return modelRepository.findById(modelId).orElse(null);
        // return modelRepository.findByManufacturerIdAndDescription(modelId.getManufacturer(), modelId.getDescription());
    }



    @CacheEvict(cacheNames = { "model","equipment","manufacturer" }, allEntries = true)
    public void deleteModel(ModelId id) {
        Model model = findByModelId(id);
        Manufacturer manufacturer = model.getManufacturer();
        manufacturer.getModels().remove(model);
        modelRepository.deleteById(id);
    }
}
