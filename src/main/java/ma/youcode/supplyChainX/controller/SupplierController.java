package ma.youcode.supplyChainX.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import ma.youcode.supplyChainX.dto.SupplierRequest;
import ma.youcode.supplyChainX.dto.SupplierResponse;
import ma.youcode.supplyChainX.model.Supplier;
import ma.youcode.supplyChainX.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Supplier Controller", description = "CRUD operations for managing suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponse> getSupplierById(@PathVariable Long id) {
        return  ResponseEntity.ok(supplierService.findById(id));
    }

    @GetMapping()
    public List<SupplierResponse> getAllSuppliers() {
        return supplierService.findAll();
    }

    @GetMapping("/name/{name}")
    public SupplierResponse getByName(@PathVariable String name) {
        return supplierService.findByName(name);
    }

    @PostMapping()
    public SupplierResponse createSupplier(@RequestBody SupplierRequest supplier) {
        return supplierService.save(supplier);
    }

    @PutMapping("/{id}")
    public SupplierResponse updateSupplier(@RequestBody SupplierRequest supplier, @PathVariable Long id) {
        return supplierService.update(supplier, id);
    }

    @DeleteMapping("/{id}")
    public SupplierResponse deleteSupplier(@PathVariable Long id) {
        return supplierService.deleteById(id);
    }
}
