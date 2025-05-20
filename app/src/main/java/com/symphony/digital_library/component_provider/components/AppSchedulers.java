package com.symphony.digital_library.component_provider.components;

import androidx.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * Інтерфейс для визначення планувальників (Schedulers), які використовуються для роботи
 * з асинхронними потоками при виконанні операцій.
 */
public interface AppSchedulers {

    /**
     * Планувальник для виконання операцій на фонових потоках вводу/виводу (I/O).
     *
     * @return {@link Scheduler} для операцій вводу/виводу.
     */
    @NonNull
    Scheduler io();

    /**
     * Планувальник для виконання операцій на головному потоці (інтерфейсу користувача).
     *
     * @return {@link Scheduler} для роботи з інтерфейсом користувача (UI).
     */
    @NonNull
    Scheduler ui();

    /**
     * Планувальник для виконання операцій, пов'язаних із роботою бази даних.
     *
     * @return {@link Scheduler} для операцій з базою даних.
     */
    @NonNull
    Scheduler db();
}

