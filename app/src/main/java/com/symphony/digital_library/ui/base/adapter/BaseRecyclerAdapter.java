package com.symphony.digital_library.ui.base.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.symphony.digital_library.util.ViewBindingUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Абстрактний клас {@code BaseRecyclerAdapter}, який спрощує використання RecyclerView із ViewBinding.
 * <p>
 * Забезпечує:
 * <ul>
 *     <li>Автоматичну інтеграцію ViewBinding для відображення елементів.</li>
 *     <li>Механізм для визначення типу елемента через адаптери макета.</li>
 * </ul>
 * </p>
 *
 * @param <VB> Тип, що розширює {@link ViewBinding}, використовується для ViewBinding у RecyclerView.
 */
public abstract class BaseRecyclerAdapter<VB extends ViewBinding> extends RecyclerView.Adapter<ViewHolder<VB>> {

    /**
     * Список Inflaters для роботи з різними макетами адаптера.
     * Синхронізується для безпечної багатопотокової роботи.
     */
    private final List<ViewBindingUtil.InflaterParent<VB>> inflaters = Collections.synchronizedList(new ArrayList<>());

    /**
     * Абстрактний метод для надання Inflater, який відповідає за створення ViewBinding для конкретної позиції.
     *
     * @param position Позиція елемента в списку RecyclerView.
     * @return Об'єкт {@link ViewBindingUtil.InflaterParent} для прив'язки макета.
     */
    protected abstract ViewBindingUtil.InflaterParent<VB> inflater(int position);

    /**
     * Абстрактний метод для прив'язки побудованого {@link ViewBinding} до елемента в заданій позиції.
     *
     * @param binding Об'єкт ViewBinding, створений для макета.
     * @param position Позиція елемента в списку RecyclerView.
     */
    protected abstract void onBindView(@NonNull VB binding, int position);

    /**
     * Визначає унікальний тип елемента для кожної позиції в RecyclerView на основі Inflater.
     *
     * @param position Поточна позиція елемента в списку.
     * @return Ідентифікатор типу елемента.
     */
    @Override
    public final int getItemViewType(int position) {
        ViewBindingUtil.InflaterParent<VB> item = inflater(position);
        int index = -1;

        // Перевірка, чи вже використовується Inflater цього типу.
        for (int i = 0; i < inflaters.size(); i++) {
            ViewBindingUtil.InflaterParent<VB> it = inflaters.get(i);
            if (it.getClass() == item.getClass()) {
                index = i;
            }
        }

        // Якщо Inflater вже існує, повертає його індекс, інакше додає новий.
        if (index >= 0) {
            return index;
        }

        inflaters.add(item);
        return inflaters.size() - 1;
    }

    /**
     * Створює {@link ViewHolder} для збереження ViewBinding і прив'язки макета до RecyclerView.
     *
     * @param parent   Батьківська ViewGroup, до якої буде прив'язаний ViewHolder.
     * @param viewType Тип елемента, що визначається методом {@code getItemViewType}.
     * @return Новий об'єкт {@link ViewHolder} з ViewBinding.
     */
    @NonNull
    @Override
    public final ViewHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder<>(
                inflaters.get(viewType).inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    /**
     * Викликає метод {@code onBindView}, щоб прив'язати дані до ViewHolder для певного елемента.
     *
     * @param holder   Об'єкт {@link ViewHolder}, який зберігає ViewBinding.
     * @param position Позиція елемента в списку RecyclerView.
     */
    @Override
    public final void onBindViewHolder(@NonNull ViewHolder<VB> holder, int position) {
        onBindView(holder.getBinding(), position);
    }
}