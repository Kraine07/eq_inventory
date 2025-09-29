package kraine.app.eq_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Manufacturer;

@Repository
@Transactional
public interface ManufacturerRepositoryInterface extends JpaRepository<Manufacturer, Long> {


    Manufacturer findManufacturerById(Long id);
    Manufacturer findByName(String name);

}
