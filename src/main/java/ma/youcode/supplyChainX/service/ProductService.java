package ma.youcode.supplyChainX.service;

import ma.youcode.supplyChainX.model.Product;
import ma.youcode.supplyChainX.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        validateProduct(product);

        return productRepository.save(product);
    }

    public Product update(Product product, Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResolutionException("Product not found"));

        validateProduct(product);

        existingProduct.setName(product.getName());
        existingProduct.setCost(product.getCost());
        existingProduct.setStock(product.getStock());
        existingProduct.setProductionTime(product.getProductionTime());

        return productRepository.save(existingProduct);
    }

    public int delete(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            if (!product.get().getOrders().isEmpty() || !product.get().getProductionOrders().isEmpty() || !product.get().getBillOfMaterials().isEmpty()) {
                throw new IllegalStateException("Cannot delete product with existing associations");
            }
        } else {
            throw new ResolutionException("Product not found");
        }

        productRepository.deleteById(id);
        return 1;
    }

    private void validateProduct(Product product) {
        if (product.getCost() < 0) {
            throw new IllegalArgumentException("Product cost cannot be negative");
        }
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative");
        }
        if (product.getProductionTime() < 0) {
            throw new IllegalArgumentException("Product production time cannot be negative");
        }
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findByName(String name) {
        if (!productRepository.existsByName(name)) {
            throw new ResolutionException("Product not found");
        }

        return productRepository.findByName(name);
    }

}
