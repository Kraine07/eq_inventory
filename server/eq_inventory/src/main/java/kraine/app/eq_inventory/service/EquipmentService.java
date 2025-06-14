/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kraine.app.eq_inventory.service;

import jakarta.transaction.Transactional;
import java.util.List;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.repository.EquipmentRepositoryInterface;


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
public class EquipmentService {
    
    @Autowired
    EquipmentRepositoryInterface eri;
    
    public Equipment addEquipment(Equipment equipment){
        return eri.save(equipment);
    }
    
    public List<Equipment> getAllEquipment() {
        return eri.findAll();
    }
    
    public Page<Equipment> getPage(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
    return eri.findAll(pageable);
}
}
