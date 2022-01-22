package io.coin.ccbc.api.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.coin.ccbc.domain.Amount;
import java.io.IOException;

public class AmountToStringSerializer extends JsonSerializer<Amount> {

  @Override
  public void serialize(Amount value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeString(value.getValue().toString());
  }
}
