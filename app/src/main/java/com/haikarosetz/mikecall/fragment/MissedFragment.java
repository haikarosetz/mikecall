package com.haikarosetz.mikecall.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.haikarosetz.mikecall.DataStore.ContentStore;
import com.haikarosetz.mikecall.R;
import com.haikarosetz.mikecall.adapter.RedirectedItemAdapter;
import com.haikarosetz.mikecall.pojo.Missed;

import java.util.List;


public class MissedFragment extends Fragment {

    private android.support.design.widget.FloatingActionButton fab;
    private TextView empty_view;
    private ListView list;
    private List<Missed> reminders;

    public MissedFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_missed, container, false);

        //empty view for empty list view
        empty_view=(TextView)view.findViewById(R.id.empty_view);
        empty_view.setText("No caller has been redirected currently");


        fab=(android.support.design.widget.FloatingActionButton)view.findViewById(R.id.clear_reminder);
        list=(ListView)view.findViewById(R.id.reminder_list);
        list.setEmptyView(empty_view);
        updateScreen();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ContentStore(getContext()).removeAllReminders();
                updateScreen();
            }
        });

        reminders=new ContentStore(getContext()).getAllReminders(getContext());

        return view;
    }

    public void updateScreen(){

        List<Missed> reminders=new ContentStore(getContext()).getAllReminders(getContext());

        RedirectedItemAdapter adapter=new RedirectedItemAdapter(getContext(),R.layout.reminder_item,reminders);
        list.setAdapter(adapter);
    }

}
