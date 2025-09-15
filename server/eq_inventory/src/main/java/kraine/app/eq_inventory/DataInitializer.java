package kraine.app.eq_inventory;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import kraine.app.eq_inventory.model.Region;
import kraine.app.eq_inventory.model.Role;
import kraine.app.eq_inventory.model.RoleType;
import kraine.app.eq_inventory.repository.RegionRepositoryInterface;
import kraine.app.eq_inventory.repository.RoleRepositoryInterface;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleRepositoryInterface roleRepository;
    private final RegionRepositoryInterface regionRepositoryInterface;

    public DataInitializer(RoleRepositoryInterface roleRepository, RegionRepositoryInterface regionRepositoryInterface) {
        this.roleRepository = roleRepository;
        this.regionRepositoryInterface = regionRepositoryInterface;
    }

    @Override
    public void run(String... args) throws Exception {
        // populate role table if empty
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(null, RoleType.ADMINISTRATOR, null));
            roleRepository.save(new Role(null, RoleType.EDITOR, null));
            System.out.println("Roles initialized");
        }
        if (regionRepositoryInterface.count() == 0) {
            regionRepositoryInterface.save(new Region(null,"Region 1"));
            regionRepositoryInterface.save(new Region(null,"Region 2"));
            regionRepositoryInterface.save(new Region(null,"Region 3"));
            System.out.println("Default regions created");
        }
    }
}