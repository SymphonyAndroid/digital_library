package com.symphony.digital_library.ui.base.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewbinding.ViewBinding;

import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.function.NotNullConsumer;

/**
 * Абстрактний клас {@code BaseActivity} забезпечує базовий функціонал для Activity із застосуванням ViewBinding.
 * <p>
 * Основні можливості:
 * <ul>
 *     <li>Інтеграція ViewBinding для автоматичного зв’язування макета.</li>
 *     <li>Автоматичний облік відступів для системних панелей за допомогою {@code EdgeToEdge}.</li>
 *     <li>Зручне управління об'єктом ViewBinding.</li>
 * </ul>
 * </p>
 *
 * @param <VB> Тип, що розширює {@link ViewBinding}, і використовується для зв'язування макета.
 */
public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    private VB binding;

    /**
     * Абстрактний метод для надання інфлятора, який відповідає за створення ViewBinding.
     * Цей метод має бути реалізований у дочірніх класах.
     *
     * @return Об'єкт {@link ViewBindingUtil.Inflater}, який використовується для ініціалізації ViewBinding.
     */
    @NonNull
    protected abstract ViewBindingUtil.Inflater<VB> inflater();

    /**
     * Повертає поточно створену ViewBinding для Activity.
     *
     * @return Об'єкт ViewBinding для роботи з елементами макета.
     */
    @NonNull
    public VB getBinding() {
        return binding;
    }

    /**
     * Виконує дію з використанням ViewBinding, якщо вона ініціалізована.
     *
     * @param action Дія, яку слід виконати, надаючи ViewBinding як параметр.
     */
    protected void withBinding(@NonNull NotNullConsumer<VB> action) {
        if (binding != null) action.accept(binding);
    }

    /**
     * Метод життєвого циклу {@code onCreate}, відповідальний за:
     * <ul>
     *     <li>Ініціалізацію ViewBinding за допомогою наданого інфлятора.</li>
     *     <li>Налаштування вмісту Activity через {@code setContentView}.</li>
     *     <li>Застосування Edge-To-Edge режиму з коригуванням відступів для системних панелей.</li>
     * </ul>
     *
     * @param savedInstanceState Збережений стан Activity, якщо він доступний (може бути {@code null}).
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Увімкнення режиму Edge-To-Edge
        EdgeToEdge.enable(this);
        // Ініціалізація ViewBinding
        binding = inflater().inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Встановлення відступів для системних панелей
        withBinding(it -> {
            ViewCompat.setOnApplyWindowInsetsListener(it.getRoot(), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        });
    }

    /**
     * Метод життєвого циклу {@code onDestroy}.
     * Звільняє ViewBinding для уникнення можливих утрат пам’яті.
     */
    @Override
    protected void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}