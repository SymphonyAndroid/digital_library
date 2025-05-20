package com.symphony.digital_library.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.symphony.digital_library.util.function.NotNullConsumer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Утилітний клас {@code ViewUtil}, що містить корисні методи для роботи з {@link View}
 * та його похідними в Android.
 *
 * <p>Цей клас включає методи для отримання всіх елементів у дереві {@link View} та
 * для обробки змін тексту в {@link EditText} через функціональні інтерфейси.</p>
 */
public class ViewUtil {

    /**
     * Збирає всі дочірні елементи (включаючи сам елемент) зі вказаного {@link View}.
     *
     * @param view Кореневий {@link View}, у якому потрібно знайти всі елементи
     * @return Список усіх {@link View} у дереві елементів, включаючи кореневий
     * @throws NullPointerException Якщо {@code view} дорівнює {@code null}.
     */
    public static List<View> allViews(View view) {
        if (view == null) return Collections.emptyList();

        List<View> views = new ArrayList<>();
        views.add(view);

        if (!(view instanceof ViewGroup)) return views;

        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            View v = ((ViewGroup) view).getChildAt(i);
            if (v instanceof ViewGroup) {
                views.addAll(allViews(v));
            } else {
                views.add(v);
            }
        }

        return views;
    }

    /**
     * Додає обробник для зміни тексту в {@link EditText}.
     * <p>
     * Метод приймає функціональний інтерфейс {@link NotNullConsumer}, який викликається після
     * завершення введення тексту. Значення тексту, передане в {@code function},
     * ніколи не буде {@code null}.
     * </p>
     *
     * @param view     Елемент {@link EditText}, до якого додається обробник
     * @param function Функція {@link NotNullConsumer}, що приймає текст у вигляді {@link String}
     * @throws NullPointerException Якщо {@code view} або {@code function} дорівнює {@code null}.
     */
    public static void onTextChanged(EditText view, NotNullConsumer<String> function) {
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                function.accept(s == null ? "" : s.toString());
            }
        });
    }
}