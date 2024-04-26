package com.symphony.digital_library.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.symphony.digital_library.R;
import com.symphony.digital_library.databinding.FragmentMainBinding;
import com.symphony.digital_library.mvp.main.MainMvp;
import com.symphony.digital_library.mvp.main.MainPresenter;
import com.symphony.digital_library.ui.base.fragment.BaseFragmentMvp;
import com.symphony.digital_library.util.ViewBindingUtil;

public class MainFragment extends BaseFragmentMvp<FragmentMainBinding, MainMvp.Presenter, MainMvp.View> implements MainMvp.View {
    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentMainBinding> inflater() {
        return FragmentMainBinding::inflate;
    }
    @NonNull
    @Override
    protected MainMvp.Presenter presenterInject() {
        return new MainPresenter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        withBinding(vb -> {
            vb.searchBook.setOnClickListener(it -> getPresenter().onSearchBookClick());
            vb.searchTakenBook.setOnClickListener(it -> getPresenter().onSearchTakenBookClick());
        });
    }

    @Override
    public void openSearchBookScreen() {
        navigateTo(R.id.action_mainFragment_to_searchBookFragment);
    }

    @Override
    public void openSearchUserScreen() {
        navigateTo(R.id.action_mainFragment_to_searchUserFragment);
    }
}
