package ma.youcode.supplyChainX.repository;

import ma.youcode.supplyChainX.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
