package com.symphony.digital_library.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.JsonAdapter;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Клас {@code Book} представляє сутність книги, яка зберігається у базі даних.
 * <p>
 * Цей клас має поля, які описують основні властивості книги: ідентифікатор, назву, автора
 * та рік видання. Він збережений як таблиця в базі даних завдяки аннотації {@link Entity}.
 * Також підтримує серіалізацію та десеріалізацію JSON через спеціальний адаптер {@link JsonAdapter}.
 * </p>
 */
@Entity
@JsonAdapter(Book.JsonAdapter.class)
public class Book implements Serializable {

    /**
     * Унікальний ідентифікатор книги (первинний ключ у базі даних).
     */
    @NonNull
    @PrimaryKey
    private final String id;

    /**
     * Назва книги.
     */
    @NonNull
    private final String title;

    /**
     * Автор книги.
     */
    @NonNull
    private final String author;

    /**
     * Рік видання книги.
     */
    private final int year;

    /**
     * Основний конструктор для створення об'єкта книги з усіма властивостями.
     *
     * @param title  Назва книги.
     * @param author Автор книги.
     * @param year   Рік видання книги.
     * @param id     Унікальний ідентифікатор книги.
     */
    public Book(@NonNull String title, @NonNull String author, int year, @NonNull String id) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.id = id;
    }

    /**
     * Альтернативний конструктор для створення книги без явного задання ідентифікатора.
     * <p>
     * Ідентифікатор формується автоматично як комбінація назви книги та автора.
     * </p>
     *
     * @param title  Назва книги.
     * @param author Автор книги.
     * @param year   Рік видання книги.
     */
    @Ignore
    public Book(@NonNull String title, @NonNull String author, int year) {
        this(title, author, year, title + "/" + author);
    }

    /**
     * Повертає унікальний ідентифікатор книги.
     *
     * @return Ідентифікатор книги.
     */
    @NonNull
    public String getId() {
        return id;
    }

    /**
     * Повертає назву книги.
     *
     * @return Назва книги.
     */
    @NonNull
    public String getTitle() {
        return title;
    }

    /**
     * Повертає автора книги.
     *
     * @return Автор книги.
     */
    @NonNull
    public String getAuthor() {
        return author;
    }

    /**
     * Повертає рік видання книги.
     *
     * @return Рік видання книги (якщо невідомо, повертає -1).
     */
    public int getYear() {
        return year;
    }

    /**
     * Вкладений клас для реалізації десеріалізації JSON у об'єкт {@link Book}.
     * <p>
     * Адаптер забезпечує перетворення JSON-даних у об'єкт книги, включаючи перевірку
     * обов'язкових полів, таких як назва, автор та рік видання.
     * </p>
     */
    public static class JsonAdapter implements JsonDeserializer<Book> {

        /**
         * Виконує десеріалізацію JSON-об'єкта в об'єкт {@link Book}.
         *
         * @param json    Вхідний JSON-елемент.
         * @param typeOfT Тип об'єкта, у який потрібно виконати десеріалізацію.
         * @param context Контекст десеріалізації.
         * @return Об'єкт {@link Book}, створений на основі даних із JSON, або {@code null}, якщо JSON недійсний.
         * @throws JsonParseException Якщо JSON-формат некоректний.
         */
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

        /**
         * Повертає рядкове значення із JSON-об'єкта для заданого ключа.
         *
         * @param jsonObject Об'єкт JSON.
         * @param key        Ключ поля, яке необхідно отримати.
         * @return Рядкове значення поля або порожній рядок, якщо ключа не існує або тип значення некоректний.
         */
        private String getString(JsonObject jsonObject, String key) {
            JsonElement element = jsonObject.get(key);
            if (element.isJsonNull() || !element.isJsonPrimitive()) return "";
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            return primitive.isString() ? primitive.getAsString() : "";
        }

        /**
         * Повертає цілочислове значення із JSON-об'єкта для ключа "year".
         *
         * @param jsonObject Об'єкт JSON.
         * @return Цілочислове значення року або -1, якщо ключа не існує або тип значення некоректний.
         */
        private int getYear(JsonObject jsonObject) {
            JsonElement element = jsonObject.get("year");
            if (element.isJsonNull() || !element.isJsonPrimitive()) return -1;
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            return primitive.isNumber() ? primitive.getAsInt() : -1;
        }
    }
}