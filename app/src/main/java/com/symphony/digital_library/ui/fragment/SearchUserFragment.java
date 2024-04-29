package com.symphony.digital_library.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.symphony.digital_library.R;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.data.entity.User;
import com.symphony.digital_library.databinding.FragmentSearchBinding;
import com.symphony.digital_library.mvp.search_user.SearchUserMvp;
import com.symphony.digital_library.mvp.search_user.SearchUserPresenter;
import com.symphony.digital_library.ui.adapter.UserAdapter;
import com.symphony.digital_library.ui.base.fragment.BaseFragmentMvp;
import com.symphony.digital_library.ui.dialog.BooksByUserDialog;
import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchUserFragment
        extends BaseFragmentMvp<FragmentSearchBinding, SearchUserMvp.Presenter, SearchUserMvp.View>
        implements SearchUserMvp.View {

    private UserAdapter adapter;

    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentSearchBinding> inflater() {
        return FragmentSearchBinding::inflate;
    }

    @NonNull
    @Override
    protected SearchUserMvp.Presenter presenterInject() {
        return new SearchUserPresenter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new UserAdapter(new ArrayList<>(), this::onUserClick);
        withBinding(binding -> {
            binding.searchTil.setHint(getString(R.string.search_user_hint));
            binding.recycler.setAdapter(adapter);
            ViewUtil.onTextChanged(binding.searchEt, query -> getPresenter().onQueryChanged(query));
        });
    }

    @Override
    public void showUsers(List<User> users) {
        adapter.updateItems(users);
    }

    @Override
    public void updateBooks(List<Book> books) {
        BooksByUserDialog dialog = new BooksByUserDialog(requireContext())
                .setBooks(books)
                .onBookClick(this::onBookClick);
        dialog.show();
    }

    @Override
    public void onEmptyBooks() {
        String text = getString(R.string.book_taken_empty);
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBookReturned(String name, String title) {
        String text = getString(R.string.book_returned, name, title);
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show();
    }

    private void onBookClick(Book book) {
        getPresenter().onReturnBook(book);
    }

    private void onUserClick(User user) {
        getPresenter().onUserClick(user);
    }
}
