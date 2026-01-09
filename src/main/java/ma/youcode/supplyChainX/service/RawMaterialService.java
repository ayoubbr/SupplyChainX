package ma.youcode.supplyChainX.service;

import lombok.AllArgsConstructor;
import ma.youcode.supplyChainX.dto.RawMaterialRequest;
import ma.youcode.supplyChainX.dto.RawMaterialResponse;
import ma.youcode.supplyChainX.dto.SupplierResponse;
import ma.youcode.supplyChainX.mapper.RawMaterialMapper;
import ma.youcode.supplyChainX.mapper.SupplierMapper;
import ma.youcode.supplyChainX.model.RawMaterial;
import ma.youcode.supplyChainX.model.Supplier;
import ma.youcode.supplyChainX.repository.RawMaterialRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class RawMaterialService {

    private final SupplierMapper supplierMapper;
    private RawMaterialRepository rawMaterialRepository;
    private RawMaterialMapper rawMaterialMapper;
    private SupplierService supplierService;

    public RawMaterialResponse save(RawMaterialRequest rawMaterialRequest) {
        if (rawMaterialRepository.existsByName(rawMaterialRequest.getName())) {
            throw new IllegalArgumentException("Raw material with name " + rawMaterialRequest.getName() + " already exists.");
        }

        RawMaterial rawMaterial = rawMaterialMapper.toEntity(rawMaterialRequest);

        List<Supplier> supplierList = rawMaterialRequest.getSupplierIds().stream()
                .map(supplierId -> supplierService.findById(supplierId))
                .map(supplierMapper::toEntity)
                .toList();

        rawMaterial.setSuppliers(supplierList);

        return rawMaterialMapper.toResponse(rawMaterialRepository.save(rawMaterial));
    }

    public RawMaterialResponse update(RawMaterialRequest rawMaterialRequest, Long id) {

        RawMaterial existingRawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Raw material with ID " + id + " not found."));

        existingRawMaterial.setName(rawMaterialRequest.getName());
        existingRawMaterial.setStock(rawMaterialRequest.getStock());
        existingRawMaterial.setStockMin(rawMaterialRequest.getMinStock());
        existingRawMaterial.setUnit(rawMaterialRequest.getUnit());

        List<Supplier> supplierList = rawMaterialRequest.getSupplierIds().stream()
                .map(supplierId -> supplierService.findById(supplierId))
                .map(supplierMapper::toEntity)
                .collect(Collectors.toList());

        existingRawMaterial.setSuppliers(supplierList);

        RawMaterial savedRawMaterial = rawMaterialRepository.save(existingRawMaterial);
        return rawMaterialMapper.toResponse(savedRawMaterial);
    }

    public void deleteById(Long id) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Raw material with ID " + id + " not found."));

        if (!rawMaterial.getSupplyOrderRawMaterials().isEmpty()) {
            throw new IllegalArgumentException("Cannot delete raw material with associated supply orders.");
        }

        rawMaterialRepository.delete(rawMaterial);
    }

    public List<RawMaterialResponse> findAll() {
        return rawMaterialRepository.findAll().stream().map(rawMaterial ->
                rawMaterialMapper.toResponse(rawMaterial)).collect(Collectors.toList());
    }

    public RawMaterialResponse findById(Long id) {
        return rawMaterialMapper.toResponse(rawMaterialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Raw material with ID " + id + " not found.")));
    }

    public List<RawMaterialResponse> getRawMaterialsBelowStock() {
        return this.findAll().stream().filter(r -> r.getMinStock() > r.getStock()).toList();
    }

    public List<RawMaterialResponse> findByName(String name) {
        if (!rawMaterialRepository.existsByName(name)) {
            throw new IllegalArgumentException("Raw material with name " + name + " not found.");
        }
        return rawMaterialRepository.findByName(name).stream().map(rawMaterial ->
                rawMaterialMapper.toResponse(rawMaterial)).toList();
    }
}
