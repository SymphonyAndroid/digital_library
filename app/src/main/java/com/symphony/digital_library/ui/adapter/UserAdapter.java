package com.symphony.digital_library.ui.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.databinding.ItemBookBinding;
import com.symphony.digital_library.databinding.ItemUserBinding;
import com.symphony.digital_library.ui.base.adapter.BaseRecyclerAdapter;
import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.function.NotNullConsumer;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseRecyclerAdapter<ItemUserBinding>  {

    @NonNull private final List<User> items;
    @NonNull private final NotNullConsumer<User> onUserClick;

    public UserAdapter(@NonNull List<User> items, @NonNull NotNullConsumer<User> onUserClick) {
        this.items = items;
        this.onUserClick = onUserClick;
    }

    @Override
    protected ViewBindingUtil.InflaterParent<ItemUserBinding> inflater(int position) {
        return ItemUserBinding::inflate;
    }

    @Override
    protected void onBindView(@NonNull ItemUserBinding binding, int position) {
        User item = items.get(position);
        binding.userNameTv.setText(item.getName());
        binding.userPhoneTv.setText(item.getPhone());
        binding.getRoot().setOnClickListener(v -> onUserClick.accept(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
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
