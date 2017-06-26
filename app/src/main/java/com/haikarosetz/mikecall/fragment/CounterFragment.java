package com.haikarosetz.mikecall.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.haikarosetz.mikecall.DataStore.ContentStore;
import com.haikarosetz.mikecall.R;

public class CounterFragment extends Fragment {

    private Button button;
    private TextView counter;

    public CounterFragment() {
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
        View view= inflater.inflate(R.layout.fragment_counter, container, false);

        counter=(TextView)view.findViewById(R.id.counter);
        button=(Button)view.findViewById(R.id.reset);
        updates(counter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTimers();
            }
        });

        return view;
    }


    public void updates(TextView counter){
        ContentStore contentStore=new ContentStore(getContext());
        int counts=contentStore.getCounterCounts();

        if(counts==0){
            String text="0000";
            counter.setText(text);
        }else if(counts<10 ){
            String text="000"+Integer.toString(counts);
            counter.setText(text);
        }else if(counts<100){
            String text="00"+Integer.toString(counts);
            counter.setText(text);
        }else if(counts<1000) {
            String text="0"+Integer.toString(counts);
            counter.setText(text);
        }else if(counts>1000 || counts == 1000){
            String text=Integer.toString(counts);
            counter.setText(text);
        }
    }


    public void clearTimers(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Clearing SMS counter");
        alert.setMessage("This option will clear counter");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                boolean check = new ContentStore(getContext()).getVibration(getContext());
                if (check) {

                    Vibrator vibrate = (Vibrator) getContext().getSystemService(getContext().VIBRATOR_SERVICE);
                    vibrate.vibrate(500);
                }

                new ContentStore(getContext()).removeCounterItem();
                updates(counter);
                Toast.makeText(getContext(), "Counter is cleared", Toast.LENGTH_SHORT).show();

            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialogs = alert.create();
        dialogs.show();

    }


}
