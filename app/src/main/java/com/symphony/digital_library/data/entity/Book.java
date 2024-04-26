package com.symphony.digital_library.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Junction;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.lang.reflect.Type;

@Entity
@JsonAdapter(Book.JsonAdapter.class)
public class Book implements Serializable {

    @NonNull
    @PrimaryKey
    private final String id;
    @NonNull private final String title;
    @NonNull private final String author;
    private final int year;

    public Book(@NonNull String title, @NonNull String author, int year, @NonNull String id) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.id = id;
    }

    @Ignore
    public Book(@NonNull String title, @NonNull String author, int year) {
        this(title, author, year, title + "/" + author);
    }

    @NonNull public String getId() {
        return id;
    }

    @NonNull public String getTitle() {
        return title;
    }

    @NonNull public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public static class JsonAdapter implements JsonDeserializer<Book> {

        @Override
        public Book deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json == null || !json.isJsonObject()) return null;
            JsonObject object = json.getAsJsonObject();
            return new Book(
                    getString(object, "title"),
                    getString(object, "author"),
                    getYear(object)
            );
        }

        private String getString(JsonObject jsonObject, String key) {
            JsonElement element = jsonObject.get(key);
            if (element.isJsonNull() || !element.isJsonPrimitive()) return "";
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            return primitive.isString() ? primitive.getAsString() : "";
        }

        private int getYear(JsonObject jsonObject) {
            JsonElement element = jsonObject.get("year");
            if (element.isJsonNull() || !element.isJsonPrimitive()) return -1;
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            return primitive.isNumber() ? primitive.getAsInt() : -1;
        }
    }

}
