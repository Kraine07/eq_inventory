package kraine.app.eq_inventory.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.LoginAttempt;
import kraine.app.eq_inventory.model.User;

@Transactional
@Repository
public interface LoginAttemptsRepositoryInterface extends JpaRepository<LoginAttempt, Long>{
    List<LoginAttempt> findByEmail(String email);
}
