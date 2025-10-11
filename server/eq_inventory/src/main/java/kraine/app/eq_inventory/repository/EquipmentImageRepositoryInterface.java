package kraine.app.eq_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.EquipmentImage;


@Transactional
@Repository
public interface EquipmentImageRepositoryInterface extends JpaRepository<EquipmentImage, Long> {

    EquipmentImage findByEquipment(kraine.app.eq_inventory.model.Equipment equipment);
}

