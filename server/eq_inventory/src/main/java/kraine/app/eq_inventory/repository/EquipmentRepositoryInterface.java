/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package kraine.app.eq_inventory.repository;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kraine
 */
@Transactional
@Repository
public interface EquipmentRepositoryInterface extends JpaRepository<Equipment, Long> {
    
}
