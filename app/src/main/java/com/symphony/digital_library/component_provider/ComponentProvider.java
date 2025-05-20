package com.symphony.digital_library.component_provider;

import androidx.annotation.NonNull;

import com.symphony.digital_library.component_provider.components.*;
import com.symphony.digital_library.data.database.dao.BaseDao;

import java.util.function.Function;

/**
 * Інтерфейс, який надає основні компоненти та сервіси додатку.
 * Він виступає центральним вузлом для отримання екземплярів планувальників,
 * юзкейсів та інших повторно використовуваних компонентів в додатку.
 */
public interface ComponentProvider {

    /**
     * Повертає планувальники, які використовуються для управління потоками
     * у фонових операціях та операціях головного потоку.
     *
     * @return Екземпляр {@link AppSchedulers}.
     */
    @NonNull
    AppSchedulers getSchedulers();

    /**
     * Повертає набір юзкейсів, які доступні в додатку.
     *
     * @return Екземпляр {@link UseCases}, що містить основну бізнес-логіку додатку.
     */
    @NonNull
    UseCases getUseCases();

    /**
     * Повертає екземпляр {@link CacheWithAction}, що комбінує кешування даних з бази даних
     * і дії, які застосовуються до цих даних.
     *
     * @param <D> Тип DAO (об'єкт доступу до даних), який розширює {@link BaseDao}.
     * @param <R> Тип ресурсу, який керується DAO.
     * @param function Функція, що приймає екземпляр {@link Database} та повертає DAO типу {@code D}.
     * @return Екземпляр {@link CacheWithAction} для управління даними та відповідними діями.
     */
    @NonNull
    <D extends BaseDao<R>, R> CacheWithAction<D, R> getCacheWithAction(Function<Database, D> function);

    /**
     * Допоміжний клас для управління синглетон-екземпляром {@link ComponentProvider}.
     */
    class Companion {

        private static ComponentProvider instance;

        /**
         * Повертає синглетон-екземпляр {@link ComponentProvider}.
         *
         * @return Синглетон {@link ComponentProvider}.
         * @throws NullPointerException Якщо екземпляр не було ініціалізовано через метод {@link #inject(ComponentProvider)}.
         */
        @NonNull
        public static ComponentProvider getInstance() {
            return instance;
        }

        /**
         * Ініціалізує глобальний синглетон-екземпляр {@link ComponentProvider}.
         *
         * @param instance Екземпляр, який буде встановлено як глобальний {@link ComponentProvider}.
         */
        public static void inject(@NonNull ComponentProvider instance) {
            Companion.instance = instance;
        }
    }
}