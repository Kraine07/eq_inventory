package kraine.app.eq_inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Location;
import kraine.app.eq_inventory.model.LocationId;

@Repository
@Transactional
public interface LocationRepositoryInterface extends JpaRepository<Location, LocationId> {


    @Query("SELECT l FROM Location l " +
        "LEFT JOIN FETCH l.property p " +
        "LEFT JOIN FETCH p.region " +
        "LEFT JOIN FETCH p.user u " +
        "LEFT JOIN FETCH u.role")
    List<Location> findAllWithFullDetails();

    Location findByPropertyIdAndName(Long property, String name);


}