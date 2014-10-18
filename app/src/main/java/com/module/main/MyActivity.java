package com.module.main;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.geeksammao.everydayenergertic.R;
import com.module.setting.SettingActivity;
import com.receiver.EnergyNotifyReceiver;
import com.module.writenote.WritingActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.geeksammao.everydayenergertic.R.drawable.icon;


public class MyActivity extends Activity implements View.OnClickListener {
    private boolean isFirstTime;  // whether it's the first time to launch the app
    private Calendar currentTime;
    private AlarmManager alarmManager;

    private Button inputButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // create SharedPreferrence to store user data
        createSharedPreferrence();

        initView();
        // if it's the first time to use the app
        if (isFirstTime) {
            // show the guide dialog
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this).
                    setTitle("使用指南")
                    .setIcon(icon)
                    .setMessage(R.string.use_guide);
            setPositiveButton(mBuilder).
                    create().
                    show();
        }

    }

    private void initView() {
        inputButton = (Button)findViewById(R.id.input_button);
        inputButton.setOnClickListener(this);
    }

    // create sharedPreferrence to store the using time
    private void createSharedPreferrence() {
        SharedPreferences preferences = getSharedPreferences("isFirstTime", MODE_PRIVATE);
        isFirstTime = preferences.getBoolean("isFirstTime", true);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFirstTime", false);
        editor.commit();
    }

    // judge whether today user has written down the words
    public static boolean isTodayWritten(Context context){
        SharedPreferences preferrences = context.getSharedPreferences("dateData", 0);

        // these following code should be packed later
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis() + 24*60*60*1000);
        String dateString = formatter.format(date);

        // if today words is null,means can still write today
        String todayWords = preferrences.getString(dateString,null);

        if (todayWords == null){
            return false;
        }
        else {
            return true;
        }
    }

    private AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder) {
        return builder.setPositiveButton("选择", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showTimePickDialog();
            }
        });
    }

    private void showTimePickDialog() {
        // create a AlarmManager to invoke the broadcastreceiver
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // create a calendar to remember the time
        currentTime = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // intent for launching the energy notify broadcastreceiver
                Intent intent = new Intent(MyActivity.this, EnergyNotifyReceiver.class);
                // pendingintent for wrap the intent
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MyActivity.this, 0, intent, 0);

                Calendar calendar = Calendar.getInstance();
                // set the calendar due to user setting the time
                calendar.setTimeInMillis(System.currentTimeMillis() + 24*60*60*1000);
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                // invoke the alarm to show the notification
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);
            }
        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(MyActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (isTodayWritten(this)){
            Toast.makeText(this,"你今天已经记录过正能量了哦，请明天再记录吧~",Toast.LENGTH_LONG).show();
        }
        else {
//            inputButton.setBackgroundColor(Color.GRAY);
            Intent intent = new Intent(this, WritingActivity.class);
            startActivity(intent);
        }
    }
}