package com.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.module.detail.DetailActivity;
import com.example.geeksammao.everydayenergertic.R;

public class EnergyNotifyReceiver extends BroadcastReceiver {
    public EnergyNotifyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("", "EnergNotify");
        // create a notification
        Bitmap iconBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
        long[] vibrate = {0, 400};

        // create a intent to invoke the DetailActivity
        Intent myIntent = new Intent(context, DetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder mBuilder = new Notification.Builder(context).
                setLargeIcon(iconBitmap).
                setSmallIcon(R.drawable.icon).
                setContentTitle("今日正能量").
                setContentText("    点击查看").
                setLights(0xff00ff00,300,1000).
                setTicker("今日正能量").
                setVibrate(vibrate).
                setContentIntent(pendingIntent);
        Notification everyDayNotification = mBuilder.build();
        everyDayNotification.flags = Notification.FLAG_SHOW_LIGHTS;
        everyDayNotification.flags = Notification.FLAG_AUTO_CANCEL;

        NotificationManager manager = (NotificationManager) context.
                getSystemService(Context.NOTIFICATION_SERVICE);
        // 0x123 is just temprorarliy used
        manager.notify(0x1234, everyDayNotification);

    }
}
