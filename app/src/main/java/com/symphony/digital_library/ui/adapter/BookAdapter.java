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

public class BookAdapter extends BaseRecyclerAdapter<ItemBookBinding> {

    @NonNull private final List<Book> items;
    @NonNull private final NotNullConsumer<Book> onBookClick;
    private final boolean isTakeBook;
    public BookAdapter(@NonNull List<Book> items,
                       @NonNull NotNullConsumer<Book> onBookClick,
                       boolean isTakeBook) {
        this.items = items;
        this.onBookClick = onBookClick;
        this.isTakeBook = isTakeBook;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    protected ViewBindingUtil.InflaterParent<ItemBookBinding> inflater(int position) {
        return ItemBookBinding::inflate;
    }

    @Override
    protected void onBindView(@NonNull ItemBookBinding binding, int position) {
        Book item = items.get(position);
        binding.authorTv.setText(item.getAuthor());
        binding.titleTv.setText(item.getTitle());
        if (isTakeBook) binding.takeItBtn.setText(R.string.take_it);
        else binding.takeItBtn.setText(R.string.turn_the_book);
        binding.takeItBtn.setOnClickListener(it -> onBookClick.accept(item));
    }

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
