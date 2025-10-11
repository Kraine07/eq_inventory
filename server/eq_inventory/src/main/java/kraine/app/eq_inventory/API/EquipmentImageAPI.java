package kraine.app.eq_inventory.API;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.EquipmentImage;

@RestController
@RequestMapping("/api/v1")
public class EquipmentImageAPI {


    private final kraine.app.eq_inventory.service.EquipmentImageService equipmentImageService;
    private final kraine.app.eq_inventory.service.EquipmentService equipmentService;

    public EquipmentImageAPI(kraine.app.eq_inventory.service.EquipmentImageService equipmentImageService,
            kraine.app.eq_inventory.service.EquipmentService equipmentService) {
        this.equipmentImageService = equipmentImageService;
        this.equipmentService = equipmentService;
    }





    @PostMapping(value = "/save-equipment-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public EquipmentImage postMethodName(
        @RequestParam("equipment") Long equipmentId,
        @RequestParam("equipment-image") MultipartFile image
    ) throws IOException {

        Equipment equipment = equipmentService.getEquipmentById(equipmentId);
        if (equipment == null) {
            return null;
        }

        EquipmentImage equipmentImage = new EquipmentImage(null, equipment, image.getOriginalFilename(), image.getBytes());
        return equipmentImageService.saveImage(equipmentImage);
    }





    @GetMapping("/fnd-by-equipment")
    public ResponseEntity<byte[]> findByEquipment(@RequestParam Long equipmentId) {
        EquipmentImage image = equipmentImageService.getImageByEquipment(equipmentId);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // or dynamically set based on image.getFileName()
        headers.setContentDispositionFormData("inline", image.getImageName());

        return new ResponseEntity<>(image.getImageData(), headers, HttpStatus.OK);
    }
}
