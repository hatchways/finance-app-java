package com.hatchways.finance.serialization;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer
    implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

  @Override
  public JsonElement serialize(
      LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
  }

  @Override
  public LocalDateTime deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    String ldtString = jsonElement.getAsString();
    return LocalDateTime.parse(ldtString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }
}
