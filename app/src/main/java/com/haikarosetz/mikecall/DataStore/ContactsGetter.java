package com.haikarosetz.mikecall.DataStore;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;


import com.haikarosetz.mikecall.pojo.ContactsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ContactsGetter {


    public static List<ContactsItem> getAllContacts(Context context){

        TreeMap<String,String> contacts=new TreeMap<>();
        List<ContactsItem> contactsItems=new ArrayList<>();


        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor cursor =context.getContentResolver().query(uri, projection, null, null, null);

        int indexName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        cursor.moveToFirst();
        do {
            String name   = cursor.getString(indexName);
            String phoneNumber = cursor.getString(indexNumber);


            contacts.put(name,phoneNumber);
            // Do work...
        } while (cursor.moveToNext());



        for (Map.Entry<String,String> entry:contacts.entrySet()
             ) {

            String name=entry.getKey();
            String phone=entry.getValue();
            ContactsItem con=new ContactsItem(name,phone);
            contactsItems.add(con);

        }


        cursor.close();
        return  contactsItems;
    }
}
