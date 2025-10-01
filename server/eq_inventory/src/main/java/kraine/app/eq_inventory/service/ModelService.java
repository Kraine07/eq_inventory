package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.DTO.ManufacturerDTO;
import kraine.app.eq_inventory.DTO.ModelDTO;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.Manufacturer;
import kraine.app.eq_inventory.model.Model;
import kraine.app.eq_inventory.model.ModelId;
import kraine.app.eq_inventory.repository.EquipmentRepositoryInterface;
import kraine.app.eq_inventory.repository.ManufacturerRepositoryInterface;
import kraine.app.eq_inventory.repository.ModelRepositoryInterface;

@Service
@Transactional
// @CacheConfig(cacheNames = "model")
public class ModelService {

    @Autowired
    private ModelRepositoryInterface modelRepository;

    @Autowired
    private ManufacturerRepositoryInterface mri;

    @Autowired
    EquipmentRepositoryInterface equipmentRepositoryInterface;





    // @CacheEvict(cacheNames = {"model","equipment","manufacturer"},allEntries = true)
    public Model saveModel(String manufacturer, String description, ModelId modelId) {

        Model existingModel = findByModelId(modelId);

        Model newModel = new Model(mri.findManufacturerById(Long.valueOf(manufacturer)), description);
        Model result = modelRepository.saveAndFlush(newModel);
        if (existingModel != null) {
            Manufacturer existingManufacturer = existingModel.getManufacturer();
            List<Equipment> equipmentList = equipmentRepositoryInterface.findByModel(existingModel);

            for (Equipment eq : equipmentList) {
                eq.setModel(newModel);
            }
            equipmentRepositoryInterface.saveAllAndFlush(equipmentList);
            existingManufacturer.getModels().remove(existingModel);
            modelRepository.delete(existingModel);

        }

        return result;
    }




    public Model saveModel(Manufacturer manufacturer, String description, ModelId modelId) {

        Model existingModel = findByModelId(modelId);

        Model newModel = new Model(manufacturer, description);
        Model result = modelRepository.saveAndFlush(newModel);
        if (existingModel != null) {
            Manufacturer existingManufacturer = existingModel.getManufacturer();
            List<Equipment> equipmentList = equipmentRepositoryInterface.findByModel(existingModel);

            for (Equipment eq : equipmentList) {
                eq.setModel(newModel);
            }
            equipmentRepositoryInterface.saveAllAndFlush(equipmentList);
            existingManufacturer.getModels().remove(existingModel);
            modelRepository.delete(existingModel);

        }

        return result;
    }







    // @Cacheable
    public List<Model> getAllModels() {
        return modelRepository.findAllWithDetails();
    }




    public Model findByModelId(ModelId modelId) {
        return modelRepository.findById(modelId).orElse(null);
        // return modelRepository.findByManufacturerIdAndDescription(modelId.getManufacturer(), modelId.getDescription());
    }



    // @CacheEvict(cacheNames = { "model","equipment","manufacturer" }, allEntries = true)
    public Boolean deleteModel(ModelId id) {

        // Find the model and remove it from the manufacturer's model list
        Model model = findByModelId(id);
        Manufacturer manufacturer = model.getManufacturer();
        manufacturer.getModels().remove(model);

        try{
            modelRepository.deleteById(id);

            if (modelRepository.findById(id).isPresent()) {
                return false;
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }




    // DTO Methods


    // @Cacheable(cacheNames = "modelDTOs")
    public List<ModelDTO> getAllModelDTOs() {
        return modelRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    private ModelDTO convertToDTO(Model model) {
        return new ModelDTO(
                model.getDescription(),
                ManufacturerDTO.from(model.getManufacturer())
        );
    }


}
