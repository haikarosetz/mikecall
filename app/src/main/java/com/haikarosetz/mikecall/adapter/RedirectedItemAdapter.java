package com.haikarosetz.mikecall.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.haikarosetz.mikecall.R;
import com.haikarosetz.mikecall.pojo.Missed;

import java.util.List;


public class RedirectedItemAdapter extends ArrayAdapter<Missed> {

    private Context context;
    private int resources;
    private List<Missed> list;

    public RedirectedItemAdapter(Context context, int resources, List<Missed> objects){

        super(context,resources,objects);
        this.context=context;
        this.resources=resources;
        this.list=objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Missed item=list.get(position);
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view=(View)inflater.inflate(resources, null);

        TextView phone=(TextView)view.findViewById(R.id.reminder);
        phone.setText(item.getPhone());

        return view;
    }
}
