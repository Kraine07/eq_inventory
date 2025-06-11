package kraine.app.eq_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;


@Transactional
@Repository
public interface RegionRepositoryInterface extends JpaRepository<kraine.app.eq_inventory.model.Region, Long> {
    // This interface will automatically provide CRUD operations for the Region entity
    // Additional custom query methods can be defined here if needed

}
