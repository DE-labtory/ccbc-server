package io.coin.ccbc.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account extends DomainEntity {

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "address", nullable = false))
  private Address address;

  public static Account from(Address address) {
    return new Account(address);
  }

}
