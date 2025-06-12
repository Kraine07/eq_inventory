package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Manufacturer;
import kraine.app.eq_inventory.repository.ManufacturerRepositoryInterface;

@Service
@Transactional
public class ManufacturerService {


    @Autowired
    private ManufacturerRepositoryInterface manufacturerRepository;


    public Manufacturer addManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }
}
