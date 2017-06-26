package com.haikarosetz.mikecall.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.haikarosetz.mikecall.DataStore.ContentStore;
import com.haikarosetz.mikecall.R;

public class StatusFragment extends Fragment {

    private Button clear;
    private Toolbar toolbar;
    private Button activate;
    protected TextView registeredExcuses;
    private ContentStore contents;
    private EditText status_value;
    private String status;
    private String excuseStatus;


    public StatusFragment() {
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
        View view=inflater.inflate(R.layout.fragment_status, container, false);

        clear=(Button) view.findViewById(R.id.clear);
        activate=(Button) view.findViewById(R.id.activate);
        status_value=(EditText) view.findViewById(R.id.status_value);



        registeredExcuses=(TextView)view.findViewById(R.id.registered);
        contents = new ContentStore(getContext());
        status=contents.getStatus(getContext());
        final String excuse=status;

        if(excuse.equalsIgnoreCase("availlable")){
            registeredExcuses.setText("You dont have any excuses for not attending phone calls now . " +
                    "you are now accessed by anyone");
        }else{
            registeredExcuses.setText(excuse);
        }

        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateButton(v);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearButton(v);
            }
        });


        return view;
    }


    public void activateButton(View views){

        excuseStatus=status_value.getText().toString();

        final View view=views;
        if(!(excuseStatus.equalsIgnoreCase("") || excuseStatus.length()<5)){

            contents = new ContentStore(getContext());
            contents.setStatus(excuseStatus);
            status_value.setText("");

            String stats = new ContentStore(getContext()).getStatus(getContext());
            Snackbar.make(view, "Excuse registered", Snackbar.LENGTH_LONG).show();

            registeredExcuses.setText(excuseStatus);

            //startActivity(new Intent(getContext(),AssistantActivity.class));

        }else{

            Snackbar.make(view, "Excuse too short", Snackbar.LENGTH_LONG).show();


        }
    }

    public void clearButton(View views){

        final View view=views;
        final String excuse=status;

        if (excuse.equalsIgnoreCase("availlable")) {

            Snackbar.make(view, "you dont have any Excuse", Snackbar.LENGTH_LONG).show();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("Clear Excuse");
            alert.setMessage("This option will switch to Normal mode mode");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    boolean check = new ContentStore(getContext()).getVibration(getContext());
                    if (check) {

                        Vibrator vibrate = (Vibrator) getContext().getSystemService(getContext().VIBRATOR_SERVICE);
                        vibrate.vibrate(500);
                    }

                    new ContentStore(getContext()).deleteAllStatus();
                    AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    // audioManager.adjustVolume(AudioManager.ADJUST_UNMUTE,0);
                    String status = new ContentStore(getContext()).getStatus(getContext());
                    Snackbar.make(view,status, Snackbar.LENGTH_LONG).show();
                    registeredExcuses.setText("You dont have any excuses for not attending phone calls now . " +
                            "you are now accessed by anyone");
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

}
