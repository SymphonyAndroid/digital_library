package com.symphony.digital_library.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.symphony.digital_library.util.function.NotNullConsumer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewUtil {

    public static List<View> allViews(View view) {
        if (view == null) return Collections.emptyList();

        List<View> views = new ArrayList<>();
        views.add(view);

        if (!(view instanceof ViewGroup)) return views;

        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            View v = ((ViewGroup) view).getChildAt(i);
            if (v instanceof ViewGroup) views.addAll(allViews(v)); else views.add(v);
        }

        return views;
    }

    public static void onTextChanged(EditText view, NotNullConsumer<String> function) {
        view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                function.accept(s == null ? "" : s.toString());
            }
        });
    }
}
