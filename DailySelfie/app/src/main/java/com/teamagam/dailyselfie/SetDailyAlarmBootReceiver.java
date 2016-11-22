package com.teamagam.dailyselfie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class SetDailyAlarmBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) ) {
            AlarmUtils.setDaily(context);
        }
    }
}
