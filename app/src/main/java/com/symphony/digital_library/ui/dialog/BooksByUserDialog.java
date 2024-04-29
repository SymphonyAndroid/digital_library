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

public class BooksByUserDialog extends BaseBottomSheetDialog<DialogBooksByUserBinding> {
    private List<Book> books;

    private NotNullConsumer<Book> onBookClick = it -> {};

    public BooksByUserDialog(@NonNull Context context) {
        super(context, 0);
    }

    public BooksByUserDialog setBooks(@NonNull List<Book> items) {
        books = items;
        return this;
    }

    public BooksByUserDialog onBookClick(@NonNull NotNullConsumer<Book> onBookClick) {
        this.onBookClick = onBookClick;
        return this;
    }

    @NonNull
    @Override
    protected ViewBindingUtil.Inflater<DialogBooksByUserBinding> inflater() {
        return DialogBooksByUserBinding::inflate;
    }

    @NonNull
    @Override
    protected ViewBindingUtil.Bind<DialogBooksByUserBinding> bind() {
        return DialogBooksByUserBinding::bind;
    }

    protected void onShow(@NonNull DialogBooksByUserBinding binding) {
        super.onShow(binding);
        binding.booksRv.setAdapter(new BookAdapter(books, this::onBookClick, false));
    }

    private void onBookClick(Book book) {
        dismiss();
        onBookClick.accept(book);
    }

}
