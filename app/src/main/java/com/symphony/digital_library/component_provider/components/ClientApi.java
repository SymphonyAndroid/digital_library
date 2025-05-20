package com.symphony.digital_library.component_provider.components;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Клас для створення та надання реалізації API-клієнта за допомогою бібліотеки Retrofit.
 * Забезпечує підключення до REST API з використанням RxJava та конвертора Gson.
 */
public class ClientApi {

    /**
     * Статичний екземпляр API-клієнта.
     */
    private static Api clientApi;

    /**
     * Метод для отримання екземпляра API-клієнта.
     * Використовує шаблон Singleton для створення єдиного екземпляра Retrofit на весь час роботи застосунку.
     *
     * @return {@link Api} — екземпляр клієнта для взаємодії з REST API.
     */
    public static Api getApi() {
        if (clientApi == null) {
            clientApi = new Retrofit.Builder()
                    .baseUrl("url") // URL базового API, який повинен бути замінений на фактичний.
                    .client(provideHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(Api.class);
        }
        return clientApi;
    }

    /**
     * Метод для створення та конфігурації HTTP-клієнта.
     * Додає інтерцептор для реєстрації запитів та відповідей у логах у режимі DEBUG.
     *
     * @return {@link OkHttpClient} — налаштований HTTP-клієнт.
     */
    private static OkHttpClient provideHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }
}