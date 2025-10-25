
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


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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


    private final EquipmentRepositoryInterface eri;


    @Cacheable("equipmentList")
    public List<Equipment> getAllWithFullDetails() {
        return eri.findAllWithFullDetails();
    }


    @Cacheable(cacheNames = "equipment", key = "#id", unless = "#result == null")
    public Equipment getEquipmentById(Long id) {
        return eri.findById(id).orElse(null);
    }



    @Caching(
        evict = {
            @CacheEvict(cacheNames = "equipmentList", allEntries = true),
            @CacheEvict(cacheNames = "equipmentDTOs", allEntries = true),
        },
        put = {
            @CachePut(cacheNames = "equipment", key = "#result.id")
        }
    )
    public EquipmentDTO saveEquipment(Equipment equipment) {
        Equipment savedEquipment = eri.saveAndFlush(equipment);
        return convertToDTO(savedEquipment);
    }




    @Caching(evict = {
            @CacheEvict(cacheNames = "equipmentList", allEntries = true),
            @CacheEvict(cacheNames = "equipmentDTOs", allEntries = true),
            @CacheEvict(cacheNames = "equipment", key = "#id")
    })
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
                ModelDTO.from(equipment.getModel()),
                LocationDTO.from(equipment.getLocation())
        );
    }


}
