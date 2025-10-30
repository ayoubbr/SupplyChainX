package ma.youcode.supplyChainX.service;

import ma.youcode.supplyChainX.model.RawMaterial;
import ma.youcode.supplyChainX.repository.RawMaterialRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public class RawMaterialService {

    private RawMaterialRepository rawMaterialRepository;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public RawMaterial save(RawMaterial rawMaterial) {
        return rawMaterialRepository.save(rawMaterial);
    }

    public RawMaterial update(RawMaterial rawMaterial, Long id) {

        RawMaterial existingRawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Raw material with ID " + id + " not found."));

        existingRawMaterial.setName(rawMaterial.getName());
        existingRawMaterial.setStock(rawMaterial.getStock());
        existingRawMaterial.setStockMin(rawMaterial.getStockMin());
        existingRawMaterial.setUnit(rawMaterial.getUnit());
        existingRawMaterial.setSuppliers(rawMaterial.getSuppliers());

        return rawMaterialRepository.save(existingRawMaterial);
    }

    public void deleteById(Long id) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Raw material with ID " + id + " not found."));

        if (!rawMaterial.getSupplyOrderRawMaterials().isEmpty()) {
            throw new IllegalArgumentException("Cannot delete raw material with associated supply orders.");
        }

        rawMaterialRepository.delete(rawMaterial);
    }

    public List<RawMaterial> findAll() {
        return rawMaterialRepository.findAll();
    }

    public RawMaterial findById(Long id) {
        return rawMaterialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Raw material with ID " + id + " not found."));
    }

    public List<RawMaterial> getRawMaterialsBelowStock() {
        return this.findAll().stream().filter(r -> r.getStockMin() > r.getStock()).toList();
    }

    public List<RawMaterial> findByName(String name) {
        if (!rawMaterialRepository.existsByName(name)) {
            throw new IllegalArgumentException("Raw material with name " + name + " not found.");
        }
        return rawMaterialRepository.findByName(name);
    }
}
