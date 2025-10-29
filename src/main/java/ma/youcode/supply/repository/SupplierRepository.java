package ma.youcode.supply.repository;

import ma.youcode.supply.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findByName(String name);
}
