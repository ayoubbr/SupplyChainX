package ma.youcode.supply.repository;

import ma.youcode.supply.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findByName(String name);

    boolean existsByName(String name);

    boolean existsByContact(String contact);
}
