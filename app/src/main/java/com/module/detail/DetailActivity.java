package com.module.detail;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.geeksammao.everydayenergertic.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DetailActivity extends Activity {
    private TextView energyText; // the energy TextView
    private String energyWords;
    private String dateString;
    private Date date;
    private SimpleDateFormat formatter;

    private AssetManager assetManager;
    private SharedPreferences preferences;
    private Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
    }

    private void initView() {
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        date = new Date(System.currentTimeMillis());
        dateString = formatter.format(date);

        Log.e("",dateString);
        preferences = getSharedPreferences("dateData",0);
        energyWords = preferences.getString(dateString,null);

        energyText = (TextView)findViewById(R.id.energy_text);
        assetManager = getAssets();
        tf = Typeface.createFromAsset(assetManager, "new_font.ttf");
        energyText.setTypeface(tf);
        energyText.setText(energyWords);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
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
}
