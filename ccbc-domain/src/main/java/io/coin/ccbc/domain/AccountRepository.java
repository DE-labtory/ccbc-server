package io.coin.ccbc.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

  Boolean existsByAddress(Address address);

  default void existsByAddressOrElseThrow(Address address) {
    if (!this.existsByAddress(address)) {
      throw new IllegalArgumentException(
          String.format("address '%s' does not registered", address));
    }
  }

  Optional<Account> findByAddress(Address address);

  default Account findByAddressOrElseThrow(Address address) {
    return this.findByAddress(address)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("address '%s' does not registered", address))
        );
  }
}
