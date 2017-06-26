package com.haikarosetz.mikecall.DataStore;

import android.content.Context;
import android.content.SharedPreferences;


import com.haikarosetz.mikecall.pojo.Assistant;
import com.haikarosetz.mikecall.pojo.Excuse;
import com.haikarosetz.mikecall.pojo.Favorite;
import com.haikarosetz.mikecall.pojo.Missed;
import com.haikarosetz.mikecall.pojo.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ContentStore {

    private Context context;

    public ContentStore(Context context){
        this.context=context;
    }

    public List<Favorite> getFavorites(Context context){

        SharedPreferences favorites=context.getSharedPreferences("FAVORITES", 0);
        Map<String,?> myfavorites=favorites.getAll();
        Set<String> keys=myfavorites.keySet();
        List<Favorite> list=new ArrayList<>();

        for (String key:keys
             ) {
            Favorite item=new Favorite();
            item.setName(key);
            item.setPhone((String)myfavorites.get(key));
            list.add(item);
        }

        return  list;
    }


    public List<Missed> getAllReminders(Context context){

        SharedPreferences reminders=context.getSharedPreferences("REMINDER", 0);
        Map<String,?> myreminders=reminders.getAll();
        Set<String> keys=myreminders.keySet();


        List<Missed> list=new ArrayList<>();

        for (String key:keys
                ) {
            Missed item=new Missed();
            item.setName(key);
            item.setPhone((String)myreminders.get(key));
            list.add(item);
        }

        return  list;
    }


    public ArrayList<String> getFavoritesArrayList(Context context){

        SharedPreferences favorites=context.getSharedPreferences("FAVORITES", 0);
        Map<String,?> myfavorites=favorites.getAll();
        Set<String> keys=myfavorites.keySet();


        ArrayList<String> list=new ArrayList<>();

        for (String key:keys
                ) {

            list.add((String)myfavorites.get(key));
        }

        return  list;
    }

    public ArrayList<String> getCalled(Context context){

        SharedPreferences callers=context.getSharedPreferences("REMINDER", 0);
        Map<String,?> myCallers=callers.getAll();
        Set<String> keys=myCallers.keySet();


        ArrayList<String> list=new ArrayList<>();

        for (String key:keys
                ) {

            list.add((String)myCallers.get(key));
        }

        return  list;
    }

    public String getPending(Context context){

        SharedPreferences pendings=context.getSharedPreferences("PENDINGS", 0);
        String pendingText=pendings.getString("value", "Write your excuse here");
        return  pendingText;
    }


    public boolean getVibration(Context context){

        SharedPreferences pendings=context.getSharedPreferences("VIBRATION", 0);
        boolean vibrate=pendings.getBoolean("value", true);
        return  vibrate;
    }



    public String getStatus(Context context){

        String stat=null;
        SharedPreferences status=context.getSharedPreferences("STATUS", 0);
        stat = status.getString("value", "availlable");

        return stat;
    }

    public int getCounterCounts(){

        SharedPreferences counts=context.getSharedPreferences("COUNTER",0);

        int itemsCount=counts.getInt("number",0);

        return  itemsCount;
    }

    public int getAllFavoritesCount(Context context){

        SharedPreferences counts=context.getSharedPreferences("FAVORITES",0);
        Map<String,?> favorites=counts.getAll();
        int favoritesCount=favorites.size();

        return  favoritesCount;
    }




    public Assistant getAssistant(Context context){

        Assistant item=new Assistant();

        SharedPreferences assistants=context.getSharedPreferences("ASSISTANTS", 0);
        String value= assistants.getString("assistant","not set");
        item.setName("assistant");
        item.setPhone(value);

        return  item;
    }



    public Map<String,?> getSmsPlans(Context context){

        SharedPreferences smsPlans=context.getSharedPreferences("SMS", 0);
        Map<String,?> smsPlanAll=smsPlans.getAll();
        return  smsPlanAll;
    }

    ///deleting preference all

    public void deleteAllStatus(){
        SharedPreferences status=context.getSharedPreferences("STATUS", 0);
        SharedPreferences.Editor editor=status.edit();

        editor.clear();
        editor.commit();

    }
   
    public void removeAllSmsPlan(){

        SharedPreferences smsPlans=context.getSharedPreferences("SMS", 0);
        SharedPreferences.Editor editor=smsPlans.edit();

        editor.clear();
        editor.commit();

    }
    public void removeAllAssistant(){

        SharedPreferences assistants=context.getSharedPreferences("ASSISTANTS", 0);
        SharedPreferences.Editor editor=assistants.edit();

        editor.clear();
        editor.commit();
    }
    public void removeAllFavorites(){

        SharedPreferences favorites=context.getSharedPreferences("FAVORITES", 0);
        SharedPreferences.Editor editor=favorites.edit();

        editor.clear();
        editor.commit();

    }

    public void removeAllReminders(){

        SharedPreferences reminders=context.getSharedPreferences("REMINDER", 0);
        SharedPreferences.Editor editor=reminders.edit();

        editor.clear();
        editor.commit();

    }


    //removing single item

    public void removeStatus(Excuse item){
        SharedPreferences status=context.getSharedPreferences("STATUS", 0);
        SharedPreferences.Editor editor=status.edit();

       // editor.remove(item.getName());
        editor.commit();

    }

    public void removeReminderItem(Missed item){
        SharedPreferences reminders=context.getSharedPreferences("REMINDER", 0);
        SharedPreferences.Editor editor=reminders.edit();

        editor.remove(item.getName());
        editor.commit();

    }

    public void removeSmsPlan(Plan item){

        SharedPreferences smsPlans=context.getSharedPreferences("SMS", 0);
        SharedPreferences.Editor editor=smsPlans.edit();

        //editor.remove(item.);
        editor.commit();

    }

    public void removeVibrate(Context context){
        SharedPreferences status=context.getSharedPreferences("VIBRATION", 0);
        SharedPreferences.Editor editor=status.edit();

        editor.putBoolean("value", false);
        editor.commit();

    }
    public void removeAssistant(Assistant item){

        SharedPreferences assistants=context.getSharedPreferences("ASSISTANTS", 0);
        SharedPreferences.Editor editor=assistants.edit();

        editor.remove(item.getName());
        editor.commit();
    }
    public void removeFavorites(Favorite item){

        SharedPreferences favorites=context.getSharedPreferences("FAVORITES", 0);
        SharedPreferences.Editor editor=favorites.edit();

        editor.remove(item.getName());
        editor.commit();

    }

    public void removeCounterItem(){

        SharedPreferences counters=context.getSharedPreferences("COUNTER", 0);
        SharedPreferences.Editor editor=counters.edit();

        editor.clear();
        editor.commit();

    }



    //adding preferences

    public void setStatus(String state){
        SharedPreferences status=context.getSharedPreferences("STATUS", 0);
        SharedPreferences.Editor editor=status.edit();

        editor.putString("value",state);
        editor.commit();

    }

    public void addVibrate(Context context){
        SharedPreferences status=context.getSharedPreferences("VIBRATION", 0);
        SharedPreferences.Editor editor=status.edit();

        editor.putBoolean("value", true);
        editor.commit();

    }

    public void addSmsPlan(Plan item){

        SharedPreferences smsPlans=context.getSharedPreferences("SMS", 0);
        SharedPreferences.Editor editor=smsPlans.edit();

       // editor.putString(item.getName(), item.getDetail());
        editor.commit();

    }

    public void addPendingText(String text){

        SharedPreferences pendingText=context.getSharedPreferences("PENDINGS", 0);
        SharedPreferences.Editor editor=pendingText.edit();
        editor.putString("value",text);
        editor.commit();

    }

    public void addAssistant(Assistant item){

        SharedPreferences assistants=context.getSharedPreferences("ASSISTANTS", 0);
        SharedPreferences.Editor editor=assistants.edit();

        editor.putString(item.getName(),item.getPhone());
        editor.commit();
    }
    public void addFavorites(Favorite item){
        SharedPreferences favorites=context.getSharedPreferences("FAVORITES", 0);
        SharedPreferences.Editor editor=favorites.edit();
        editor.putString(item.getName(),item.getPhone());
        editor.commit();
    }

    public void addReminders(Missed item){

        SharedPreferences reminders=context.getSharedPreferences("REMINDER", 0);
        SharedPreferences.Editor editor=reminders.edit();

        editor.putString(item.getName(),item.getPhone());
        editor.commit();

    }


    public void addCounterItem(){

        SharedPreferences pendingText=context.getSharedPreferences("COUNTER", 0);
        SharedPreferences.Editor editor=pendingText.edit();
        int numbers=pendingText.getInt("number",0);
        numbers=numbers +1;
        editor.putInt("number",numbers);
        editor.commit();

    }


}
