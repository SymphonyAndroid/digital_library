package com.symphony.digital_library.ui.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.function.NotNullConsumer;

/**
 * Абстрактний клас {@code BaseFragment}, який виступає як база для всіх Fragment із підтримкою ViewBinding.
 *
 * @param <VB> Тип, що розширює {@link ViewBinding}, і використовується для прив'язки макета до Fragment.
 */
public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {

    /**
     * Об'єкт ViewBinding, прив'язаний до цього Fragment.
     */
    private VB binding;

    /**
     * Абстрактний метод для надання Inflater, який відповідає за створення ViewBinding.
     * <p>
     * Реалізується в дочірніх класах, щоб визначити, як створюється ViewBinding для макета.
     * </p>
     *
     * @return Інстанс {@link ViewBindingUtil.InflaterParent} для створення ViewBinding.
     */
    @NonNull
    protected abstract ViewBindingUtil.InflaterParent<VB> inflater();

    /**
     * Повертає ViewBinding, прив'язаний до цього Fragment.
     * <p>
     * Корисно для доступу до елементів макета, прив'язаних до ViewBinding.
     * </p>
     *
     * @return Ініціалізований об'єкт {@link ViewBinding}.
     * @throws NullPointerException Якщо спроба доступу до цього методу здійснюється до створення View.
     */
    @NonNull
    public VB getBinding() {
        return binding;
    }

    /**
     * Зручний метод для виконання дій із ViewBinding лише тоді, коли він ініціалізований.
     * <p>
     * Запобігає проблемам, пов'язаним із доступом до ViewBinding після знищення View.
     * </p>
     *
     * @param action Дія, яка буде виконана з {@link ViewBinding}.
     */
    protected void withBinding(@NonNull NotNullConsumer<VB> action) {
        if (binding != null) action.accept(binding);
    }

    /**
     * Викликається для створення View для Fragment, використовуючи ViewBinding.
     *
     * @param inflater  Об'єкт {@link LayoutInflater}, який використовується для "надування" макета.
     * @param container Батьківська ViewGroup, до якої додається макет (може бути {@code null}).
     * @param savedInstanceState Стан, збережений під час попереднього створення (може бути {@code null}).
     * @return Об'єкт View, створений за допомогою {@link ViewBinding}.
     */
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        // Ініціалізація ViewBinding
        binding = inflater().inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Викликається при знищенні View Fragment.
     * <p>
     * Очищає об'єкт ViewBinding, щоб уникнути витоків пам'яті.
     * </p>
     */
    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}