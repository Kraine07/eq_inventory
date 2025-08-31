/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kraine.app.eq_inventory.service;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.util.List;

import kraine.app.eq_inventory.SessionHandler;
import kraine.app.eq_inventory.DTO.EquipmentDTO;
import kraine.app.eq_inventory.DTO.LocationDTO;
import kraine.app.eq_inventory.DTO.ManufacturerDTO;
import kraine.app.eq_inventory.DTO.ModelDTO;
import kraine.app.eq_inventory.DTO.PropertyDTO;
import kraine.app.eq_inventory.DTO.RegionDTO;
import kraine.app.eq_inventory.DTO.RoleDTO;
import kraine.app.eq_inventory.DTO.UserDTO;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.Location;
import kraine.app.eq_inventory.model.Model;
import kraine.app.eq_inventory.model.Property;
import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.repository.EquipmentRepositoryInterface;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 *
 * @author Kraine
 */
@Service
@Transactional
@RequiredArgsConstructor
@CacheConfig(cacheNames = "equipment")
public class EquipmentService {


    @Autowired
    EquipmentRepositoryInterface eri;


    @Cacheable
    public List<Equipment> getAllWithFullDetails() {
        return eri.findAllWithFullDetails();
    }


    @CacheEvict(cacheNames = { "equipment", "equipmentDTOs" }, allEntries = true)
    public Equipment saveEquipment(Equipment equipment) {
        return eri.saveAndFlush(equipment);
    }


    @CacheEvict(cacheNames = { "equipment", "equipmentDTOs" }, allEntries = true)
    public void deleteEquipment(Long id) {
        eri.deleteById(id);
    }



    @Cacheable( key = "{#page, #size}")
    public Page<Equipment> getPage(int page, int size, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("location.property.name").ascending());
        User user = SessionHandler.getAttribute(request, "authUser", User.class);
        if(user.getIsAdmin()) {
            return eri.findAll(pageable);
        }
        return eri.findByLocation_Property_User(user, pageable);
    }



    public List<Equipment> getByProperty(Property property){
        return eri.findByLocation_Property(property);
    }

    public Page<Equipment> findByUser(User user) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("location.property.name").ascending());
        return eri.findByLocation_Property_User(user, pageable);
    }





    // DTOs

    @Cacheable("equipmentDTOs") // Cache DTOs separately
    public List<EquipmentDTO> getAllEquipmentDTOs() {
        return eri.findAllWithFullDetails().stream()
                .map(this::convertToDTO)
                .toList();
    }

    private EquipmentDTO convertToDTO(Equipment equipment) {
        return new EquipmentDTO(
                equipment.getId(),
                equipment.getSerialNumber(),
                equipment.getManufacturedDate(),
                convertModelToDTO(equipment.getModel()),
                convertLocationToDTO(equipment.getLocation())
        );
    }

    private ModelDTO convertModelToDTO(Model model) {
        return new ModelDTO(
                model.getDescription(),
                new ManufacturerDTO(
                    model.getManufacturer().getId(), 
                    model.getManufacturer().getName())
        );
    }

    private LocationDTO convertLocationToDTO(Location location) {
        return new LocationDTO(
                location.getName(),
                new PropertyDTO(
                    location.getProperty().getId(),
                    location.getProperty().getName(),
                    new RegionDTO(
                        location.getProperty().getRegion().getId(),
                        location.getProperty().getRegion().getName()
                    )   ,
                    new UserDTO(
                        location.getProperty().getUser().getId(),
                        location.getProperty().getUser().getFirstName(),
                        location.getProperty().getUser().getLastName(),
                        location.getProperty().getUser().getEmail(),
                        new RoleDTO(
                            location.getProperty().getUser().getRole().getId(),
                            location.getProperty().getUser().getRole().getRoleType()
                                ),
                        location.getProperty().getUser().getIsTemporaryPassword(),
                        location.getProperty().getUser().getIsSuspended(),
                        location.getProperty().getUser().getFailedAttempts(),
                        location.getProperty().getUser().getIsAdmin()
                    )
                )
        );
    }

}
