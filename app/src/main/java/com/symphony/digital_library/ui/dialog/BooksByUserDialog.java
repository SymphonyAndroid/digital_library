package com.symphony.digital_library.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.databinding.DialogBooksByUserBinding;
import com.symphony.digital_library.ui.adapter.BookAdapter;
import com.symphony.digital_library.ui.base.dialog.BaseBottomSheetDialog;
import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.function.NotNullConsumer;

import java.util.List;

/**
 * Клас {@code BooksByUserDialog} представляє діалог у вигляді BottomSheet,
 * який відображає список книг, асоційованих із користувачем.
 */
public class BooksByUserDialog extends BaseBottomSheetDialog<DialogBooksByUserBinding> {

    /**
     * Список книг, які будуть відображені в діалозі.
     */
    private List<Book> books;

    /**
     * Callback-функція, яка викликається під час кліку на книгу.
     */
    private NotNullConsumer<Book> onBookClick = it -> {};

    /**
     * Конструктор для створення діалогу.
     *
     * @param context Контекст, у якому створюється діалог.
     */
    public BooksByUserDialog(@NonNull Context context) {
        super(context, 0);
    }

    /**
     * Задає список книг, які будуть показані в діалозі.
     *
     * @param items Список книг.
     * @return Поточний інстанс {@link BooksByUserDialog} (для ланцюжкового виклику методів).
     */
    public BooksByUserDialog setBooks(@NonNull List<Book> items) {
        books = items;
        return this;
    }

    /**
     * Задає callback-функцію, яка буде викликана під час вибору книги.
     *
     * @param onBookClick Callback-функція, що приймає об'єкт {@link Book}.
     * @return Поточний інстанс {@link BooksByUserDialog} (для ланцюжкового виклику методів).
     */
    public BooksByUserDialog onBookClick(@NonNull NotNullConsumer<Book> onBookClick) {
        this.onBookClick = onBookClick;
        return this;
    }

    /**
     * Повертає Inflater для створення об'єкта {@link DialogBooksByUserBinding}.
     *
     * @return Інстанс {@link ViewBindingUtil.Inflater}.
     */
    @NonNull
    @Override
    protected ViewBindingUtil.Inflater<DialogBooksByUserBinding> inflater() {
        return DialogBooksByUserBinding::inflate;
    }

    /**
     * Повертає Bind-функцію для прив'язки {@link DialogBooksByUserBinding} до існуючого View.
     *
     * @return Інстанс {@link ViewBindingUtil.Bind}.
     */
    @NonNull
    @Override
    protected ViewBindingUtil.Bind<DialogBooksByUserBinding> bind() {
        return DialogBooksByUserBinding::bind;
    }

    /**
     * Викликається під час відображення діалогу.
     * <p>
     * Налаштовує RecyclerView, передаючи адаптер із списком книг і обробником кліків.
     * </p>
     *
     * @param binding Ініціалізований об'єкт {@link DialogBooksByUserBinding}.
     */
    @Override
    protected void onShow(@NonNull DialogBooksByUserBinding binding) {
        super.onShow(binding);
        binding.booksRv.setAdapter(new BookAdapter(books, this::onBookClick, false));
    }

    /**
     * Викликається під час кліку на книгу зі списку.
     * <p>
     * Закриває діалог і викликає переданий callback із вибраною книгою.
     * </p>
     *
     * @param book Вибрана книга.
     */
    private void onBookClick(Book book) {
        dismiss();
        onBookClick.accept(book);
    }
}