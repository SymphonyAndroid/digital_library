package com.symphony.digital_library.mvp.main;

import com.symphony.digital_library.mvp.base.BaseMvp;

public interface MainMvp {

    interface View extends BaseMvp.BaseView {

        void openSearchBookScreen();

        void openSearchUserScreen();
    }

    interface Presenter extends BaseMvp.BasePresenter<View> {

        void onSearchBookClick();
        void onSearchTakenBookClick();

    }

}
