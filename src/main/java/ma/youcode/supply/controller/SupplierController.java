package ma.youcode.supply.controller;

import ma.youcode.supply.model.Supplier;
import ma.youcode.supply.service.SupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/{id}")
    public Supplier getSupplierById(@PathVariable Long id) {
        return supplierService.findById(id);
    }

    @GetMapping()
    public List<Supplier> getAllSuppliers() {
        return supplierService.findAll();
    }

    @GetMapping("/name/{name}")
    public Supplier getByName(@PathVariable String name) {
        return supplierService.findByName(name);
    }

    @PostMapping()
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return supplierService.save(supplier);
    }

    @PutMapping("/{id}")
    public Supplier updateSupplier(@RequestBody Supplier supplier, @PathVariable Long id) {
        return supplierService.update(supplier, id);
    }

    @DeleteMapping("/{id}")
    public Supplier deleteSupplier(@PathVariable Long id) {
        return supplierService.deleteById(id);
    }
}
