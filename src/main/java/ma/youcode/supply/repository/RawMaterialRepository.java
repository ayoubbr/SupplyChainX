package ma.youcode.supply.repository;

import ma.youcode.supply.model.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {
    List<RawMaterial> findByStockLessThan(int minStock);
}
