package com.symphony.digital_library.ui.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.symphony.digital_library.R;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.databinding.ItemBookBinding;
import com.symphony.digital_library.ui.base.adapter.BaseRecyclerAdapter;
import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.function.NotNullConsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * Адаптер {@code BookAdapter} для роботи зі списком книг у RecyclerView.
 * <p>
 * Використовує DataBinding для прив'язки даних до елементів списку.
 * Забезпечує:
 * <ul>
 *     <li>Відображення автора та назви книги.</li>
 *     <li>Управління кнопкою для дій (взяти чи повернути книгу).</li>
 *     <li>Можливість оновлення елементів за допомогою {@code DiffUtil} без перезавантаження всього списку.</li>
 * </ul>
 * </p>
 */
public class BookAdapter extends BaseRecyclerAdapter<ItemBookBinding> {

    @NonNull private final List<Book> items;
    @NonNull private final NotNullConsumer<Book> onBookClick;
    private final boolean isTakeBook;

    /**
     * Конструктор {@code BookAdapter}.
     *
     * @param items Список книг для відображення.
     * @param onBookClick Дія, яка виконується при натисканні на кнопку дії для конкретної книги.
     * @param isTakeBook Якщо {@code true}, показує кнопку "Взяти книгу", інакше — "Повернути книгу".
     */
    public BookAdapter(@NonNull List<Book> items,
                       @NonNull NotNullConsumer<Book> onBookClick,
                       boolean isTakeBook) {
        this.items = items;
        this.onBookClick = onBookClick;
        this.isTakeBook = isTakeBook;
    }

    /**
     * Повертає кількість елементів у списку книг.
     *
     * @return Кількість елементів у списку {@code items}.
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Надає об'єкт інфлятора для створення ViewBinding для окремого елемента списку.
     *
     * @param position Позиція елемента в списку.
     * @return Інфлятор для прив'язки макета {@code ItemBookBinding}.
     */
    @Override
    protected ViewBindingUtil.InflaterParent<ItemBookBinding> inflater(int position) {
        return ItemBookBinding::inflate;
    }

    /**
     * Прив'язує дані книги до елемента списку.
     *
     * @param binding Об'єкт ViewBinding для елемента списку.
     * @param position Позиція елемента у списку.
     */
    @Override
    protected void onBindView(@NonNull ItemBookBinding binding, int position) {
        Book item = items.get(position);
        binding.authorTv.setText(item.getAuthor());
        binding.titleTv.setText(item.getTitle());
        if (isTakeBook) binding.takeItBtn.setText(R.string.take_it);
        else binding.takeItBtn.setText(R.string.turn_the_book);
        binding.takeItBtn.setOnClickListener(it -> onBookClick.accept(item));
    }

    /**
     * Оновлює список елементів і виконує диференційне оновлення адаптера.
     * <p>
     * Використовує {@link DiffUtil} для визначення змін у списку.
     * </p>
     *
     * @param newList Новий список книг для оновлення.
     */
    public void updateItems(List<Book> newList) {
        List<Book> old = new ArrayList<>(items);
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
                return old.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return old.get(oldItemPosition) == newList.get(newItemPosition);
            }
        }).dispatchUpdatesTo(this);
    }

}