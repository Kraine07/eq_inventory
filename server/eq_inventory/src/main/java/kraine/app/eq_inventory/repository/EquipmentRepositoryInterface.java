/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package kraine.app.eq_inventory.repository;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.Location;
import kraine.app.eq_inventory.model.Model;

import java.util.List;
import kraine.app.eq_inventory.model.Property;
import kraine.app.eq_inventory.model.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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



    @Query("SELECT e FROM Equipment e " +
        "LEFT JOIN FETCH e.model m " +
        "LEFT JOIN FETCH m.manufacturer " +
        "LEFT JOIN FETCH e.location l " +
        "LEFT JOIN FETCH l.property p " +
        "LEFT JOIN FETCH p.region " +
        "LEFT JOIN FETCH p.user u " +
        "LEFT JOIN FETCH u.role "
        )
    List<Equipment> findAllWithFullDetails();

    Equipment findBySerialNumber(String serialNumber);



    List<Equipment> findByModel(Model model);

    List<Equipment> findByLocation(Location location);
    
    List<Equipment> findByLocation_Property(Property property);
    
    Page<Equipment> findByLocation_Property_User(User user, Pageable pageable);
    

    // Page<Equipment> findByLocation_Property_User(User user, Pageable pageable);


}
