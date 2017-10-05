package com.localfriend;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.localfriend.adapter.DrawerAdapter;
import com.localfriend.application.MyApp;
import com.localfriend.fragments.CartFragment;
import com.localfriend.fragments.CenteredTextFragment;
import com.localfriend.fragments.FragmentDrawer;
import com.localfriend.fragments.HomeFragment;
import com.localfriend.fragments.TiffinFragment;
import com.localfriend.utils.DrawerItem;
import com.localfriend.utils.SimpleItem;
import com.localfriend.utils.SpaceItem;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.sql.Types.NULL;

public class MainActivity extends CustomActivity implements DrawerAdapter.OnItemSelectedListener {
    private static final int POS_DASHBOARD = 0;
    private static final int POS_ACCOUNT = 1;
    private static final int POS_MESSAGES = 2;
    private static final int POS_CART = 3;
    private static final int POS_LOGOUT = 5;
    private String[] screenTitles;
    private Drawable[] screenIcons;
    private Toolbar toolbar;
    private SlidingRootNav slidingRootNav;

    //private TextView tv_home, tv_tiffin, tv_cart, tv_more;
    private ImageView img_home, img_tiffin, img_cart, img_more;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    // private ImageButton navBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Local Friend");
        actionBar.setTitle("");

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_ACCOUNT),
                createItemFor(POS_MESSAGES),
                createItemFor(POS_CART),
                new SpaceItem(48),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);


        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.service_container, new HomeFragment()).commit();

        setupUiElements();


    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_LOGOUT) {
            finish();
        }
        slidingRootNav.closeMenu();
        Fragment selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
        showFragment(selectedScreen);
    }

    private void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.black))
                .withTextTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    private Context getContext() {
        return MainActivity.this;
    }

    private void setupUiElements() {
        /*tv_home = (TextView) findViewById(R.id.tv_home);
        tv_tiffin = (TextView) findViewById(R.id.tv_tiffin);
        tv_cart = (TextView) findViewById(R.id.tv_cart);
        tv_more = (TextView) findViewById(R.id.tv_more);*/
        // navBtn = (ImageButton) findViewById(R.id.nav_drawer_btn);

        img_home = (ImageView) findViewById(R.id.img_home);
        img_tiffin = (ImageView) findViewById(R.id.img_tiffin);
        img_cart = (ImageView) findViewById(R.id.img_cart);
        img_more = (ImageView) findViewById(R.id.img_more);
        setClick(R.id.rl_tab_1);
        setClick(R.id.rl_tab_2);
        setClick(R.id.rl_tab_3);
        setClick(R.id.rl_tab_4);
        // setClick(R.id.nav_drawer_btn);

        /*tv_home.setSelected(true);
        tv_home.setTextColor(Color.parseColor("#166DB6"));*/

    }

  /*  @Override
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
    }*/


    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.rl_tab_1) {

           /* tv_home.setSelected(true);
            tv_tiffin.setSelected(false);
            tv_cart.setSelected(false);
            tv_more.setSelected(false);*/

            img_home.setSelected(true);
            img_tiffin.setSelected(false);
            img_cart.setSelected(false);
            img_more.setSelected(false);

            img_home.setImageResource(R.drawable.ic_home_active);
            img_tiffin.setImageResource(R.drawable.ic_tifin);
            img_cart.setImageResource(R.drawable.ic_cart);
            img_more.setImageResource(R.drawable.ic_more);

            toolbar.setBackgroundResource(NULL);

            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.service_container, new HomeFragment()).commit();

      /*      tv_home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.search_active, 0, 0);
            tv_cart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifications_inactive, 0, 0);
            tv_tiffin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.services_inactive, 0, 0);
            tv_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.account_inactive, 0, 0);*/


           /* tv_home.setTextColor(Color.parseColor("#166DB6"));
            tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
            tv_cart.setTextColor(Color.parseColor("#888F8C"));
            tv_more.setTextColor(Color.parseColor("#888F8C"));*/
        } else if (v.getId() == R.id.rl_tab_2) {
            /*tv_home.setSelected(false);
            tv_tiffin.setSelected(true);
            tv_cart.setSelected(false);
            tv_more.setSelected(false);*/

            img_home.setSelected(false);
            img_tiffin.setSelected(true);
            img_cart.setSelected(false);
            img_more.setSelected(false);

            img_home.setImageResource(R.drawable.ic_home);
            img_tiffin.setImageResource(R.drawable.ic_tiffin_active);
            img_cart.setImageResource(R.drawable.ic_cart);
            img_more.setImageResource(R.drawable.ic_more);

            toolbar.setBackgroundResource(R.drawable.main_gradient_bg);

            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.service_container, new TiffinFragment()).commit();
          /*  tv_home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.search_inactive, 0, 0);
            tv_cart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifications_inactive, 0, 0);
            tv_tiffin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.services_active, 0, 0);
            tv_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.account_inactive, 0, 0);*/

          /*  tv_home.setTextColor(Color.parseColor("#888F8C"));
            tv_tiffin.setTextColor(Color.parseColor("#166DB6"));
            tv_cart.setTextColor(Color.parseColor("#888F8C"));
            tv_more.setTextColor(Color.parseColor("#888F8C"));
*/
            //startActivity(new Intent(MainActivity.this, AllServicesActivity.class));

        } else if (v.getId() == R.id.rl_tab_3) {
            /*tv_home.setSelected(false);
            tv_tiffin.setSelected(false);
            tv_cart.setSelected(true);
            tv_more.setSelected(false);*/
            img_home.setSelected(false);
            img_tiffin.setSelected(false);
            img_cart.setSelected(true);
            img_more.setSelected(false);

            img_home.setImageResource(R.drawable.ic_home);
            img_tiffin.setImageResource(R.drawable.ic_tifin);
            img_cart.setImageResource(R.drawable.ic_cart_active);
            img_more.setImageResource(R.drawable.ic_more);

            toolbar.setBackgroundResource(R.drawable.main_gradient_bg);

            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.service_container, new CartFragment()).commit();
           /* tv_home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.search_inactive, 0, 0);
            tv_cart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifications_active, 0, 0);
            tv_tiffin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.services_inactive, 0, 0);
            tv_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.account_inactive, 0, 0);*/


         /*   tv_home.setTextColor(Color.parseColor("#888F8C"));
            tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
            tv_cart.setTextColor(Color.parseColor("#166DB6"));
            tv_more.setTextColor(Color.parseColor("#888F8C"));
//            openImage();*/
            //  startActivity(new Intent(MainActivity.this, NotificationActivity.class));
        } else if (v.getId() == R.id.rl_tab_4) {
            /*tv_home.setSelected(false);
            tv_tiffin.setSelected(false);
            tv_cart.setSelected(false);
            tv_more.setSelected(true);
*/
            img_home.setSelected(false);
            img_tiffin.setSelected(false);
            img_cart.setSelected(false);
            img_more.setSelected(true);

            img_home.setImageResource(R.drawable.ic_home);
            img_tiffin.setImageResource(R.drawable.ic_tifin);
            img_cart.setImageResource(R.drawable.ic_cart);
            img_more.setImageResource(R.drawable.ic_more_active);

           /* tv_home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.search_inactive, 0, 0);
            tv_cart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifications_inactive, 0, 0);
            tv_tiffin.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.services_inactive, 0, 0);
            tv_more.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.account_active, 0, 0);*/


           /* tv_home.setTextColor(Color.parseColor("#888F8C"));
            tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
            tv_cart.setTextColor(Color.parseColor("#888F8C"));
            tv_more.setTextColor(Color.parseColor("#166DB6"));*/
            //  startActivity(new Intent(MainActivity.this, AddMoneyActivity.class));

            // startActivity(new Intent(MainActivity.this, ScheduleServiceActivity.class));

        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
