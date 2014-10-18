package com.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.geeksammao.everydayenergertic.R;
import com.module.main.MyActivity;
import com.module.writenote.WritingActivity;

public class NoteWritingReceiver extends BroadcastReceiver {
    public NoteWritingReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (MyActivity.isTodayWritten(context)) {
//            do nothing
        } else {
            // create a notification
            Bitmap iconBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
            long[] vibrate = {0, 500};

            // create a intent to invoke the DetailActivity
            Intent myIntent = new Intent(context, WritingActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder mBuilder = new Notification.Builder(context).
                    setLargeIcon(iconBitmap).
                    setSmallIcon(R.drawable.icon).
                    setContentTitle("每日记录").
                    setContentText("点击记录正能量").
                    setLights(0xff00ff00, 300, 1000).
                    setTicker("每日正能量").
                    setVibrate(vibrate).
                    setContentIntent(pendingIntent);
            Notification everyDayNotification = mBuilder.build();
            everyDayNotification.flags = Notification.FLAG_SHOW_LIGHTS;
            everyDayNotification.flags = Notification.FLAG_AUTO_CANCEL;

            NotificationManager manager = (NotificationManager) context.
                    getSystemService(Context.NOTIFICATION_SERVICE);
            // 0x123 is just temprorarliy used
            manager.notify(0x123, everyDayNotification);
        }
    }
}
