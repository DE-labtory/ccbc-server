package io.coin.ccbc.kokoa;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class DecimalStringToBigIntegerDeserializer extends JsonDeserializer<BigInteger> {

  @Override
  public BigInteger deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    String text = p.getText();
    return new BigDecimal(text).toBigInteger();
  }
}
