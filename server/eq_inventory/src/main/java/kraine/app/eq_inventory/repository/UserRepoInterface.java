package kraine.app.eq_inventory.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.User;

@Transactional
@Repository
public interface UserRepoInterface extends JpaRepository<User, Long>{


    User findByEmail(String email);

    Boolean existsByEmail(String email);
}
