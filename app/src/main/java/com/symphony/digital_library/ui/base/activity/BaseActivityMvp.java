package com.symphony.digital_library.ui.base.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.symphony.digital_library.mvp.base.BaseMvp;

/**
 * Абстрактний клас {@code BaseActivityMvp} для реалізації MVP (Model-View-Presenter) архітектури в Activity.
 * <p>
 * Розширює {@link BaseActivity} з додаванням Presenter та View для взаємодії у рамках MVP.
 * </p>
 *
 * @param <VB> Тип, що розширює {@link ViewBinding}, використовується для зв'язування макета.
 * @param <P>  Тип Presenter, що розширює {@link BaseMvp.BasePresenter}.
 * @param <V>  Тип View, що розширює {@link BaseMvp.BaseView}.
 */
public abstract class BaseActivityMvp<VB extends ViewBinding, P extends BaseMvp.BasePresenter<V>, V extends BaseMvp.BaseView>
        extends BaseActivity<VB> implements BaseMvp.BaseView {

    private P presenter;

    /**
     * Абстрактний метод для забезпечення ін'єкції Presenter.
     * <p>
     * Кожна Activity, яка наслідує цей клас, повинна реалізувати цей метод для
     * створення конкретного Presenter.
     * </p>
     *
     * @return Інстанс Presenter, який буде пов'язаний з View.
     */
    protected abstract P presenterInject();

    /**
     * Повертає ін'єктований Presenter для взаємодії з Activity.
     *
     * @return Інстанс Presenter, пов'язаний з View.
     */
    @NonNull
    public P getPresenter() {
        return presenter;
    }

    /**
     * Метод життєвого циклу {@code onCreate}, який:
     * <ul>
     *     <li>Викликає метод {@code presenterInject()} для створення Presenter.</li>
     *     <li>Прив'язує Presenter до View за допомогою {@link BaseMvp.BasePresenter#attachView(BaseMvp.BaseView)}.</li>
     * </ul>
     *
     * @param savedInstanceState Збережений стан Activity, якщо він доступний (може бути {@code null}).
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ініціалізація Presenter
        presenter = presenterInject();
        // Прив'язка Presenter до View
        presenter.attachView((V) this);
    }

    /**
     * Метод життєвого циклу {@code onDestroy}, який:
     * <ul>
     *     <li>Від'єднує Presenter від View для запобігання витокам пам'яті.</li>
     *     <li>Очищає ресурси за допомогою {@code super.onDestroy()}.</li>
     * </ul>
     */
    @Override
    public void onDestroy() {
        // Від'єднання Presenter
        presenter.detachView();
        super.onDestroy();
    }

    /**
     * Показує Toast-повідомлення з текстом помилки.
     *
     * @param message Текст повідомлення про помилку.
     */
    @Override
    public void showErrorToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}