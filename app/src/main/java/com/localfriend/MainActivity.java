package com.localfriend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.localfriend.application.MyApp;
import com.localfriend.fragments.FragmentDrawer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends CustomActivity implements FragmentDrawer.FragmentDrawerListener{
    private FragmentDrawer drawerFragment;
    private DrawerLayout drawer;
    private TextView tv_home, tv_tiffin, tv_cart, tv_more;
    private ImageButton navBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUiElements();




        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        DrawerLayout.LayoutParams lll = (DrawerLayout.LayoutParams) drawer.getLayoutParams();

//        lll.set
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        drawerFragment = (FragmentDrawer) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), null);
        drawerFragment.setDrawerListener(this);



        List<String> dataList = new ArrayList<>();



    }

    private Context getContext() {
        return MainActivity.this;
    }

    private void setupUiElements() {
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_tiffin = (TextView) findViewById(R.id.tv_tiffin);
        tv_cart = (TextView) findViewById(R.id.tv_cart);
        tv_more = (TextView) findViewById(R.id.tv_more);
        navBtn = (ImageButton) findViewById(R.id.nav_drawer_btn);


        setClick(R.id.rl_tab_1);
        setClick(R.id.rl_tab_2);
        setClick(R.id.rl_tab_3);
        setClick(R.id.rl_tab_4);
        setClick(R.id.nav_drawer_btn);

        tv_home.setSelected(true);
        tv_home.setTextColor(Color.parseColor("#166DB6"));

    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

        if (position == 0) {
            //  MyApp.showMassage(getContext(), "will go to my service requests");
           // startActivity(new Intent(getContext(), ScheduleServiceActivity.class));
        } else if (position == 1) {
            MyApp.showMassage(getContext(), "will go to my service requests");
        } else if (position == 2) {
           // startActivity(new Intent(getContext(), ChatActivity.class));
        } else if (position == 3) {
            MyApp.showMassage(getContext(), "will go to History");
        } else if (position == 4) {
            //startActivity(new Intent(MainActivity.this, WalletActivity.class));
        } else if (position == 5) {
            MyApp.showMassage(getContext(), "will go to promos and offers");
        } else if (position == 6) {
            MyApp.showMassage(getContext(), "will go to Favourites");
        } else if (position == 7) {

            String shareBody = "https://play.google.com/store/apps/details?" + "id=com.localfriend";
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Local Friend (Open it in Google Play Store to Download the Application)");

            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

            //  MyApp.showMassage(getContext(), "will invite your friends");
        } else if (position == 8) {
            MyApp.showMassage(getContext(), "will switch to service mode");
        }
    }



    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.rl_tab_1) {

            tv_home.setSelected(true);
            tv_tiffin.setSelected(false);
            tv_cart.setSelected(false);
            tv_more.setSelected(false);

      /*      tv_home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.search_active, 0, 0);
            tv_cart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifications_inactive, 0, 0);
            tv_tiffin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.services_inactive, 0, 0);
            tv_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.account_inactive, 0, 0);*/


            tv_home.setTextColor(Color.parseColor("#166DB6"));
            tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
            tv_cart.setTextColor(Color.parseColor("#888F8C"));
            tv_more.setTextColor(Color.parseColor("#888F8C"));
        } else if (v.getId() == R.id.rl_tab_2) {
            tv_home.setSelected(false);
            tv_tiffin.setSelected(true);
            tv_cart.setSelected(false);
            tv_more.setSelected(false);

          /*  tv_home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.search_inactive, 0, 0);
            tv_cart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifications_inactive, 0, 0);
            tv_tiffin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.services_active, 0, 0);
            tv_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.account_inactive, 0, 0);*/

            tv_home.setTextColor(Color.parseColor("#888F8C"));
            tv_tiffin.setTextColor(Color.parseColor("#166DB6"));
            tv_cart.setTextColor(Color.parseColor("#888F8C"));
            tv_more.setTextColor(Color.parseColor("#888F8C"));

            //startActivity(new Intent(MainActivity.this, AllServicesActivity.class));

        } else if (v.getId() == R.id.rl_tab_3) {
            tv_home.setSelected(false);
            tv_tiffin.setSelected(false);
            tv_cart.setSelected(true);
            tv_more.setSelected(false);

           /* tv_home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.search_inactive, 0, 0);
            tv_cart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifications_active, 0, 0);
            tv_tiffin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.services_inactive, 0, 0);
            tv_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.account_inactive, 0, 0);*/


            tv_home.setTextColor(Color.parseColor("#888F8C"));
            tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
            tv_cart.setTextColor(Color.parseColor("#166DB6"));
            tv_more.setTextColor(Color.parseColor("#888F8C"));
//            openImage();
          //  startActivity(new Intent(MainActivity.this, NotificationActivity.class));
        } else if (v.getId() == R.id.rl_tab_4) {
            tv_home.setSelected(false);
            tv_tiffin.setSelected(false);
            tv_cart.setSelected(false);
            tv_more.setSelected(true);

           /* tv_home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.search_inactive, 0, 0);
            tv_cart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifications_inactive, 0, 0);
            tv_tiffin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.services_inactive, 0, 0);
            tv_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.account_active, 0, 0);*/


            tv_home.setTextColor(Color.parseColor("#888F8C"));
            tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
            tv_cart.setTextColor(Color.parseColor("#888F8C"));
            tv_more.setTextColor(Color.parseColor("#166DB6"));
            //  startActivity(new Intent(MainActivity.this, AddMoneyActivity.class));

            // startActivity(new Intent(MainActivity.this, ScheduleServiceActivity.class));

        }  else if (v == navBtn) {
            drawer.openDrawer(GravityCompat.START);

        }
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
