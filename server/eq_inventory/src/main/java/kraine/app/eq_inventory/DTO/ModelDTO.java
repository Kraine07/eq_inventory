package kraine.app.eq_inventory.DTO;

import kraine.app.eq_inventory.model.Model;

public record ModelDTO( String description, ManufacturerDTO manufacturer) {

    public static ModelDTO from(Model m) {
        return new ModelDTO(
            m.getDescription(),
            ManufacturerDTO.from(m.getManufacturer())
        );

    }

}
