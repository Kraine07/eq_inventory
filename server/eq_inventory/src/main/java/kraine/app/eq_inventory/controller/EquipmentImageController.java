package kraine.app.eq_inventory.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import kraine.app.eq_inventory.model.Equipment;
import kraine.app.eq_inventory.model.EquipmentImage;
import kraine.app.eq_inventory.service.EquipmentImageService;
import kraine.app.eq_inventory.service.EquipmentService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class EquipmentImageController {

    private EquipmentImageService equipmentImageService;
    private EquipmentService equipmentService;

    public EquipmentImageController(EquipmentImageService equipmentImageService, EquipmentService equipmentService) {
        this.equipmentImageService = equipmentImageService;
        this.equipmentService = equipmentService;
    }





    @GetMapping("/get-equipment-images")
    public List<EquipmentImage> getEquipmentImages() {
        return equipmentImageService.getAllImages();
    }




    @PostMapping(value = "/save-equipment-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadEquipmentImage(
        @RequestParam("equipment") Long equipmentId,
        @RequestParam("equipment-image") MultipartFile image
    ) throws IOException {

        Equipment equipment = equipmentService.getEquipmentById(equipmentId);
        if (equipment == null) {
            return null;
        }

        EquipmentImage equipmentImage = new EquipmentImage(null, equipment, image.getOriginalFilename(), image.getBytes());
        equipmentImageService.saveImage(equipmentId, equipmentImage);
        return "redirect:/";
    }




    // @GetMapping("/fnd-by-equipment")
    // public EquipmentImage findByEquipment(@RequestParam Long equipmentId) {
    //         return equipmentImageService.getImageByEquipment(equipmentId);

    // }





}
