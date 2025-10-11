package kraine.app.eq_inventory.DTO;

public record EquipmentImageDTO(Long id, Long equipmentId, String imageName, byte[] imageData) {

    public static EquipmentImageDTO from(kraine.app.eq_inventory.model.EquipmentImage ei) {
        return new EquipmentImageDTO(
                ei.getId(),
                ei.getEquipment().getId(),
                ei.getImageName(),
                ei.getImageData());
    }

}
