package com.symphony.digital_library.ui.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.symphony.digital_library.R;
import com.symphony.digital_library.databinding.DialogTakeBookBinding;
import com.symphony.digital_library.ui.base.dialog.BaseBottomSheetDialog;
import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.ViewUtil;
import com.symphony.digital_library.util.function.NotNullConsumer;

public class TakeBookBottomDialog extends BaseBottomSheetDialog<DialogTakeBookBinding> {

    private boolean isNameStep = true;
    private NotNullConsumer<String> onNameChanged = it -> {};
    private NotNullConsumer<String> onPhoneChanged = it -> {};
    public TakeBookBottomDialog(@NonNull Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    protected ViewBindingUtil.Inflater<DialogTakeBookBinding> inflater() {
        return DialogTakeBookBinding::inflate;
    }

    @NonNull
    @Override
    protected ViewBindingUtil.Bind<DialogTakeBookBinding> bind() {
        return DialogTakeBookBinding::bind;
    }
    public TakeBookBottomDialog onNameChanged(@NonNull NotNullConsumer<String> onNameChanged) {
        this.onNameChanged = onNameChanged;
        return this;
    }

    public TakeBookBottomDialog onPhoneChanged(@NonNull NotNullConsumer<String> onPhoneChanged) {
        this.onPhoneChanged = onPhoneChanged;
        return this;
    }

    @Override
    protected void onShow(@NonNull DialogTakeBookBinding binding) {
        super.onShow(binding);
        withBinding(vb -> {
            vb.okBtn.setOnClickListener(v -> {
                String text = vb.editText.getText() == null ? "" : vb.editText.getText().toString();
                onOkClick(text);
            });
            ViewUtil.onTextChanged(binding.editText, text -> {
                vb.okBtn.setEnabled(!text.isEmpty());
            });
        });
    }

    private void onOkClick(String text) {
        withBinding(vb -> {
            if (isNameStep) {
                onNameChanged.accept(text);
                isNameStep = false;
                vb.til.setHint(getContext().getString(R.string.phone_hint));
            } else onPhoneChanged.accept(text);
        });
    }

    public void setPhone(String phone) {
        withBinding(vb -> vb.editText.setText(phone));
    }

    public void showProgressBar(boolean isVisible) {
        withBinding(vb -> {
            if (isVisible) vb.progressBar.setVisibility(View.VISIBLE);
            else vb.progressBar.setVisibility(View.GONE);
        });
    }
}
