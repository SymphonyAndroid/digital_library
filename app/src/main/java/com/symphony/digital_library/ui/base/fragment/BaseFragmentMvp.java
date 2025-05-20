package com.symphony.digital_library.ui.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import com.symphony.digital_library.R;
import com.symphony.digital_library.mvp.base.BaseMvp;

/**
 * Абстрактний клас {@code BaseFragmentMvp}, який розширює {@link BaseFragment}, надаючи підтримку
 * архітектури MVP (Model-View-Presenter).
 *
 * @param <VB> Тип, що розширює {@link ViewBinding}, і використовується для прив'язки макета до Fragment.
 * @param <P>  Тип, що розширює {@link BaseMvp.BasePresenter}, який використовується як Presenter для цього Fragment.
 * @param <V>  Тип, що розширює {@link BaseMvp.BaseView}, який реалізує View для Presenter.
 */
public abstract class BaseFragmentMvp<VB extends ViewBinding, P extends BaseMvp.BasePresenter<V>, V extends BaseMvp.BaseView>
        extends BaseFragment<VB> implements BaseMvp.BaseView {

    /**
     * Presenter, відповідальний за обробку бізнес-логіки та зв'язок між View і Model у цьому Fragment.
     */
    private P presenter;

    /**
     * Абстрактний метод для створення інстанса Presenter.
     * <p>
     * Має бути реалізований у дочірніх класах для ін'єкції відповідного Presenter.
     * </p>
     *
     * @return Інстанс Presenter, реалізований для використання в цьому Fragment.
     */
    @NonNull
    protected abstract P presenterInject();

    /**
     * Повертає інстанс Presenter, який прив'язаний до цього Fragment.
     *
     * @return Ініціалізований об'єкт Presenter.
     */
    @NonNull
    public P getPresenter() {
        return presenter;
    }

    /**
     * Викликається під час створення Fragment.
     * <p>
     * Ініціалізує Presenter, використовуючи метод {@code presenterInject}, перед створенням View.
     * </p>
     *
     * @param savedInstanceState Збережений стан для відновлення (може бути {@code null}).
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = presenterInject();
    }

    /**
     * Викликається для створення View цього Fragment за допомогою ViewBinding.
     * <p>
     * Крім ініціалізації View, прив'язує Presenter до View через {@code attachView}.
     * </p>
     *
     * @param inflater  Об'єкт {@link LayoutInflater}, який використовується для "надування" макета.
     * @param container Батьківська ViewGroup, до якої додається макет (може бути {@code null}).
     * @param savedInstanceState Стан, збережений під час попереднього створення (може бути {@code null}).
     * @return Створений об'єкт View, прив'язаний до ViewBinding.
     */
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        // Виклик базового onCreateView для ініціалізації ViewBinding
        View view = super.onCreateView(inflater, container, savedInstanceState);
        // Прив'язка Presenter до View
        presenter.attachView((V) this);
        return view;
    }

    /**
     * Викликається при знищенні View Fragment.
     * <p>
     * Після видалення View Presenter знімається з View для уникнення витоків пам'яті.
     * </p>
     */
    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }

    /**
     * Показує помилкове повідомлення у вигляді Toast.
     *
     * @param message Текст повідомлення, яке потрібно показати.
     */
    @Override
    public void showErrorToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Метод для навігації до нового екрану за допомогою його {@code id}.
     *
     * @param idRes Ресурсний ідентифікатор цілі навігації.
     */
    protected void navigateTo(@IdRes int idRes) {
        navigateTo(idRes, null);
    }

    /**
     * Метод для навігації до нового екрану з передачею додаткових даних.
     *
     * @param idRes  Ресурсний ідентифікатор цілі навігації.
     * @param bundle Додаткові параметри для передачі в новий екран (може бути {@code null}).
     */
    protected void navigateTo(@IdRes int idRes, Bundle bundle) {
        NavHostFragment
                .findNavController(this)
                .navigate(idRes, bundle);
    }
}