package io.coin.ccbc.api.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.coin.ccbc.domain.Value;
import java.io.IOException;

public class ValueToDoubleSerializer extends JsonSerializer<Value> {

  @Override
  public void serialize(Value value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeNumber(value.getValue().doubleValue());
  }
}
