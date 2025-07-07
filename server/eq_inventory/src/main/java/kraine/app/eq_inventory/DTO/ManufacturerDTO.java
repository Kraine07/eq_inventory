package kraine.app.eq_inventory.DTO;

import kraine.app.eq_inventory.model.Manufacturer;

public record ManufacturerDTO(Long id, String name) {

    public static ManufacturerDTO from(Manufacturer m) {
        return new ManufacturerDTO(
            m.getId(),
            m.getName()
        );
    }

}
