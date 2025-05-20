package com.symphony.digital_library.ui.base.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

/**
 * Універсальний ViewHolder для роботи з RecyclerView, який використовує ViewBinding.
 *
 * @param <VB> Тип, що розширює {@link ViewBinding}, для прив'язки макета до ViewHolder.
 */
public class ViewHolder<VB extends ViewBinding> extends RecyclerView.ViewHolder {

    /**
     * Об'єкт ViewBinding, який відповідає за зв'язування макета з ViewHolder.
     */
    @NonNull private final VB binding;

    /**
     * Конструктор {@code ViewHolder}, який приймає ViewBinding для створення ViewHolder.
     *
     * @param binding Об'єкт {@link ViewBinding}, який зв'язує елементи макета з ViewHolder.
     */
    public ViewHolder(@NonNull VB binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Повертає ViewBinding, прив'язаний до цього ViewHolder.
     *
     * @return Об'єкт {@link ViewBinding}, пов'язаний з макетом у цьому ViewHolder.
     */
    @NonNull
    public VB getBinding() {
        return binding;
    }

    /**
     * Повертає {@link Context} із кореневого View, прив'язаного до ViewBinding.
     * <p>
     * Зручно для доступу до ресурсів, параметрів теми або інших компонентів, пов'язаних із контекстом.
     * </p>
     *
     * @return {@link Context}, асоційований із кореневим View.
     */
    @NonNull
    public Context getContext() {
        return binding.getRoot().getContext();
    }
}