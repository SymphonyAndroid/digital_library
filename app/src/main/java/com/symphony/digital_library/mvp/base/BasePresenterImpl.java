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

public abstract class BasePresenterImpl<V extends BaseMvp.BaseView> implements BaseMvp.BasePresenter<V> {

    private WeakReference<V> viewRef;
    private final CompositeDisposable subscriptions = new CompositeDisposable();

    @Override
    public final void attachView(@NonNull V view) {
        viewRef = new WeakReference<>(view);
        withView(this::onViewAttached);
    }

    @Override
    public final void detachView() {
        if (viewRef != null) viewRef.clear();
        viewRef = null;
    }

    @Override
    public final void withView(@NonNull NotNullConsumer<V> action) {
        V view = viewRef != null ? viewRef.get() : null;
        if (view != null) action.accept(view);
    }

    protected void onViewAttached(@NonNull V view) {

    }

    @NonNull
    private ComponentProvider getComponentProvider() {
        return ComponentProvider.Companion.getInstance();
    }

    @NonNull
    protected final AppSchedulers getSchedulers() {
        return getComponentProvider().getSchedulers();
    }

    @NonNull
    protected final UseCases getUseCases() {
        return getComponentProvider().getUseCases();
    }

    protected final void subscriptions(@NonNull Supplier<Disposable> add) {
        subscriptions.add(add.get());
    }

    protected final void onError(Throwable throwable) {
        withView(view -> view.showErrorToast(throwable.getLocalizedMessage()));
    }
}
