package com.symphony.digital_library.ui.base.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.function.NotNullConsumer;

/**
 * Абстрактний клас {@code BaseBottomSheetDialog}, що забезпечує базовий функціонал для BottomSheetDialog із підтримкою ViewBinding.
 *
 * @param <VB> Тип, що розширює {@link ViewBinding}, для прив'язки макета до діалогу.
 */
public abstract class BaseBottomSheetDialog<VB extends ViewBinding> extends BottomSheetDialog {

    /**
     * Конструктор {@code BaseBottomSheetDialog}.
     *
     * @param context Контекст, у якому створюється діалог.
     * @param theme   Стиль діалогу (ресурс теми).
     */
    public BaseBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    /**
     * Об'єкт ViewBinding, використовується для доступу до елементів макета.
     */
    private VB binding;

    /**
     * Ідентифікатор кореневого елемента ViewBinding, використовується для його збереження і відновлення.
     */
    private String viewBindingRootId;

    /**
     * Абстрактний метод, який має надавати Inflater для створення ViewBinding.
     * <p>
     * Реалізується у дочірніх класах.
     * </p>
     *
     * @return Інстанс {@link ViewBindingUtil.Inflater}, що відповідає за створення ViewBinding.
     */
    @NonNull
    protected abstract ViewBindingUtil.Inflater<VB> inflater();

    /**
     * Абстрактний метод, який має надавати Bind-функцію для підключення ViewBinding до кореневого View.
     * <p>
     * Реалізується у дочірніх класах.
     * </p>
     *
     * @return Інстанс {@link ViewBindingUtil.Bind}, використовуваний для прив'язки ViewBinding.
     */
    @NonNull
    protected abstract ViewBindingUtil.Bind<VB> bind();

    /**
     * Зручний метод для виконання дії з ViewBinding, якщо він ініціалізований.
     *
     * @param action Дія, яка буде виконана з ViewBinding.
     */
    protected void withBinding(@NonNull NotNullConsumer<VB> action) {
        if (binding != null) action.accept(binding);
    }

    /**
     * Методи життєвого циклу {@code onCreate}, який:
     * <ul>
     *     <li>Ініціалізує ViewBinding за допомогою {@code inflater}.</li>
     *     <li>Зберігає ідентифікатор кореневого елемента ViewBinding.</li>
     *     <li>Встановлює вміст діалогу за допомогою кореневого ViewBinding.</li>
     * </ul>
     *
     * @param savedInstanceState Збережений стан для відновлення (може бути {@code null}).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflater().inflate(getLayoutInflater());
        viewBindingRootId = ViewBindingUtil.saveViewBindingId(binding);
        setContentView(binding.getRoot());
    }

    /**
     * Показує діалог і відновлює ViewBinding, якщо він ще не ініціалізований.
     * <p>
     * Викликає {@link #onShow(ViewBinding)} для виконання дій під час відображення діалогу.
     * </p>
     */
    @Override
    public void show() {
        super.show();

        Window window = getWindow();
        if (binding == null && window != null) {
            // Віднаходить кореневий ViewBinding за збереженим ідентифікатором.
            View root = ViewBindingUtil.findViewBindingRoot(window.getDecorView(), viewBindingRootId);
            if (root != null) binding = bind().bind(root);
        }

        // Виклик onShow
        if (binding != null) onShow(binding);
    }

    /**
     * Викликається, коли BottomSheetDialog відображається.
     * Може бути перевизначений у дочірніх класах для додавання логіки при відображенні діалогу.
     *
     * @param binding Ініціалізований об'єкт {@link ViewBinding}.
     */
    protected void onShow(@NonNull VB binding) {

    }

    /**
     * Закриває діалог і очищує ViewBinding для уникнення потенційних втрат пам'яті.
     */
    @Override
    public void dismiss() {
        binding = null;
        super.dismiss();
    }
}