package kraine.app.eq_inventory.DTO;

import kraine.app.eq_inventory.model.Region;

public record RegionDTO(Long id, String name) {

    public static RegionDTO from(Region r) {
        return new RegionDTO(r.getId(), r.getName());
    }

}
