package com.strathmore.projectds.converters;

import com.google.gson.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;

public class DateTimeConverter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
    public static final DateTimeZone ZONE = DateTimeZone.getDefault();
    public static final DateTimeFormatter FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss Z");

    @Override
    public DateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement == null || jsonElement.toString() == null || jsonElement.toString().isBlank()) return null;
        return DateTime.parse(jsonElement.getAsString(), FORMAT).withZoneRetainFields(ZONE);
    }

    @Override
    public JsonElement serialize(DateTime dateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        if (dateTime == null) return null;
        return new JsonPrimitive(dateTime.withZoneRetainFields(ZONE).toString(FORMAT));
    }
}
