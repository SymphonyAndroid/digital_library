package com.symphony.digital_library.ui.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import com.symphony.digital_library.R;
import com.symphony.digital_library.mvp.base.BaseMvp;

public abstract class BaseFragmentMvp<VB extends ViewBinding, P extends BaseMvp.BasePresenter<V>, V extends BaseMvp.BaseView>
        extends BaseFragment<VB> implements BaseMvp.BaseView {

    private P presenter;

    @NonNull protected abstract P presenterInject();

    @NonNull
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = presenterInject();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        presenter.attachView((V) this);
        return view;
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void showErrorToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void navigateTo(@IdRes int idRes) {
        navigateTo(idRes, null);
    }

    protected void navigateTo(@IdRes int idRes, Bundle bundle) {
        NavHostFragment
                .findNavController(this)
                .navigate(idRes, bundle);
    }
}
