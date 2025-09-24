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
import kraine.app.eq_inventory.DTO.ModelDTO;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.Property;
import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.repository.EquipmentRepositoryInterface;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
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
// @CacheConfig(cacheNames = "equipment")
public class EquipmentService {


    @Autowired
    EquipmentRepositoryInterface eri;


    public List<Equipment> getAllWithFullDetails() {
        return eri.findAllWithFullDetails();
    }



    public EquipmentDTO saveEquipment(Equipment equipment) {
        Equipment savedEquipment = eri.saveAndFlush(equipment);
        return convertToDTO(savedEquipment);
    }



    public boolean deleteEquipment(Long id) {
        if (eri.existsById(id)) {
            eri.deleteById(id);
            return true;
        }
        return false;
    }





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


    @Cacheable(cacheNames = "equipmentDTOs")
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
                // convertModelToDTO(equipment.getModel()),
                ModelDTO.from(equipment.getModel()),
                LocationDTO.from(equipment.getLocation())
        );
    }


}
