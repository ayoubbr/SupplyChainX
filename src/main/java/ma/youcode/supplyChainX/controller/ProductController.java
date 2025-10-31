package ma.youcode.supplyChainX.controller;

import lombok.Getter;
import ma.youcode.supplyChainX.model.Product;
import ma.youcode.supplyChainX.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{name}")
    public Product getProductByName(@PathVariable String name) {
        return productService.findByName(name);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.update(product, id);
    }

    @DeleteMapping("/{id}")
    public int deleteProduct(@PathVariable Long id) {
        return productService.delete(id);
    }

}
