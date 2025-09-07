package kraine.app.eq_inventory.DTO;

import kraine.app.eq_inventory.model.User;

public record UserDTO(Long id, String firstName, String lastName, String email, RoleDTO role, Boolean isTemporaryPassword, Boolean isSuspended, int failedAttempts, Boolean isAdmin) {

    public static UserDTO from(User u) {

        return new UserDTO(
            u.getId(),
            u.getFirstName(),
            u.getLastName(),
            u.getEmail(),
            RoleDTO.from(u.getRole()),
            u.getIsTemporaryPassword(),
            u.getIsSuspended(),
            u.getFailedAttempts(),
            u.getIsAdmin()
        ) ;
    }

}
