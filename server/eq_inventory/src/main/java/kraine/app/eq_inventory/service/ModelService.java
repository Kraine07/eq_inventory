package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Model;
import kraine.app.eq_inventory.repository.ModelRepositoryInterface;

@Service
@Transactional
public class ModelService {

    @Autowired
    private ModelRepositoryInterface modelRepository;

    public Model createModel(Model model) {
        return modelRepository.save(model);
    }

    public List<Model> getAllModels() {
        return modelRepository.findAll();
    }

}
