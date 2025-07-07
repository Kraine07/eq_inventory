/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package kraine.app.eq_inventory.repository;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.DTO.EquipmentDTO;
import kraine.app.eq_inventory.model.Equipment;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kraine
 */
@Transactional
@Repository
public interface EquipmentRepositoryInterface extends JpaRepository<Equipment, Long> {

    // @EntityGraph(attributePaths = { "model", "model.manufacturer", "location", "location.property", "location.property.region", "location.property.user" })

    @Query("SELECT e FROM Equipment e " +
        "LEFT JOIN FETCH e.model m " +
        "LEFT JOIN FETCH m.manufacturer " +
        "LEFT JOIN FETCH e.location l " +
        "LEFT JOIN FETCH l.property p " +
        "LEFT JOIN FETCH p.region " +
        "LEFT JOIN FETCH p.user")
    List<Equipment> findAllWithFullDetails();


    boolean deleteEquipmentById(Long id);
}
