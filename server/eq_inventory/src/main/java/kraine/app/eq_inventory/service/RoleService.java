package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Role;
import kraine.app.eq_inventory.model.RoleType;
import kraine.app.eq_inventory.repository.RoleRepositoryInterface;

@Service
@Transactional
public class RoleService {

    @Autowired
    RoleRepositoryInterface roleRepositoryInterface;

    public List<Role> getRoles() {
        return roleRepositoryInterface.findAll();
    }

    public Role getRole(RoleType roleType){
        return roleRepositoryInterface.findByRoleType(roleType);
    }
}
