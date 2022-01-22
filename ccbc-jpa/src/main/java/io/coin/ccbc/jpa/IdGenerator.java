package io.coin.ccbc.jpa;

import io.coin.ccbc.support.Converter;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class IdGenerator implements IdentifierGenerator {

  private final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

  public IdGenerator() throws NoSuchAlgorithmException {
    super();
    this.keyGenerator.init(128);
  }

  @Override
  public Serializable generate(SharedSessionContractImplementor s, Object obj) {
    Serializable id = s.getEntityPersister(null, obj)
        .getClassMetadata()
        .getIdentifier(obj, s);
    return id == null ? this.generate() : id;
  }

  public String generate() {
    return Converter
        .remove0x(Converter.byteArrayToHexString(this.keyGenerator.generateKey().getEncoded()));
  }

  public String generateShortId() {
    return this.generate().substring(0, 8);
  }
}
