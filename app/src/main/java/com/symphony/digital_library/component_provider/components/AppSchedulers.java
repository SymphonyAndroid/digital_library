package com.symphony.digital_library.component_provider.components;

import androidx.annotation.NonNull;

import io.reactivex.Scheduler;

public interface AppSchedulers {

    @NonNull Scheduler io();
    @NonNull Scheduler ui();
    @NonNull Scheduler db();
}
