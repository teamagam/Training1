package com.teamagam.dailyselfie;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Calendar;

class AlarmUtils {
    private static final long INITIAL_DELAY_IN_MILLIS = 10 * 1000;
    private static final long REPEATING_DELAY_IN_MILLIS = 2 * 60 * 1000;
    private static final int CHOSEN_HOUR_FOR_NOTIFICATION = 18;

    static void setDaily(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingAlarmIntent = getPendingAlarmIntent(context);

        // Clarification: notification timing varies in order to test it without
        // needing to wait until the day after

//            Calendar calendar = getTiming();
//            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingAlarmIntent);

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + INITIAL_DELAY_IN_MILLIS, REPEATING_DELAY_IN_MILLIS, pendingAlarmIntent);
    }

    private static PendingIntent getPendingAlarmIntent(Context context) {
        Intent alarmIntent = new Intent(context, DailyAlarmReceiver.class);
        return PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
    }

    private static Calendar getTiming() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, CHOSEN_HOUR_FOR_NOTIFICATION);
        return calendar;
    }

}
