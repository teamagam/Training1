package com.teamagam.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class DailyAlarmReceiver extends BroadcastReceiver{

    private final int DAILY_NOTIFICATION_ID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        notifyDailyNotification(context);
    }

    private void notifyDailyNotification(Context context) {
        Notification notification = buildDailyNotification(context);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(DAILY_NOTIFICATION_ID, notification);
    }

    private Notification buildDailyNotification(Context context) {
        Notification.Builder notificationBuilder = new Notification.Builder(
                context)
                .setTicker(context.getString(R.string.notification_daily_text_ticker))
                .setSmallIcon(R.drawable.ic_add_a_photo_black_24dp)
                .setAutoCancel(true)
                .setContentTitle(context.getString(R.string.notification_daily_title_content))
                .setContentText(context.getString(R.string.notification_daily_text_content))
                .setContentIntent(createDailySelfiePendingIntent(context));

        return notificationBuilder.build();
    }

    private PendingIntent createDailySelfiePendingIntent(Context context) {
        Intent dailySelfieIntent = new Intent(context.getApplicationContext(),
                MainActivity.class);
        return PendingIntent.getActivity(context.getApplicationContext(), DAILY_NOTIFICATION_ID,
                dailySelfieIntent, 0);
    }

}
