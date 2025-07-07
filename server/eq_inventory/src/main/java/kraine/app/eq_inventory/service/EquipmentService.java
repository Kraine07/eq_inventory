/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kraine.app.eq_inventory.service;

import jakarta.transaction.Transactional;
import java.util.List;

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
import kraine.app.eq_inventory.repository.EquipmentRepositoryInterface;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
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
public class EquipmentService {


    @Autowired
    EquipmentRepositoryInterface eri;


    public Equipment addEquipment(Equipment equipment){
        return eri.saveAndFlush(equipment);
    }


    public List<Equipment> getAllWithFullDetails() {
        return eri.findAllWithFullDetails();
    }


    public Equipment updateEquipment(Equipment equipment) {
        return eri.saveAndFlush(equipment);
    }

    public boolean deleteEquipment(Long id) {
        return eri.deleteEquipmentById(id);
    }


    public Page<Equipment> getPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return eri.findAll(pageable);
    }



    // DTOs
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
                model.getId(),
                model.getDescription(),
                new ManufacturerDTO(
                    model.getManufacturer().getId(), 
                    model.getManufacturer().getName())
        );
    }

    private LocationDTO convertLocationToDTO(Location location) {
        return new LocationDTO(
                location.getId(),
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
