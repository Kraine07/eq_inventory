package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.exception.DeleteManufacturerException;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.Manufacturer;
import kraine.app.eq_inventory.model.Model;
import kraine.app.eq_inventory.repository.EquipmentRepositoryInterface;
import kraine.app.eq_inventory.repository.ManufacturerRepositoryInterface;
import kraine.app.eq_inventory.repository.ModelRepositoryInterface;

@Service
@Transactional
@CacheConfig(cacheNames = "manufacturer")


public class ManufacturerService {


    @Autowired
    private ManufacturerRepositoryInterface manufacturerRepository;

    @Autowired
    private EquipmentRepositoryInterface equipmentRepositoryInterface;



    @CacheEvict(cacheNames = {"manufacturer","equipment", "model"}, allEntries = true)
    public Manufacturer addManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.saveAndFlush(manufacturer);
    }



    @Cacheable
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }



    @CacheEvict(cacheNames = {"manufacturer", "equipment", "model"}, allEntries = true)
    public Manufacturer updateManufacturer(Manufacturer manufacturer) {
        Manufacturer existingManufacturer = manufacturerRepository.findById(manufacturer.getId()).orElse(null);

        existingManufacturer.setName(manufacturer.getName());
        return manufacturerRepository.saveAndFlush(existingManufacturer);
    }



    @CacheEvict(cacheNames = {"manufacturer",  "model"}, allEntries = true)
    public void deleteManufacturer(Long id) {

        List<Equipment> equipment = equipmentRepositoryInterface.findAll();
        equipment.forEach(eq -> {
            if (eq.getModel().getManufacturer().getId() == id) {
                throw new DeleteManufacturerException("Cannot delete manufacturer because related equipment exists.");
            }
        });
        manufacturerRepository.deleteById(id);
    }


    public Manufacturer findById(Long id) {
        return manufacturerRepository.findManufacturerById(id);
    }
}
