package kraine.app.eq_inventory.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.User;

@Transactional
@Repository
public interface UserRepoInterface extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u " +
        "LEFT JOIN FETCH u.role "
    )
    List<User> findAllWithDetails();

    User findByEmail(String email);

    Boolean existsByEmail(String email);
}
