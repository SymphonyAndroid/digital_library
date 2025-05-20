package com.symphony.digital_library.ui.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.databinding.ItemUserBinding;
import com.symphony.digital_library.ui.base.adapter.BaseRecyclerAdapter;
import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.function.NotNullConsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * Адаптер {@code UserAdapter} для роботи зі списком користувачів у RecyclerView.
 * <p>
 * Використовує DataBinding для прив'язки даних до елементів списку.
 * Забезпечує:
 * <ul>
 *     <li>Відображення імені та номера телефону користувача.</li>
 *     <li>Встановлення обробника кліків для кожного елемента списку.</li>
 *     <li>Оновлення списку з використанням механізму {@code DiffUtil}.</li>
 * </ul>
 * </p>
 */
public class UserAdapter extends BaseRecyclerAdapter<ItemUserBinding>  {

    @NonNull private final List<User> items;
    @NonNull private final NotNullConsumer<User> onUserClick;

    /**
     * Конструктор {@code UserAdapter}.
     *
     * @param items Список користувачів для відображення.
     * @param onUserClick Дія, яка виконується при натисканні на елемент користувача.
     */
    public UserAdapter(@NonNull List<User> items, @NonNull NotNullConsumer<User> onUserClick) {
        this.items = items;
        this.onUserClick = onUserClick;
    }

    /**
     * Надає об'єкт інфлятора для створення ViewBinding для окремого елемента списку.
     *
     * @param position Позиція елемента в списку.
     * @return Інфлятор для прив'язки макета {@code ItemUserBinding}.
     */
    @Override
    protected ViewBindingUtil.InflaterParent<ItemUserBinding> inflater(int position) {
        return ItemUserBinding::inflate;
    }

    /**
     * Прив'язує дані користувача до елемента списку.
     *
     * @param binding Об'єкт ViewBinding для елемента списку.
     * @param position Позиція елемента у списку.
     */
    @Override
    protected void onBindView(@NonNull ItemUserBinding binding, int position) {
        User item = items.get(position);
        binding.userNameTv.setText(item.getName());
        binding.userPhoneTv.setText(item.getPhone());
        binding.getRoot().setOnClickListener(v -> onUserClick.accept(item));
    }

    /**
     * Повертає кількість елементів у списку користувачів.
     *
     * @return Кількість елементів у списку {@code items}.
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Оновлює список елементів і виконує диференційне оновлення адаптера.
     * <p>
     * Використовує {@link DiffUtil} для визначення змін між старим та новим списком.
     * </p>
     *
     * @param newList Новий список користувачів для оновлення.
     */
    public void updateItems(List<User> newList) {
        List<User> old = new ArrayList<>(items);
        items.clear();
        items.addAll(newList);

        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return old.size();
            }

            @Override
            public int getNewListSize() {
                return newList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return old.get(oldItemPosition).getName().equals(newList.get(newItemPosition).getName());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return old.get(oldItemPosition) == newList.get(newItemPosition);
            }
        }).dispatchUpdatesTo(this);
    }
}