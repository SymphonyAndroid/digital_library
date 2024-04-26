package com.symphony.digital_library.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.symphony.digital_library.R;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.databinding.FragmentSearchBookBinding;
import com.symphony.digital_library.mvp.search_book.SearchBookMvp;
import com.symphony.digital_library.mvp.search_book.SearchBookPresenter;
import com.symphony.digital_library.ui.adapter.BookAdapter;
import com.symphony.digital_library.ui.base.fragment.BaseFragmentMvp;
import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchBookFragment
        extends BaseFragmentMvp<FragmentSearchBookBinding, SearchBookMvp.Presenter, SearchBookMvp.View>
        implements SearchBookMvp.View {

    private BookAdapter adapter;
    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentSearchBookBinding> inflater() {
        return FragmentSearchBookBinding::inflate;
    }

    @NonNull
    @Override
    protected SearchBookMvp.Presenter presenterInject() {
        return new SearchBookPresenter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new BookAdapter(new ArrayList<>(), this::onBookClick);
        withBinding(binding -> {
            binding.bookRv.setAdapter(adapter);
            ViewUtil.onTextChanged(binding.searchEt, query -> getPresenter().onQueryChanged(query));
        });
    }

    @Override
    public void showResult(List<Book> result) {
        adapter.updateItems(result);
    }
    private void onBookClick(Book book) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", book);
        navigateTo(R.id.action_searchBookFragment_to_takeBookFragment, bundle);
    }

}
