package com.symphony.digital_library.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.symphony.digital_library.R;
import com.symphony.digital_library.data.entity.Book;
import com.symphony.digital_library.databinding.FragmentTakeBookBinding;
import com.symphony.digital_library.mvp.take_book.TakeBookMvp;
import com.symphony.digital_library.mvp.take_book.TakeBookPresenter;
import com.symphony.digital_library.ui.base.fragment.BaseFragmentMvp;
import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.ViewUtil;

public class TakeBookFragment
        extends BaseFragmentMvp<FragmentTakeBookBinding, TakeBookMvp.Presenter, TakeBookMvp.View>
        implements TakeBookMvp.View {

    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentTakeBookBinding> inflater() {
        return FragmentTakeBookBinding::inflate;
    }

    @NonNull
    @Override
    protected TakeBookMvp.Presenter presenterInject() {
        return new TakeBookPresenter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Book book = TakeBookFragmentArgs.fromBundle(getArguments()).getBook();
        getPresenter().onBookChange(book);
        withBinding(vb -> {
            vb.authorTv.setText(book.getAuthor());
            vb.titleTv.setText(book.getTitle());
            vb.okBtn.setOnClickListener(v -> {
                CharSequence name = vb.nameEt.getText();
                CharSequence phone = vb.nameEt.getText();
                getPresenter().onSubmitClick(
                        name == null ? "" : name.toString(),
                        phone == null ? "" : phone.toString()
                );
            });
            ViewUtil.onTextChanged(vb.nameEt, it -> vb.nameTil.setError(null));
            ViewUtil.onTextChanged(vb.phoneEt, it -> vb.phoneTil.setError(null));
        });
    }

    @Override
    public void showNameError() {
        withBinding(vb -> vb.nameTil.setError(getString(R.string.name_error)));
    }

    @Override
    public void showPhoneError() {
        withBinding(vb -> vb.phoneTil.setError(getString(R.string.phone_error)));
    }

    @Override
    public void onFinishTakeBook() {
        // TODO: 26.04.2024
    }
}
