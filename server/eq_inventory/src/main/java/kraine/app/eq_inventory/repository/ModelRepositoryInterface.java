package kraine.app.eq_inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Model;


@Repository
@Transactional
public interface ModelRepositoryInterface extends JpaRepository<Model, Long> {


}
