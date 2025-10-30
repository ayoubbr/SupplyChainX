package ma.youcode.supply.service;

import ma.youcode.supply.model.Supplier;
import ma.youcode.supply.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier save(Supplier supplier) {
        if (supplierRepository.existsById(supplier.getId())) {
            throw new IllegalArgumentException("Supplier with ID " + supplier.getId() + " already exists.");
        }
        if (supplierRepository.existsByName(supplier.getName())) {
            throw new IllegalArgumentException("Supplier with name " + supplier.getName() + " already exists.");
        }
        if (supplierRepository.existsByContact(supplier.getContact())) {
            throw new IllegalArgumentException("Supplier with contact " + supplier.getContact() + " already exists.");
        }

        return supplierRepository.save(supplier);
    }

    public Supplier update(Supplier supplier, Long id) {
        if (!supplierRepository.existsById(supplier.getId())) {
            throw new IllegalArgumentException("Supplier with ID " + supplier.getId() + " does not exist.");
        }
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplier with ID " + id + " not found."));

        if (!Objects.equals(supplier.getId(), existingSupplier.getId())) {
            if (supplierRepository.existsByName(supplier.getName())) {
                throw new IllegalArgumentException("Supplier with name " + supplier.getName() + " already exists.");
            }
            if (supplierRepository.existsByContact(supplier.getContact())) {
                throw new IllegalArgumentException("Supplier with contact " + supplier.getContact() + " already exists.");
            }
        }

        return supplierRepository.save(supplier);
    }

    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public Supplier deleteById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplier with ID " + id + " not found."));

        if (supplier.getSupplyOrders() != null && !supplier.getSupplyOrders().isEmpty()) {
            throw new IllegalStateException("Cannot delete supplier with existing supply orders.");
        }

        supplierRepository.deleteById(id);
        return supplier;
    }

    public Supplier findById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplier with ID " + id + " not found."));
    }

    public Supplier findByName(String name) {
        if (!supplierRepository.existsByName(name)) {
            throw new IllegalArgumentException("Supplier with name " + name + " does not exist.");
        }
        return supplierRepository.findByName(name);
    }
}
