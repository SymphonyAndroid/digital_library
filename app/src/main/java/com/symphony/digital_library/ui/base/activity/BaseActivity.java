package com.symphony.digital_library.ui.base.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewbinding.ViewBinding;

import com.symphony.digital_library.util.ViewBindingUtil;
import com.symphony.digital_library.util.function.NotNullConsumer;

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    private VB binding;

    @NonNull
    protected abstract ViewBindingUtil.Inflater<VB> inflater();

    @NonNull
    public VB getBinding() {
        return binding;
    }

    protected void withBinding(@NonNull NotNullConsumer<VB> action) {
        if (binding != null) action.accept(binding);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = inflater().inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        withBinding(it -> ViewCompat.setOnApplyWindowInsetsListener(it.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        }));
    }

    @Override
    protected void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
