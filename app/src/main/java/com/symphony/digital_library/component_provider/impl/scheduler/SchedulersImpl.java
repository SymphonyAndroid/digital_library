package com.symphony.digital_library.component_provider.impl.scheduler;

import androidx.annotation.NonNull;

import com.symphony.digital_library.component_provider.components.AppSchedulers;

import java.util.concurrent.Executors;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Реалізація інтерфейсу {@link AppSchedulers}, яка надає різні RxJava планувальники (Schedulers) для керування потоками.
 * <p>
 * Цей клас забезпечує роботу з потоками для виконання IO-операцій, оновлення UI та операцій із базою даних.
 * </p>
 */
public class SchedulersImpl implements AppSchedulers {

    /**
     * Планувальник для операцій із базою даних. Використовує один потік для виконання.
     */
    private Scheduler scheduler;

    /**
     * Повертає планувальник для виконання IO-операцій.
     * <p>
     * IO-планувальник призначений для операцій введення-виведення, які вимагають неблокуючого виконання, таких як робота з файлами або запити в мережу.
     * </p>
     *
     * @return Планувальник {@link Scheduler}, який працює з IO-операціями.
     */
    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    /**
     * Повертає планувальник для оновлення користувацького інтерфейсу (UI).
     * <p>
     * Цей планувальник забезпечує виконання операцій у головному (UI) потоці програми.
     * </p>
     *
     * @return Планувальник {@link Scheduler}, який працює з основним потоком (UI).
     */
    @NonNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    /**
     * Повертає планувальник для операцій із базою даних.
     * <p>
     * Використовує окремий потік для забезпечення послідовного виконання операцій з базою даних.
     * Якщо планувальник ще не створений, він ініціалізується з потоку, створеного за допомогою {@link Executors#newSingleThreadExecutor()}.
     * </p>
     *
     * @return Планувальник {@link Scheduler}, який працює з операціями бази даних.
     */
    @NonNull
    @Override
    public Scheduler db() {
        if (scheduler == null) scheduler = Schedulers.from(Executors.newSingleThreadExecutor());
        return scheduler;
    }
}