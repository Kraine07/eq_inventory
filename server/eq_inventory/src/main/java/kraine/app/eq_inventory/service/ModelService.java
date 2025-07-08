package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.model.Model;
import kraine.app.eq_inventory.repository.ModelRepositoryInterface;

@Service
@Transactional
@CacheConfig(cacheNames = "model")
public class ModelService {

    @Autowired
    private ModelRepositoryInterface modelRepository;

    @CacheEvict(allEntries = true)
    public Model createModel(Model model) {
        return modelRepository.save(model);
    }

    @Cacheable
    public List<Model> getAllModels() {
        return modelRepository.findAllWithDetails();
    }

}
