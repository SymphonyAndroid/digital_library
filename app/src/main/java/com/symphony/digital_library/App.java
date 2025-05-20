package com.symphony.digital_library;

import android.app.Application;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.impl.ComponentProviderImpl;

/**
 * Клас {@code App} є основним класом додатку, який наслідує {@link Application}.
 * <p>
 * Використовується для ініціалізації глобальних компонентів додатку при його запуску.
 * </p>
 */
public class App extends Application {

    /**
     * Метод {@code onCreate} викликається при створенні додатку.
     * <p>
     * У цьому методі виконується ініціалізація необхідних компонентів, зокрема,
     * здійснюється ін'єкція реалізації {@link ComponentProvider} у додаток.
     * </p>
     */
    @Override
    public void onCreate() {
        super.onCreate();
        ComponentProvider.Companion.inject(new ComponentProviderImpl(this));
    }
}