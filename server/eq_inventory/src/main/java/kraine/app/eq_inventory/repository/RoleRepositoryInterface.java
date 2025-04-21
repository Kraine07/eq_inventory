package kraine.app.eq_inventory.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Role;
import kraine.app.eq_inventory.model.RoleType;


@Transactional
@Repository
public interface RoleRepositoryInterface extends JpaRepository<Role, Long> {
    Role findByRoleType(RoleType roleType);
}
