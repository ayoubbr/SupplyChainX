package ma.youcode.delivery.repository;

import ma.youcode.delivery.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
