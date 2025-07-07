package kraine.app.eq_inventory.DTO;

import kraine.app.eq_inventory.model.Location;

public record LocationDTO(Long id, String name, PropertyDTO property) {

    public static LocationDTO from(Location l) {
        return new LocationDTO(l.getId(), l.getName(), PropertyDTO.from(l.getProperty()));
    }

}
