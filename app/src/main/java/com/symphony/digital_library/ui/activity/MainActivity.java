package com.symphony.digital_library.ui.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.symphony.digital_library.databinding.ActivityMainBinding;
import com.symphony.digital_library.ui.base.activity.BaseActivity;
import com.symphony.digital_library.util.ViewBindingUtil;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @NonNull
    @Override
    protected ViewBindingUtil.Inflater<ActivityMainBinding> inflater() {
        return ActivityMainBinding::inflate;
    }

}