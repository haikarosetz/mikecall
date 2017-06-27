package com.haikarosetz.mikecall.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.haikarosetz.mikecall.DataStore.ContentStore;
import com.haikarosetz.mikecall.R;
import com.haikarosetz.mikecall.activity.ContactsActivity;
import com.haikarosetz.mikecall.adapter.FavoritesItemAdapter;
import com.haikarosetz.mikecall.pojo.Colors;
import com.haikarosetz.mikecall.pojo.Favorite;

import java.util.List;


public class FavoritesFragment extends Fragment {

    ContentStore favoritesContent;

    private ListView favorites_list;
    private List<Favorite> favorites;
    private TextView emptyView;
    private FavoritesItemAdapter favoritesAdapter;
    private android.support.design.widget.FloatingActionButton adder;
    private Intent intent;

    public FavoritesFragment() {
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
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        favoritesContent=new ContentStore(getContext());
        emptyView=(TextView)view.findViewById(R.id.empty_view);
        emptyView.setText("Click + sign to add favorites");
        favorites_list =(ListView)view.findViewById(R.id.favorites_list);
        favorites=favoritesContent.getFavorites(getContext());
        intent=new Intent(getContext(),ContactsActivity.class);
        favorites_list.setEmptyView(emptyView);



        updateList();


        favorites_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Favorite item = favorites.get(position);

                View.OnClickListener aclist = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        favoritesContent.removeFavorites(item);
                        updateList();
                    }
                };

                Snackbar.make(view, "Remove " + item.getName() + " as favorite?", Snackbar.LENGTH_LONG)
                        .setAction("DELETE", aclist).setActionTextColor(Color.parseColor(Colors.danger))
                        .show();
            }
        });

        /*adder=(android.support.design.widget.FloatingActionButton)view.findViewById(R.id.fab_fav_add);
        adder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });*/


        return view;
    }

    public void updateList() {

        favoritesContent = new ContentStore(getContext());
        favorites = favoritesContent.getFavorites(getContext());
        //favorites list
        favorites_list.setEmptyView(emptyView);
        favoritesAdapter = new FavoritesItemAdapter(getContext(), R.layout.favorite_item, favorites);
        favorites_list.setAdapter(favoritesAdapter);

    }

}
