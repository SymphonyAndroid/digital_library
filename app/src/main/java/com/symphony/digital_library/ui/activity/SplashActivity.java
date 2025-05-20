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

/**
 * Клас {@code SplashActivity} реалізує логіку для Splash-екрану програми.
 * <p>
 * Клас є частиною MVP (Model-View-Presenter) структури, де він виступає як {@code View},
 * і взаємодіє з {@link SplashPresenter}.
 * </p>
 * <ul>
 *     <li>Використовує ViewBinding для прив'язки макета {@code ActivitySplashBinding}.</li>
 *     <li>Ініціює Presenter для обробки логіки Splash-екрану.</li>
 *     <li>Забезпечує перехід до головного екрана програми.</li>
 * </ul>
 */
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivityMvp<ActivitySplashBinding, Presenter, View> implements View {

    /**
     * Надає метод для створення інфлятора, який використовується для прив'язки макета {@code ActivitySplashBinding}
     * до Activity.
     *
     * @return Обʼєкт {@link ViewBindingUtil.Inflater}, який відповідає за інфляцію макета Splash-екрану.
     */
    @NonNull
    @Override
    protected ViewBindingUtil.Inflater<ActivitySplashBinding> inflater() {
        return ActivitySplashBinding::inflate;
    }

    /**
     * Ін'єкція Presenter для цієї Activity.
     * <p>
     * Ініціалізує об'єкт {@link SplashPresenter}, який містить бізнес-логіку Splash-екрану.
     * </p>
     *
     * @return Об'єкт {@link Presenter}, який обробляє логіку {@code SplashActivity}.
     */
    @Override
    protected Presenter presenterInject() {
        return new SplashPresenter();
    }

    /**
     * Метод, що викликається при створенні {@code SplashActivity}.
     * <p>
     * Викликає метод базового класу для забезпечення життєвого циклу {@code Activity}.
     * </p>
     *
     * @param savedInstanceState Збережений стан Activity, якщо він доступний (може бути {@code null}).
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Метод, що реалізує перехід до головного екрана програми.
     * <p>
     * Запускає {@link MainActivity}.
     * </p>
     */
    @Override
    public void openMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
    }

}