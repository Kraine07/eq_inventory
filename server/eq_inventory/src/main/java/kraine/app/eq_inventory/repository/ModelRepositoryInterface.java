package kraine.app.eq_inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Model;
import kraine.app.eq_inventory.model.ModelId;


@Repository
@Transactional
public interface ModelRepositoryInterface extends JpaRepository<Model,ModelId> {

    @Query("SELECT m FROM Model m " +
    "LEFT JOIN FETCH m.manufacturer "
    )
    List<Model> findAllWithDetails();

    Model findByManufacturerIdAndDescription(Long manufacturer, String description);



}
