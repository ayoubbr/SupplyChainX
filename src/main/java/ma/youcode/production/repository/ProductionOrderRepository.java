package ma.youcode.production.repository;

import ma.youcode.production.model.ProductionOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, Integer> {
}
