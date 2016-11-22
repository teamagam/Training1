package com.teamagam.dailyselfie;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DailySelfieApplication extends Application {

    private static final String PREFERENCE_FIRST_RUN = "first_run";

    @Override
    public void onCreate() {
        super.onCreate();
        setAlarmInFirstRun();
    }

    private void setAlarmInFirstRun() {
        if (isFirstRun()) {
            AlarmUtils.setDaily(this);
            setNotFirstRun();
        }
    }

    private void setNotFirstRun() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean(PREFERENCE_FIRST_RUN, false).apply();
    }

    private boolean isFirstRun() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean(PREFERENCE_FIRST_RUN, true);
    }

}
