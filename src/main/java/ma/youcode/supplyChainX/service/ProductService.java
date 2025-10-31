package ma.youcode.supplyChainX.service;

import ma.youcode.supplyChainX.dto.ProductRequest;
import ma.youcode.supplyChainX.dto.ProductResponse;
import ma.youcode.supplyChainX.model.BillOfMaterial;
import ma.youcode.supplyChainX.model.Product;
import ma.youcode.supplyChainX.model.RawMaterial;
import ma.youcode.supplyChainX.repository.ProductRepository;
import ma.youcode.supplyChainX.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductService(ProductRepository productRepository,
                          RawMaterialRepository rawMaterialRepository) {
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public ProductResponse save(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setCost(request.getCost());
        product.setStock(request.getStock());
        product.setProductionTime(request.getProductionTime());


        List<BillOfMaterial> materials = request.getBillOfMaterials().stream()
                .map(dto -> {
                    RawMaterial rm = rawMaterialRepository.findById(dto.getRawMaterialId())
                            .orElseThrow(() -> new IllegalArgumentException("Raw material not found: " + dto.getRawMaterialId()));

                    BillOfMaterial bom = new BillOfMaterial();
                    bom.setRawMaterial(rm);
                    bom.setProduct(product);
                    bom.setQuantity(dto.getQuantity());
                    return bom;
                })
                .collect(Collectors.toList());

        product.setBillOfMaterials(materials);
        validateProduct(product);
        Product saved = productRepository.save(product);
        return mapToResponseDTO(saved);
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

    private ProductResponse mapToResponseDTO(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setStock(product.getStock());
        response.setCost(product.getCost());
        response.setProductionTime(product.getProductionTime());

        List<ProductResponse.BillOfMaterialResponseDTO> materials = product.getBillOfMaterials().stream()
                .map(bom -> {
                    ProductResponse.BillOfMaterialResponseDTO dto = new ProductResponse.BillOfMaterialResponseDTO();
                    dto.setId(bom.getId());
                    dto.setRawMaterialId(bom.getRawMaterial().getId());
                    dto.setRawMaterialName(bom.getRawMaterial().getName());
                    dto.setQuantity(bom.getQuantity());
                    return dto;
                })
                .collect(Collectors.toList());

        response.setBillOfMaterials(materials);
        return response;
    }
}
