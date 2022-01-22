package io.coin.ccbc.domain;

import io.coin.ccbc.support.Converter;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Embeddable
public class Address {

  @Column
  private String value;

  public static Address empty() {
    return new Address("");
  }

  public static Address of(String address) {
    if (address == null) {
      throw new IllegalArgumentException("address is null");
    }

    String checksumAddress = Converter.makeChecksumAddress(address);
    // 0x + 40 자리 길이여야함
    // todo : input address 가 hex string 이였는지 검사해야함
    if (checksumAddress.length() != 42) {
      throw new IllegalArgumentException(
          String.format("input '%s' is not address format. invalid length", address)
      );
    }
    return new Address(checksumAddress);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (o.getClass() == String.class) {
      return this.equals((String) o);
    }
    if (this.getClass() != o.getClass()) {
      return false;
    }
    Address address = (Address) o;
    return this.equals(address);
  }

  public boolean equals(Address address) {
    return Objects.equals(this.value.toLowerCase(), address.value.toLowerCase());
  }

  public boolean equals(String address) {
    try {
      return this.equals(new Address(address));
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.value);
  }

  @Override
  public String toString() {
    return this.value;
  }
}
