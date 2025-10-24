package kraine.app.eq_inventory.service;

import java.util.List;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.DTO.ManufacturerDTO;
import kraine.app.eq_inventory.exception.DeleteManufacturerException;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.Manufacturer;
import kraine.app.eq_inventory.repository.EquipmentRepositoryInterface;
import kraine.app.eq_inventory.repository.ManufacturerRepositoryInterface;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor


public class ManufacturerService {


    private final ManufacturerRepositoryInterface manufacturerRepository;

    private final EquipmentRepositoryInterface equipmentRepositoryInterface;





    @Cacheable(cacheNames = "manufacturerList")
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }



    @Caching(
        evict = {
            @CacheEvict(cacheNames = "manufacturerList", allEntries = true),
            @CacheEvict(cacheNames = "model", allEntries = true),
            @CacheEvict(cacheNames = "equipment", allEntries = true)
        },
        put = {
            @CachePut(cacheNames = "manufacturer", key = "#result.id")
        }
    )
    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        // Check if name is already taken (by another manufacturer)
        Manufacturer existingByName = manufacturerRepository.findByName(manufacturer.getName());
        if (existingByName != null && !existingByName.getId().equals(manufacturer.getId())) {
            throw new IllegalArgumentException(
                    "Manufacturer with name '" + manufacturer.getName() + "' already exists.");
        }

        if (manufacturer.getId() == null) {
            // ✅ NEW Manufacturer
            return manufacturerRepository.saveAndFlush(manufacturer);
        } else {
            // ✅ UPDATE Manufacturer
            Manufacturer managed = manufacturerRepository.findById(manufacturer.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Manufacturer not found"));

            // Update simple fields
            managed.setName(manufacturer.getName());

            // Update models safely (avoid replacing collection)
            managed.getModels().clear();
            if (manufacturer.getModels() != null && !manufacturer.getModels().isEmpty()) {
                managed.getModels().clear();
                managed.getModels().addAll(manufacturer.getModels());
            }

            return manufacturerRepository.saveAndFlush(managed);
        }
    }









    @Caching(evict = {
            @CacheEvict(cacheNames = "manufacturerList", allEntries = true),
            @CacheEvict(cacheNames = "manufacturerDTOs", allEntries = true),
            @CacheEvict(cacheNames = "manufacturer", key = "#id")
    })
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
