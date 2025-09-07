package kraine.app.eq_inventory.DTO;

import java.time.YearMonth;

import kraine.app.eq_inventory.model.Equipment;

public record EquipmentDTO(Long id, String serialNumber, YearMonth manufacturedDate, ModelDTO model,
        LocationDTO location) {

    public static EquipmentDTO from(Equipment e) {
        return new EquipmentDTO(
                e.getId(),
                e.getSerialNumber(),
                e.getManufacturedDate(),
                ModelDTO.from(e.getModel()),
                LocationDTO.from(e.getLocation()));
    }

}

