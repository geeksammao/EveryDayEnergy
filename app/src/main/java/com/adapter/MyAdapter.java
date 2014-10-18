package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.geeksammao.everydayenergertic.R;

/**
 * Created by Geeksammao on 10/18/14.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;

    public MyAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        // temporary just 2 setting items
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String [] timeType = {"设置正能量提醒时间","设置记录提醒时间"};

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView =  inflater.inflate(R.layout.setting_layout,parent,false);
        TextView setTime = (TextView)convertView.findViewById(R.id.energy_notigy_time);
        setTime.setText(timeType[position]);
        setTime.setTextSize(20);

        return convertView;
    }
};

