package com.example.geeksammao.everydayenergertic.writenote;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.geeksammao.everydayenergertic.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WritingActivity extends Activity implements View.OnClickListener {
    private InputFilter[] filters;
    private EditText writingNoteEditText;
    private Button finishButton;

    private String todayEnergy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        initView();
    }

    private void initView() {
        finishButton = (Button)findViewById(R.id.finish_button);
        writingNoteEditText = (EditText)findViewById(R.id.writing_editText);

        //set the most words to input to 30
        filters = new InputFilter[]{new InputFilter.LengthFilter(30)};
        writingNoteEditText.setFilters(filters);

        finishButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.writing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences preferences = getSharedPreferences("dateData", 0);
        SharedPreferences.Editor editor = preferences.edit();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String dateString = formatter.format(date);

        todayEnergy = writingNoteEditText.getText().toString();

        editor.putString(dateString, todayEnergy);
        editor.commit();

        Log.e("",todayEnergy);
        finish();
    }
}
