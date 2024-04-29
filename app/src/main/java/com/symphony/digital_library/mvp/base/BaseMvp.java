package com.symphony.digital_library.mvp.base;

import androidx.annotation.NonNull;

import com.symphony.digital_library.util.function.NotNullConsumer;

public interface BaseMvp {

    interface BaseView {
        void showErrorToast(String message);
    }

    interface BasePresenter<V extends  BaseView> {
        void attachView(@NonNull V view);
        void detachView();
        void withView(@NonNull NotNullConsumer<V> action);

    }
}
