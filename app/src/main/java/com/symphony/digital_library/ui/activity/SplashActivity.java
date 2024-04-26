package com.symphony.digital_library.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.symphony.digital_library.databinding.ActivitySplashBinding;
import com.symphony.digital_library.mvp.splash.SplashMvp.*;
import com.symphony.digital_library.mvp.splash.SplashPresenter;
import com.symphony.digital_library.ui.base.activity.BaseActivityMvp;
import com.symphony.digital_library.util.ViewBindingUtil;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivityMvp<ActivitySplashBinding, Presenter, View> implements View {

    @NonNull
    @Override
    protected ViewBindingUtil.Inflater<ActivitySplashBinding> inflater() {
        return ActivitySplashBinding::inflate;
    }

    @Override
    protected Presenter presenterInject() {
        return new SplashPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showDatabaseError(String message) {
        Snackbar.make(this, getBinding().getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void openMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
    }

}
