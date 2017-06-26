package com.haikarosetz.mikecall.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.haikarosetz.mikecall.R;
import com.haikarosetz.mikecall.pojo.Favorite;

import java.util.List;


public class FavoritesItemAdapter extends ArrayAdapter<Favorite> {

    private Context context;
    private int resources;
    private List<Favorite> list;

    public FavoritesItemAdapter(Context context, int resources, List<Favorite> objects){

        super(context,resources,objects);
        this.context=context;
        this.resources=resources;
        this.list=objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Favorite item=list.get(position);
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view=(View)inflater.inflate(resources, null);

        TextView phone=(TextView)view.findViewById(R.id.phone);
        TextView name=(TextView)view.findViewById(R.id.name);

        phone.setText(item.getPhone());
        name.setText(item.getName());

        return view;
    }
}
