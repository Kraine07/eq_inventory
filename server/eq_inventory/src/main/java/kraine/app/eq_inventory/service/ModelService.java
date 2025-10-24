package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ModelService {

    private final ModelRepositoryInterface modelRepository;
    private final ManufacturerRepositoryInterface manufacturerRepositoryInterface;
    private final EquipmentRepositoryInterface equipmentRepositoryInterface;





    @Caching(
        evict = {
            @CacheEvict(cacheNames = "manufacturerList", allEntries = true),
            @CacheEvict(cacheNames = "manufacturerDTOs", allEntries = true),
        },
        put = {
            @CachePut(cacheNames = "manufacturer", key = "#result.id")
        }
    )
    public Model saveModel(String manufacturer, String description, ModelId modelId) {

        Model existingModel = findByModelId(modelId);

        Model newModel = new Model(manufacturerRepositoryInterface.findManufacturerById(Long.valueOf(manufacturer)), description);
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




    @Caching(
        evict = {
            @CacheEvict(cacheNames = "modelList", allEntries = true),
            @CacheEvict(cacheNames = "modelDTOs", allEntries = true),
        },
        put = {
            @CachePut(cacheNames = "model", key = "#result.id")
        }
    )
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







    @Cacheable(cacheNames = "modelList")
    public List<Model> getAllModels() {
        return modelRepository.findAllWithDetails();
    }




    @Cacheable(cacheNames = "model", key = "#id", unless = "#result == null")
    public Model findByModelId(ModelId modelId) {
        return modelRepository.findById(modelId).orElse(null);
    }



    @Caching(
        evict = {
            @CacheEvict(cacheNames = "modelList", allEntries = true),
            @CacheEvict(cacheNames = "modelDTOs", allEntries = true),
            @CacheEvict(cacheNames = "model", key = "#id")
        }
    )
    public Boolean deleteModel(ModelId id) {

        // Find the model and remove it from the manufacturer's model list first
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


    @Cacheable(cacheNames = "modelDTOs")
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
