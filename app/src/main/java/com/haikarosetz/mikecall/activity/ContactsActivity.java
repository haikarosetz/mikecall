package com.haikarosetz.mikecall.activity;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.haikarosetz.mikecall.DataStore.ContactsGetter;
import com.haikarosetz.mikecall.DataStore.ContentStore;
import com.haikarosetz.mikecall.R;
import com.haikarosetz.mikecall.adapter.ContactsAdapter;
import com.haikarosetz.mikecall.pojo.ContactsItem;
import com.haikarosetz.mikecall.pojo.Favorite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private ListView contacts_list;
    private List<ContactsItem> contacts;
    private List<ContactsItem> contactsSearch;
    private Favorite favorite;
    private ContactsAdapter contactsAdapter;
    private ContactsAdapter contactsAdapterSearch;
    private SearchView search;
    private ContentStore favoritesContent;
    private ContactsItem contact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        getSupportActionBar().hide();

        favoritesContent=new ContentStore(getBaseContext());

        search=(SearchView)findViewById(R.id.search_view);

        contacts_list=(ListView)findViewById(R.id.contacts_list);
        favoritesContent=new ContentStore(getBaseContext());

        //contacts list
        contacts= ContactsGetter.getAllContacts(this);
        contactsAdapter=new ContactsAdapter(getBaseContext(),R.layout.contact_item,contacts);
        contactsAdapterSearch=null;
        contacts_list.setAdapter(contactsAdapter);


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                contactsSearch = new ArrayList<ContactsItem>();
                Iterator iterator = contacts.iterator();

                while (iterator.hasNext()) {

                    ContactsItem item = (ContactsItem) iterator.next();
                    String word = newText;


                    if (!word.isEmpty()) {

                        try {


                            Integer d = Integer.parseInt(word);

                            if (item.getPhone().contains(word)) {
                                contactsSearch.add(item);
                                contacts_list=(ListView)findViewById(R.id.contacts_list);
                                contactsAdapterSearch= new ContactsAdapter(getBaseContext(), R.layout.contact_item, contactsSearch);
                                contacts_list.setAdapter(contactsAdapterSearch);

                                contacts_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        contact = contactsSearch.get(position);
                                        favorite = new Favorite();
                                        favorite.setName(contact.getName());
                                        favorite.setPhone(contact.getPhone());

                                        int count = favoritesContent.getAllFavoritesCount(getBaseContext());
                                        if (count == 5) {

                                            Toast.makeText(getBaseContext(), "Favorites can only be five", Toast.LENGTH_SHORT).show();

                                        } else {
                                            favoritesContent.addFavorites(favorite);
                                            finish();
                                        }

                                    }
                                });

                            } else {

                                if (!(iterator.hasNext())) {

                                    contacts_list.setFilterText("No Contacts");

                                }

                            }

                        } catch (Exception ex) {


                            word = Character.toString(word.charAt(0)).toUpperCase().concat(word.substring(1));

                            if (item.getName().contains(word)) {

                                contactsSearch.add(item);
                                contacts_list=(ListView)findViewById(R.id.contacts_list);
                                contactsAdapterSearch = new ContactsAdapter(getBaseContext(), R.layout.contact_item, contactsSearch);
                                contacts_list.setAdapter(contactsAdapterSearch);

                                contacts_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        contact = contactsSearch.get(position);
                                        favorite = new Favorite();
                                        favorite.setName(contact.getName());
                                        favorite.setPhone(contact.getPhone());

                                        int count = favoritesContent.getAllFavoritesCount(getBaseContext());
                                        if (count == 5) {
                                            Toast.makeText(getBaseContext(), "Favorites can only be five", Toast.LENGTH_SHORT).show();
                                        } else {
                                            favoritesContent.addFavorites(favorite);
                                            finish();
                                        }

                                    }
                                });

                            }


                        }

                    } else {
                        contactsAdapter = new ContactsAdapter(getBaseContext(), R.layout.contact_item, contacts);
                        contacts_list.setAdapter(contactsAdapter);
                    }
                }

                return true;
            }

        });



        //here goes contacts
        contacts_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                contact = contacts.get(position);
                favorite = new Favorite();
                favorite.setName(contact.getName());
                favorite.setPhone(contact.getPhone());

                int count = favoritesContent.getAllFavoritesCount(getBaseContext());
                if (count == 5) {
                    Snackbar.make(view,"Favorites can only be five", Snackbar.LENGTH_SHORT).setActionTextColor(Color.RED).show();
                } else {
                    favoritesContent.addFavorites(favorite);
                    finish();
                }
            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
