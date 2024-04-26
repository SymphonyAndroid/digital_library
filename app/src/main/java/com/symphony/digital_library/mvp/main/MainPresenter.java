package com.symphony.digital_library.mvp.main;

import androidx.annotation.NonNull;

import com.symphony.digital_library.mvp.base.BasePresenterImpl;

public class MainPresenter extends BasePresenterImpl<MainMvp.View> implements MainMvp.Presenter {

    @Override
    protected void onViewAttached(@NonNull MainMvp.View view) {
        super.onViewAttached(view);

    }

    @Override
    public void onSearchBookClick() {
        withView(MainMvp.View::openSearchBookScreen);
    }

    @Override
    public void onSearchTakenBookClick() {
        withView(MainMvp.View::openSearchUserScreen);
    }
}
