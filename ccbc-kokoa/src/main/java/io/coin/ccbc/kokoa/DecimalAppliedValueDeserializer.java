package io.coin.ccbc.kokoa;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class DecimalAppliedValueDeserializer extends JsonDeserializer<Double> {

  @Override
  public Double deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    String text = p.getText();
    return Double.parseDouble(text) / Math.pow(10, 18);
  }
}
