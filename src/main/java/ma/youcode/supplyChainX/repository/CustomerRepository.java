package ma.youcode.supplyChainX.repository;

import ma.youcode.supplyChainX.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByName(String name);
}
