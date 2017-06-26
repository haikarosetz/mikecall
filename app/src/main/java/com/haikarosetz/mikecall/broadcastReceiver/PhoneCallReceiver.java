package com.haikarosetz.mikecall.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.haikarosetz.mikecall.DataStore.ContentStore;
import com.haikarosetz.mikecall.pojo.Assistant;
import com.haikarosetz.mikecall.pojo.Missed;

import java.util.ArrayList;

public class PhoneCallReceiver extends BroadcastReceiver {


    private  String status;

    private boolean once=true;
    public  boolean getOnce(){
        return this.once;
    }

    public  void setOnce(){
        this.once=false;
    }

    public static void sendSms(String receiver, String message,Context context){

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(receiver, null, message, null, null);
        Toast.makeText(context,"done",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        TelephonyManager telephonyManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new PhoneStateListener() {


            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    Log.e("the phone call","incoming");
                    ContentStore stores = new ContentStore(context);
                    Assistant myassistant = stores.getAssistant(context);
                    ArrayList<String> favorites = new ContentStore(context).getFavoritesArrayList(context);

                    String name=" ";

                    Log.e("the favs",Integer.toString(favorites.size()));

                    for(String fav:favorites){
                        name+=fav;
                        name+=" ";
                    }
                    Log.e("fav",name);

                    ArrayList<String> called = stores.getCalled(context);
                    boolean repeat = true;
                    status = stores.getStatus(context);
                    String info = " incase of emergence call " + myassistant.getPhone();
                    Log.e("contained number",incomingNumber);
                    if (!(status.equalsIgnoreCase("availlable"))) {
                        if (!(favorites.contains(incomingNumber))) {

                            if (!(called.contains(incomingNumber))) {
                                while (getOnce()) {
                                    ArrayList<String> favo = new ContentStore(context).getFavoritesArrayList(context);
                                    if (!favo.contains(incomingNumber)) {
                                        //stay silent without soound or notifications
                                        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                                        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                        audioManager.adjustVolume(AudioManager.ADJUST_MUTE,0);
                                        Missed item = new Missed();
                                        item.setName(incomingNumber);
                                        item.setPhone(incomingNumber);
                                        stores.addReminders(item);
                                        SmsManager smsManager = SmsManager.getDefault();
                                        String message = status + info;
                                        smsManager.sendTextMessage(incomingNumber, null, message, null, null);
                                        stores.addCounterItem();
                                        setOnce();
                                    } else {
                                        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                                        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                        audioManager.adjustVolume(AudioManager.ADJUST_MUTE,0);
                                    }
                                }
                            } else {
                                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                audioManager.adjustVolume(AudioManager.ADJUST_MUTE,0);
                            }
                        } else {
                            Log.e("Favorite is calling",incomingNumber);
                            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                            audioManager.adjustVolume(AudioManager.ADJUST_MUTE,0);
                            long[] pattern = {0, 1000, 100, 3000, 100, 1000, 100, 1000, 300, 100, 1000};
                            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(pattern, -1);
                        }
                    }
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }
}
