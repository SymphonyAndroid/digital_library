package com.symphony.digital_library.mvp.splash;

import com.symphony.digital_library.mvp.base.BaseMvp;

public interface SplashMvp {

    interface View extends BaseMvp.BaseView {

        void showDatabaseError(String message);

        void openMainScreen();

    }

    interface Presenter extends BaseMvp.BasePresenter<View> {

    }

}
