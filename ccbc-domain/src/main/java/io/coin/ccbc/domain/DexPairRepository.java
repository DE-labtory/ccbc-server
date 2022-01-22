package io.coin.ccbc.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DexPairRepository extends JpaRepository<DexPair, String> {

  Optional<DexPair> findByAddress(Address address);
}
