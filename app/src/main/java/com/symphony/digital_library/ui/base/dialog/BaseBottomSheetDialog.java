package com.symphony.digital_library.ui.base.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.function.NotNullConsumer;

public abstract class BaseBottomSheetDialog<VB extends ViewBinding> extends BottomSheetDialog {
    public BaseBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    private VB binding;
    private String viewBindingRootId;

    @NonNull
    protected abstract ViewBindingUtil.Inflater<VB> inflater();

    @NonNull
    protected abstract ViewBindingUtil.Bind<VB> bind();

    protected void withBinding(@NonNull NotNullConsumer<VB> action) {
        if (binding != null) action.accept(binding);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflater().inflate(getLayoutInflater());
        viewBindingRootId = ViewBindingUtil.saveViewBindingId(binding);
        setContentView(binding.getRoot());
    }

    @Override
    public void show() {
        super.show();

        Window window = getWindow();
        if (binding == null && window != null) {
            View root = ViewBindingUtil.findViewBindingRoot(window.getDecorView(), viewBindingRootId);
            if (root != null) binding = bind().bind(root);
        }

        if (binding != null) onShow(binding);
    }

    @Override
    public void dismiss() {
        binding = null;
        super.dismiss();
    }

    protected void onShow(@NonNull VB binding) {

    }
}
