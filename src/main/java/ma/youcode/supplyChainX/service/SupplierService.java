package ma.youcode.supplyChainX.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ma.youcode.supplyChainX.dto.SupplierRequest;
import ma.youcode.supplyChainX.dto.SupplierResponse;
import ma.youcode.supplyChainX.mapper.SupplierMapper;
import ma.youcode.supplyChainX.model.Supplier;
import ma.youcode.supplyChainX.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;


    public SupplierResponse save(SupplierRequest supplierRequest) {
        if (supplierRepository.existsByName(supplierRequest.getName())) {
            throw new IllegalArgumentException("Supplier with name " + supplierRequest.getName() + " already exists.");
        }
        if (supplierRepository.existsByContact(supplierRequest.getContact())) {
            throw new IllegalArgumentException("Supplier with contact " + supplierRequest.getContact() + " already exists.");
        }

        Supplier supplier = supplierMapper.toEntity(supplierRequest);

        return supplierMapper.toResponse(supplierRepository.save(supplier));
    }

    public SupplierResponse update(SupplierRequest supplierRequest, Long id) {

        Supplier existingSupplier = supplierRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Supplier with ID " + id + " not found."));

        if (supplierRepository.existsByName(supplierRequest.getName())) {
            Supplier supplierByName = supplierRepository.findByName(supplierRequest.getName());
            if (!Objects.equals(supplierByName.getId(), existingSupplier.getId())) {
                throw new IllegalArgumentException("Supplier with name " + supplierRequest.getName() + " already exists.");
            }
        }

        existingSupplier.setName(supplierRequest.getName());
        existingSupplier.setContact(supplierRequest.getContact());
        existingSupplier.setRating(supplierRequest.getRating());
        existingSupplier.setLeadTime(supplierRequest.getLeadTime());

        return supplierMapper.toResponse(supplierRepository.save(existingSupplier));
    }

    public List<SupplierResponse> findAll() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers.stream().map(supplierMapper::toResponse).collect(Collectors.toList());
    }

    public SupplierResponse deleteById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplier with ID " + id + " not found."));

        if (supplier.getSupplyOrders() != null && !supplier.getSupplyOrders().isEmpty()) {
            throw new IllegalStateException("Cannot delete supplier with existing supply orders.");
        }

        supplierRepository.deleteById(id);
        return supplierMapper.toResponse(supplier);
    }

    public SupplierResponse findById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplier with ID " + id + " not found."));

        return supplierMapper.toResponse(supplier);
    }

    public SupplierResponse findByName(String name) {
        if (!supplierRepository.existsByName(name)) {
            throw new IllegalArgumentException("Supplier with name " + name + " does not exist.");
        }
        Supplier byName = supplierRepository.findByName(name);
        return supplierMapper.toResponse(byName);
    }
}
