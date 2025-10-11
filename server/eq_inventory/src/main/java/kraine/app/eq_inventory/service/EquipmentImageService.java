package kraine.app.eq_inventory.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.EquipmentImage;
import kraine.app.eq_inventory.repository.EquipmentImageRepositoryInterface;
import kraine.app.eq_inventory.repository.EquipmentRepositoryInterface;

@Transactional
@Service
public class EquipmentImageService {

    @Autowired
    private EquipmentImageRepositoryInterface equipmentImageRepository;

    @Autowired
    private EquipmentRepositoryInterface equipmentRepository;


    public List<EquipmentImage> getAllImages() {
        return equipmentImageRepository.findAll();
    }


    public EquipmentImage saveImage(EquipmentImage image) {

        return equipmentImageRepository.saveAndFlush(image);
    }


    public EquipmentImage getImageByEquipment(Long equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);
        if (equipment == null) {
            return null;
        }
        return equipmentImageRepository.findByEquipment(equipment);
    }

    public boolean deleteImage(Long id) {
        equipmentImageRepository.deleteById(id);
        return !equipmentImageRepository.existsById(id);
    }

}
