package ma.youcode.supplyChainX.controller;

import ma.youcode.supplyChainX.dto.RawMaterialRequest;
import ma.youcode.supplyChainX.dto.RawMaterialResponse;
import ma.youcode.supplyChainX.model.RawMaterial;
import ma.youcode.supplyChainX.service.RawMaterialService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/raw-materials")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @GetMapping
    public List<RawMaterialResponse> getRawMaterials() {
        return rawMaterialService.findAll();
    }

    @GetMapping("/{id}")
    public RawMaterialResponse getRawMaterialById(@PathVariable Long id) {
        return rawMaterialService.findById(id);
    }

    @PostMapping
    public RawMaterialResponse createRawMaterial(@RequestBody RawMaterialRequest rawMaterial) {
        return rawMaterialService.save(rawMaterial);
    }

    @PutMapping("/{id}")
    public RawMaterialResponse updateRawMaterial(@RequestBody RawMaterialRequest rawMaterial, @PathVariable Long id) {
        return rawMaterialService.update(rawMaterial, id);
    }

    @DeleteMapping("/{id}")
    public void deleteRawMaterial(@PathVariable Long id) {
        rawMaterialService.deleteById(id);
    }

    @GetMapping("/below-stock")
    public List<RawMaterialResponse> getRawMaterialsBelowStock() {
        return rawMaterialService.getRawMaterialsBelowStock();
    }

    @GetMapping("/name/{name}")
    public List<RawMaterialResponse> getRawMaterialByName(@PathVariable String name) {
        return rawMaterialService.findByName(name);
    }

}
