package io.coin.ccbc.support;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

  @Override
  public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeString(String.valueOf(value.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli()));
  }
}
