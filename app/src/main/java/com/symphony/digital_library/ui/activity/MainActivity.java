package com.symphony.digital_library.ui.activity;

import androidx.annotation.NonNull;

import com.symphony.digital_library.databinding.ActivityMainBinding;
import com.symphony.digital_library.ui.base.activity.BaseActivity;
import com.symphony.digital_library.util.ViewBindingUtil;

/**
 * Клас {@code MainActivity} реалізує головний екран програми.
 * <p>
 * Цей клас розширює {@code BaseActivity}, використовуючи ViewBinding для зв’язування макета Activity.
 * </p>
 */
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    /**
     * Визначає метод для ініціалізації ViewBinding у {@code MainActivity}.
     * <p>
     * Метод використовує {@code ActivityMainBinding::inflate}, що дозволяє створити прив'язку
     * макета до активності через ViewBinding.
     * </p>
     *
     * @return Обʼєкт {@link ViewBindingUtil.Inflater}, який виконує інфляцію макету {@code ActivityMainBinding}.
     */
    @NonNull
    @Override
    protected ViewBindingUtil.Inflater<ActivityMainBinding> inflater() {
        return ActivityMainBinding::inflate;
    }

}