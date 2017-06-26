package com.haikarosetz.mikecall.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
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
import com.haikarosetz.mikecall.adapter.FavoritesItemAdapter;
import com.haikarosetz.mikecall.pojo.Assistant;
import com.haikarosetz.mikecall.pojo.Favorite;

import java.util.List;


public class AssistantFragment extends Fragment {

    private String phone=null;
    private TextView textView;
    private TextView emptyView;
    private ContentStore contents;
    private ListView myfavoritesList;
    private List<Favorite> myfavorites;
    private AlertDialog.Builder alert;
    private  Favorite favorite;
    private View view;

    public AssistantFragment() {
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
        view = inflater.inflate(R.layout.fragment_assistant, container, false);

        contents = new ContentStore(getContext());
        emptyView=(TextView)view.findViewById(R.id.empty_view);
        emptyView.setText("Please set Favorites first");

        myfavorites = contents.getFavorites(getContext());
        textView = (TextView)view.findViewById(R.id.assistant_number);
        myfavoritesList = (ListView)view.findViewById(R.id.myfavorites_new_list);
        myfavoritesList.setEmptyView(emptyView);

        //Loading necessary data
        Loader();
        favoritesLoader();

        myfavoritesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                favorite=new Favorite();
                favorite=myfavorites.get(position);
                contents=new ContentStore(getContext());

                Snackbar.make(view, "Assign" + favorite.getName() + "?", Snackbar.LENGTH_LONG)
                        .setAction("YES", new View.OnClickListener() {

                            public void onClick(View view) {
                                boolean check = new ContentStore(getContext()).getVibration(getContext());

                                if(check){
                                    Vibrator vibrate=(Vibrator)getContext().getSystemService(getContext().VIBRATOR_SERVICE);
                                    vibrate.vibrate(500);
                                }
                                contents.addAssistant(new Assistant("assistant",favorite.getPhone()));
                                textView.setText(favorite.getPhone());

                            }

                        }).setActionTextColor(Color.RED)
                        .show();

            }
        });

        return view;
    }

    public void Loader(){

        contents=new ContentStore(getContext());
        Assistant assistant=contents.getAssistant(getContext());
        textView.setText(assistant.getPhone());
        TextView improved=(TextView)view.findViewById(R.id.improved);
        improved.setText("is your assistant");
        myfavoritesList.setEmptyView(emptyView);
    }

    public void favoritesLoader(){

        myfavorites=contents.getFavorites(getContext());

        FavoritesItemAdapter adapter=new FavoritesItemAdapter(getContext(),R.layout.assistant_item,myfavorites);

        myfavoritesList.setAdapter(adapter);

    }


}
