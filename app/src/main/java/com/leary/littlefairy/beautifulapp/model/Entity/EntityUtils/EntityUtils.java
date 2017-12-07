package com.leary.littlefairy.beautifulapp.model.Entity.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.DateTime;

import java.lang.reflect.Type;

public final class EntityUtils {

    private EntityUtils() {
    }

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter())
            .create();

    private static class DateTimeTypeAdapter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

        @Override
        public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }

        @Override
        public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new DateTime(json.getAsString());
        }

    }

    //将Json数据解析成相应的映射对象
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

}
