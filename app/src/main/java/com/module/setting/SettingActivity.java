package com.module.setting;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.adapter.MyAdapter;
import com.receiver.EnergyNotifyReceiver;
import com.receiver.NoteWritingReceiver;

import java.util.Calendar;

public class SettingActivity extends Activity implements AdapterView.OnItemClickListener {
    private ListView settingList;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyAdapter adapter = new MyAdapter(this);

        settingList = new ListView(this);
        settingList.setDividerHeight(5);
        settingList.setAdapter(adapter);

        setContentView(settingList);
        settingList.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0){
            // create a AlarmManager to invoke the broadcastreceiver
            final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            // create a calendar to remember the time
            Calendar currentTime = Calendar.getInstance();

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    // intent for launching the energy notify broadcastreceiver
                    Intent intent = new Intent(SettingActivity.this, EnergyNotifyReceiver.class);
                    // pendingintent for wrap the intent
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(SettingActivity.this, 0, intent, 0);

                    Calendar calendar = Calendar.getInstance();
                    // set the calendar due to user setting the time
                    calendar.setTimeInMillis(System.currentTimeMillis() + 24*60*60*1000);
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);

                    // invoke the alarm to show the notification
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);

                    Toast.makeText(SettingActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
                }
            }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), true);
            timePickerDialog.show();

        }

        else if (position == 1){
            // create a AlarmManager to invoke the broadcastreceiver
            final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            // create a calendar to remember the time
            Calendar currentTime = Calendar.getInstance();

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    // intent for launching the energy notify broadcastreceiver
                    Intent intent = new Intent(SettingActivity.this, NoteWritingReceiver.class);
                    // pendingintent for wrap the intent
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(SettingActivity.this, 0, intent, 0);

                    Calendar calendar = Calendar.getInstance();
                    // set the calendar due to user setting the time
                    calendar.setTimeInMillis(System.currentTimeMillis() + 24*60*60*1000);
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);

                    // invoke the alarm to show the notification
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);

                    Toast.makeText(SettingActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
                }
            }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        }
    }
}
