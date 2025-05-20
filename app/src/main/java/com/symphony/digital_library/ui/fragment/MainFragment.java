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

/**
 * Клас {@code MainFragment}, який відповідає за головний екран програми.
 * <p>
 * Цей фрагмент реалізує {@link MainMvp.View} для роботи з архітектурною моделлю MVP і взаємодії з {@link MainMvp.Presenter}.
 * </p>
 */
public class MainFragment extends BaseFragmentMvp<FragmentMainBinding, MainMvp.Presenter, MainMvp.View> implements MainMvp.View {

    /**
     * Надає функцію для створення об'єкта {@link FragmentMainBinding} через метод інфлювання.
     *
     * @return Інстанс {@link ViewBindingUtil.InflaterParent}, який інфлятує {@link FragmentMainBinding}.
     */
    @NonNull
    @Override
    protected ViewBindingUtil.InflaterParent<FragmentMainBinding> inflater() {
        return FragmentMainBinding::inflate;
    }

    /**
     * Ін'єктує Presenter для цього фрагменту.
     * <p>
     * Повертає новий екземпляр {@link MainPresenter}, який реалізує {@link MainMvp.Presenter}.
     * </p>
     *
     * @return Інстанс Presenter для цього фрагменту.
     */
    @NonNull
    @Override
    protected MainMvp.Presenter presenterInject() {
        return new MainPresenter();
    }

    /**
     * Викликається після створення View для цього фрагменту.
     * <p>
     * Налаштовує обробники подій для кнопок пошуку книг та пошуку виданих книг.
     * </p>
     *
     * @param view Створене View для фрагменту.
     * @param savedInstanceState Збережений стан, якщо фрагмент відновлюється (може бути {@code null}).
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        withBinding(vb -> {
            // Обробник натискання кнопки "Пошук книг"
            vb.searchBook.setOnClickListener(it -> getPresenter().onSearchBookClick());
            // Обробник натискання кнопки "Пошук виданих книг"
            vb.searchTakenBook.setOnClickListener(it -> getPresenter().onSearchTakenBookClick());
        });
    }

    /**
     * Відкриває екран для пошуку книг.
     * <p>
     * Використовується {@link #navigateTo} для переходу до фрагмента {@code searchBookFragment}.
     * </p>
     */
    @Override
    public void openSearchBookScreen() {
        navigateTo(R.id.action_mainFragment_to_searchBookFragment);
    }

    /**
     * Відкриває екран для пошуку користувачів.
     * <p>
     * Використовується {@link #navigateTo} для переходу до фрагмента {@code searchUserFragment}.
     * </p>
     */
    @Override
    public void openSearchUserScreen() {
        navigateTo(R.id.action_mainFragment_to_searchUserFragment);
    }
}