package com.symphony.digital_library;

import android.app.Application;

import com.symphony.digital_library.component_provider.ComponentProvider;
import com.symphony.digital_library.component_provider.impl.ComponentProviderImpl;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ComponentProvider.Companion.inject(new ComponentProviderImpl(this));
    }
}
