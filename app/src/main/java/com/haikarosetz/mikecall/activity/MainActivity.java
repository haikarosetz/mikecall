package com.haikarosetz.mikecall.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.haikarosetz.mikecall.R;
import com.haikarosetz.mikecall.fragment.AboutFragment;
import com.haikarosetz.mikecall.fragment.AssistantFragment;
import com.haikarosetz.mikecall.fragment.FavoritesFragment;
import com.haikarosetz.mikecall.fragment.MissedFragment;
import com.haikarosetz.mikecall.fragment.SmsPlanFragment;
import com.haikarosetz.mikecall.fragment.StatusFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FragmentManager fragmentManager;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.MEDIA_CONTENT_CONTROL,
                Manifest.permission.READ_PHONE_STATE
                ,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_SMS};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        Fragment  statusFragment=new StatusFragment();
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container,statusFragment).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_status) {
            fragmentManager.beginTransaction().replace(R.id.container,new StatusFragment()).commit();
            getSupportActionBar().setTitle("Status");

        } else if (id == R.id.nav_assistant) {
            fragmentManager.beginTransaction().replace(R.id.container,new AssistantFragment()).commit();
            getSupportActionBar().setTitle("Assistant");
        } else if (id == R.id.nav_favorites) {
            fragmentManager.beginTransaction().replace(R.id.container,new FavoritesFragment()).commit();
            getSupportActionBar().setTitle("Favorites");
        } else if (id == R.id.nav_smsplan) {
            fragmentManager.beginTransaction().replace(R.id.container,new SmsPlanFragment()).commit();
            getSupportActionBar().setTitle("SmsPlan");
        } else if (id == R.id.nav_missed) {
            fragmentManager.beginTransaction().replace(R.id.container,new MissedFragment()).commit();
            getSupportActionBar().setTitle("MissedCalls");
        }else if(id ==R.id.nav_about){
            fragmentManager.beginTransaction().replace(R.id.container,new AboutFragment()).commit();
            getSupportActionBar().setTitle("About");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
