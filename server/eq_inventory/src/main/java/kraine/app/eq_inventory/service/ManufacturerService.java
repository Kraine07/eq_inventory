package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.DTO.ManufacturerDTO;
import kraine.app.eq_inventory.exception.DeleteManufacturerException;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.Manufacturer;
import kraine.app.eq_inventory.repository.EquipmentRepositoryInterface;
import kraine.app.eq_inventory.repository.ManufacturerRepositoryInterface;

@Service
@Transactional
@CacheConfig(cacheNames = "manufacturer")


public class ManufacturerService {


    @Autowired
    private ManufacturerRepositoryInterface manufacturerRepository;

    @Autowired
    private EquipmentRepositoryInterface equipmentRepositoryInterface;





    @Cacheable
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }



    @CacheEvict(cacheNames = {"manufacturer", "equipment", "model"}, allEntries = true)
    public Manufacturer saveManufacturer(Manufacturer manufacturer) {

        Manufacturer existingManufacturer = null;
        if (manufacturer.getId() != null) {
            existingManufacturer = findById(manufacturer.getId());
        }

        if (existingManufacturer != null) {
            existingManufacturer.setName(manufacturer.getName());
            return manufacturerRepository.saveAndFlush(existingManufacturer);
        }
        return manufacturerRepository.saveAndFlush(manufacturer);
    }



    @CacheEvict(cacheNames = {"manufacturer",  "model", "equipment"}, allEntries = true)
    public void deleteManufacturer(Long id) {

        // check if manufacturer has related equipment
        List<Equipment> equipment = equipmentRepositoryInterface.findAll();
        equipment.forEach(eq -> {
            if (eq.getModel().getManufacturer().getId().equals(id)) {
                throw new DeleteManufacturerException("Cannot delete manufacturer because related equipment exists.");
            }
        });
        manufacturerRepository.deleteById(id);
    }


    public Manufacturer findById(Long id) {
        return manufacturerRepository.findManufacturerById(id);
    }





    // DTO methods





    @Cacheable(cacheNames = "manufacturerDTOs")
    public List<ManufacturerDTO> getAllManufacturerDTOs() {
        return manufacturerRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }



    private ManufacturerDTO convertToDTO(Manufacturer manufacturer) {
        return new ManufacturerDTO(manufacturer.getId(), manufacturer.getName());
    }




}
