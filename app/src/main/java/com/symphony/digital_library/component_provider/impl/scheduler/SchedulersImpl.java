package com.symphony.digital_library.component_provider.impl.scheduler;

import androidx.annotation.NonNull;

import com.symphony.digital_library.component_provider.components.AppSchedulers;

import java.util.concurrent.Executors;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulersImpl implements AppSchedulers {

    private Scheduler bank;


    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @NonNull
    @Override
    public Scheduler db() {
        if (bank == null) bank = Schedulers.from(Executors.newSingleThreadExecutor());
        return bank;
    }
}
