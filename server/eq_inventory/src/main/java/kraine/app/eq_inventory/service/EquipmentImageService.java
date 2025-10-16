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



    public EquipmentImage saveImage(Long equipmentId, EquipmentImage newImage) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        EquipmentImage existing = equipmentImageRepository.findByEquipment(equipment);

        if (existing != null) {
            // Delete the old row completely
            equipmentImageRepository.delete(existing);
            equipmentImageRepository.flush(); // make sure it's actually gone
        }

        // Attach the equipment and save the new one
        newImage.setEquipment(equipment);
        return equipmentImageRepository.saveAndFlush(newImage);
    }


    // public EquipmentImage saveImage(Long equipmentId, EquipmentImage newImage) {
    //     // Fetch managed Equipment entity
    //     Equipment equipment = equipmentRepository.findById(equipmentId)
    //             .orElseThrow(() -> new EntityNotFoundException("Equipment not found with id " + equipmentId));

    //     // Fetch existing image if it exists
    //     EquipmentImage existingImage = equipmentImageRepository.findByEquipment(equipment);

    //     if (existingImage != null) {
    //         // Replace the image data and name
    //         existingImage.setImageData(newImage.getImageData().clone()); // clone to avoid reference issues
    //         existingImage.setImageName(newImage.getImageName());
    //         // Hibernate will detect changes because this entity is managed
    //         return equipmentImageRepository.saveAndFlush(existingImage);
    //     } else {
    //         // Link and insert new one
    //         newImage.setEquipment(equipment);
    //         return equipmentImageRepository.saveAndFlush(newImage);
    //     }
    // }



    public EquipmentImage getImageByEquipment(Long equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId).orElse(null);
        if (equipment == null) {
            return null;
        }
        return equipmentImageRepository.findByEquipment(equipment);
    }




    public boolean deleteImage(Long id) {
        EquipmentImage image = equipmentImageRepository.findByEquipment(equipmentRepository.findById(id).orElse(null));
        if (image != null) {
            equipmentImageRepository.delete(image);
            return true;
        }
        return false;
    }

}
