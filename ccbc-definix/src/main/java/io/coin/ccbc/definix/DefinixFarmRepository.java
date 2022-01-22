package io.coin.ccbc.definix;

import io.coin.ccbc.domain.Address;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefinixFarmRepository extends JpaRepository<DefinixFarm, String> {

  Optional<DefinixFarm> findByAddress(Address address);

  default DefinixFarm findByAddressOrElseThrow(Address address) {

    return this.findByAddress(address).orElseThrow(() -> new IllegalArgumentException(
            String.format("farm with address '%s' does not exist", address.getValue())
        )
    );
  }

  default DefinixFarm findByIdOrElseThrow(String id) {
    return this.findById(id)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("farm with id '%s' does not exist", id)
        ));
  }

  default Map<Address, DefinixFarm> getAllFarmMap() {
    return findAll()
        .stream()
        .collect(Collectors.toMap(DefinixFarm::getAddress,pair->pair));
  }
}
