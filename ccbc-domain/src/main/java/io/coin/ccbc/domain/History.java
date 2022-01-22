package io.coin.ccbc.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@MappedSuperclass
@Getter
public abstract class History extends DomainEntity {

  @Column(name = "collected_at", nullable = false)
  private LocalDateTime collectedAt;

  public History(LocalDateTime collectedAt) {
    this.collectedAt = collectedAt;
  }
}
