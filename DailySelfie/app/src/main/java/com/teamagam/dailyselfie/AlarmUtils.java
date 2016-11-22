package com.teamagam.dailyselfie;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Calendar;

class AlarmUtils {
    static void setDaily(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingAlarmIntent = getPendingAlarmIntent(context);

//            Calendar calendar = getTiming();
//            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingAlarmIntent);

        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 10000, 60000, pendingAlarmIntent);
    }

    private static PendingIntent getPendingAlarmIntent(Context context) {
        Intent alarmIntent = new Intent(context, DailyAlarmReceiver.class);
        return PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
    }

    private static Calendar getTiming() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        return calendar;
    }

}
