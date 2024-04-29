package com.symphony.digital_library.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.symphony.digital_library.R;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.databinding.FragmentSearchBinding;
import com.symphony.digital_library.mvp.search_book.SearchBookMvp;
import com.symphony.digital_library.mvp.search_book.SearchBookPresenter;
import com.symphony.digital_library.ui.adapter.BookAdapter;
import com.symphony.digital_library.ui.base.fragment.BaseFragmentMvp;
import com.symphony.digital_library.ui.dialog.TakeBookBottomDialog;
import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchBookFragment
        extends BaseFragmentMvp<FragmentSearchBinding, SearchBookMvp.Presenter, SearchBookMvp.View>
        implements SearchBookMvp.View {

    private BookAdapter adapter;
    private TakeBookBottomDialog dialog;

    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentSearchBinding> inflater() {
        return FragmentSearchBinding::inflate;
    }

    @NonNull
    @Override
    protected SearchBookMvp.Presenter presenterInject() {
        return new SearchBookPresenter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new BookAdapter(new ArrayList<>(), this::onBookClick, true);
        withBinding(binding -> {
            binding.searchTil.setHint(getString(R.string.search_book_hint));
            binding.recycler.setAdapter(adapter);
            ViewUtil.onTextChanged(binding.searchEt, query -> getPresenter().onQueryChanged(query));
        });
    }

    @Override
    public void showResult(List<Book> result) {
        adapter.updateItems(result);
    }

    private void onBookClick(Book book) {
        dialog = new TakeBookBottomDialog(requireContext());
        dialog
                .onNameChanged(name -> getPresenter().onNameChanged(name))
                .onPhoneChanged(phone -> getPresenter().onPhoneChanged(phone, book))
                .show();
    }

    @Override
    public void showUserPhone(String phone) {
        dialog.setPhone(phone);
    }

    @Override
    public void onTakeSuccess(String userName, String bookName) {
        dialog.dismiss();
        String text = getString(R.string.book_taken, userName, bookName);
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar(boolean isVisible) {
        dialog.showProgressBar(isVisible);
    }

}
