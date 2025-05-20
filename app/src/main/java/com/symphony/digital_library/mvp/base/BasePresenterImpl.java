package com.symphony.digital_library.mvp.base;

import androidx.annotation.NonNull;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.components.AppSchedulers;
import com.symphony.digital_library.component_provider.components.UseCases;
import com.symphony.digital_library.util.function.NotNullConsumer;

import java.lang.ref.WeakReference;
import java.util.function.Supplier;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Абстрактний клас {@code BasePresenterImpl} реалізує базову функціональність Presenter
 * у структурі MVP (Model-View-Presenter).
 * <p>
 * Цей клас забезпечує керування життєвим циклом View, роботу з RxJava підписками
 * та інструментами для взаємодії з компонентами додатку.
 * </p>
 *
 * @param <V> Тип View, який наслідує {@link BaseMvp.BaseView}.
 */
public abstract class BasePresenterImpl<V extends BaseMvp.BaseView> implements BaseMvp.BasePresenter<V> {

    /**
     * Слабке посилання на прикріплене View для запобігання утриманню об'єкта в пам'яті після його
     * деаттачу.
     */
    private WeakReference<V> viewRef;

    /**
     * Колекція RxJava підписок для автоматичного керування їх життєвим циклом.
     */
    private final CompositeDisposable subscriptions = new CompositeDisposable();

    /**
     * Прикріплює View до Presenter.
     * <p>
     * Крім цього, викликає метод {@link #onViewAttached(BaseMvp.BaseView)},
     * щоб виконати будь-яку додаткову логіку після підключення View.
     * </p>
     *
     * @param view Екземпляр View, який потрібно зв'язати з Presenter.
     */
    @Override
    public final void attachView(@NonNull V view) {
        viewRef = new WeakReference<>(view);
        withView(this::onViewAttached);
    }

    /**
     * Від'єднує View від Presenter.
     * <p>
     * Очищає слабке посилання на View, щоб уникнути утримання об'єкта у пам'яті.
     * </p>
     */
    @Override
    public final void detachView() {
        if (viewRef != null) viewRef.clear();
        viewRef = null;
    }

    /**
     * Виконує дію з прикріпленим View, якщо воно доступне.
     *
     * @param action Дія, яка приймає View і виконується, якщо View прикріплене.
     */
    @Override
    public final void withView(@NonNull NotNullConsumer<V> action) {
        V view = viewRef != null ? viewRef.get() : null;
        if (view != null) action.accept(view);
    }

    /**
     * Викликається після підключення View до Presenter.
     * <p>
     * Використовується для виконання логіки, яка залежить від активного View.
     * Метод може бути перевизначений у дочірніх класах.
     * </p>
     *
     * @param view Прикріплене View.
     */
    protected void onViewAttached(@NonNull V view) {

    }

    /**
     * Отримує екземпляр {@link ComponentProvider}, який забезпечує доступ до компонентів додатку.
     *
     * @return Екземпляр компонентного провайдера.
     */
    @NonNull
    private ComponentProvider getComponentProvider() {
        return ComponentProvider.Companion.getInstance();
    }

    /**
     * Отримує екземпляр {@link AppSchedulers} для управління потоками RxJava.
     *
     * @return Екземпляр {@code AppSchedulers}.
     */
    @NonNull
    protected final AppSchedulers getSchedulers() {
        return getComponentProvider().getSchedulers();
    }

    /**
     * Отримує доступ до всіх доступних UseCase через екземпляр {@link UseCases}.
     *
     * @return Екземпляр {@code UseCases}.
     */
    @NonNull
    protected final UseCases getUseCases() {
        return getComponentProvider().getUseCases();
    }

    /**
     * Додає RxJava-підписку до загального контейнера підписок для автоматичного очищення.
     *
     * @param add Постачальник підписки, яка буде додана.
     */
    protected final void subscriptions(@NonNull Supplier<Disposable> add) {
        subscriptions.add(add.get());
    }

    /**
     * Обробляє помилки шляхом відображення повідомлення про помилку на View.
     *
     * @param throwable Виняток, який потрібно обробити.
     */
    protected final void onError(Throwable throwable) {
        withView(view -> view.showErrorToast(throwable.getLocalizedMessage()));
    }
}