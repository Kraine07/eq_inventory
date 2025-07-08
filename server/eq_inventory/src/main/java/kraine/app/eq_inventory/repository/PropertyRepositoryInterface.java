package kraine.app.eq_inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Property;

@Transactional
@Repository
public interface PropertyRepositoryInterface extends JpaRepository<Property, Long> {

    @Query("SELECT p FROM Property p " +
    "LEFT JOIN FETCH p.region " +
    "LEFT JOIN FETCH p.user u " +
    "LEFT JOIN FETCH u.role"
    )
    List<Property> findAllWithDetails();


}
