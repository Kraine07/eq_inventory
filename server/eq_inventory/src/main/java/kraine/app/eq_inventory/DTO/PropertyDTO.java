package kraine.app.eq_inventory.DTO;

import kraine.app.eq_inventory.model.Property;

public record PropertyDTO(Long id, String name, RegionDTO region, UserDTO user) {

    public static PropertyDTO from(Property p) {

        return new PropertyDTO(p.getId(), p.getName(), RegionDTO.from(p.getRegion()), UserDTO.from(p.getUser()));
    }

}
