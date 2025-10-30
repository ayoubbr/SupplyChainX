package ma.youcode.supply.repository;

import ma.youcode.supply.model.SupplyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyOrderRepository extends JpaRepository<SupplyOrder, Long> {
}
