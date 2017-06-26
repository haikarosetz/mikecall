package com.haikarosetz.mikecall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.haikarosetz.mikecall.R;
import com.haikarosetz.mikecall.activity.MainActivity;
import com.haikarosetz.mikecall.pojo.ContactsItem;

import java.util.List;


public class ContactsAdapter extends ArrayAdapter<ContactsItem> {


    private Context context;
    private int resource;
    private List<ContactsItem> contacts;

    public ContactsAdapter(Context context, int resource, List<ContactsItem> objects){

        super(context,resource,objects);
        this.context=context;
        this.resource=resource;
        this.contacts=objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ContactsItem item=contacts.get(position);

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(resource,null);

        TextView name=(TextView)view.findViewById(R.id.contact_name);
        TextView phone=(TextView)view.findViewById(R.id.contact_phone);

        phone.setText(item.getPhone());
        name.setText(item.getName());


        return view;
    }
}
