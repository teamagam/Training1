package com.teamagam.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class DailyAlarmReceiver extends BroadcastReceiver{

    private final int DAILY_NOTIFICATION_ID = 0;
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        notifyNotification(buildDailyNotification());
    }

    private Notification buildDailyNotification() {
        Notification.Builder notificationBuilder = new Notification.Builder(
                mContext)
                .setTicker(mContext.getString(R.string.notification_daily_text_ticker))
                .setSmallIcon(R.drawable.ic_add_a_photo_black_24dp)
                .setAutoCancel(true)
                .setContentTitle(mContext.getString(R.string.notification_daily_title_content))
                .setContentText(mContext.getString(R.string.notification_daily_text_content))
                .setContentIntent(createDailySelfiePendingIntent());

        return notificationBuilder.build();
    }

    private PendingIntent createDailySelfiePendingIntent() {
        Intent dailySelfieIntent = new Intent(mContext.getApplicationContext(),
                MainActivity.class);
        dailySelfieIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(mContext.getApplicationContext(), 0,
                dailySelfieIntent, 0);
    }

    private void notifyNotification(Notification notification) {
        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(DAILY_NOTIFICATION_ID, notification);
    }

}
