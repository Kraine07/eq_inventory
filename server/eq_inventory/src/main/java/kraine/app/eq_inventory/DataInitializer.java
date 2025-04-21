package kraine.app.eq_inventory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import kraine.app.eq_inventory.model.Role;
import kraine.app.eq_inventory.model.RoleType;
import kraine.app.eq_inventory.repository.RoleRepositoryInterface;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleRepositoryInterface roleRepository;

    public DataInitializer(RoleRepositoryInterface roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(null, RoleType.ADMINISTRATOR, null));
            roleRepository.save(new Role(null, RoleType.EDITOR, null));
            System.out.println("Roles initialized");
        }
    }
}