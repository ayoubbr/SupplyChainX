package ma.youcode.production.repository;

import ma.youcode.production.model.BillOfMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillOfMaterialRepository extends JpaRepository<BillOfMaterial, Long> {
}
