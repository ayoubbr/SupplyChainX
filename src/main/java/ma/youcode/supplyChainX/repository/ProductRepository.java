package ma.youcode.supplyChainX.repository;

import ma.youcode.supplyChainX.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
}
