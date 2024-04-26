package com.symphony.digital_library.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.symphony.digital_library.component_provider.components.Preferences;


public class PreferencesImpl implements Preferences {

    private final SharedPreferences preferences;

    public PreferencesImpl(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
