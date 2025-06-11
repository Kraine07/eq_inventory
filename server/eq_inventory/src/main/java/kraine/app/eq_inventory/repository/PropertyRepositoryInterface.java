package kraine.app.eq_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Property;

@Transactional
@Repository
public interface PropertyRepositoryInterface extends JpaRepository<Property, Long> {

    // Custom query methods can be defined here if needed
    // For example:
    // List<Property> findByName(String name);

    // Additional methods can be added as per requirements


}
