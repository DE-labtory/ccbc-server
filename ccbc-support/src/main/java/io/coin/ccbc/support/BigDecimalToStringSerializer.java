package io.coin.ccbc.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalToStringSerializer extends JsonSerializer<BigDecimal> {

  @Override
  public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeString(value.toPlainString());
  }
}
