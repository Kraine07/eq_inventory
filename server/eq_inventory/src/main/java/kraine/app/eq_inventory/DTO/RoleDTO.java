package kraine.app.eq_inventory.DTO;

import kraine.app.eq_inventory.model.Role;
import kraine.app.eq_inventory.model.RoleType;

public record RoleDTO(Long id, RoleType roleType) {

    public static RoleDTO from(Role r) {
        return new RoleDTO(r.getId(), r.getRoleType());
    }

}
