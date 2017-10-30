package com.localfriend;

import android.animation.Animator;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.localfriend.adapter.CartAdapter;
import com.localfriend.adapter.DrawerAdapter;
import com.localfriend.application.AppConstants;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.fragments.CartFragment;
import com.localfriend.fragments.CenteredTextFragment;
import com.localfriend.fragments.HomeFragment;
import com.localfriend.fragments.MoreFragment;
import com.localfriend.fragments.TiffinFragment;
import com.localfriend.fragments.WishListFragment;
import com.localfriend.model.Cart;
import com.localfriend.model.CategoryDetails;
import com.localfriend.utils.AppConstant;
import com.localfriend.utils.CircleAnimationUtil;
import com.localfriend.utils.DrawerItem;
import com.localfriend.utils.SimpleItem;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.sql.Types.NULL;

public class MainActivity extends CustomActivity implements DrawerAdapter.OnItemSelectedListener, CustomActivity.ResponseCallback {
    private static final int HOME = 0;
    private static final int ORDER = 1;
    private static final int WISH_LIST = 2;
    private static final int ADDRESS = 3;
    private static final int NEED_HELP = 4;
//    private static final int LOGOUT = 5;
    private String[] screenTitles;
    private Drawable[] screenIcons;
    private Toolbar toolbar;
    private SlidingRootNav slidingRootNav;

    private TextView tv_home, tv_tiffin, tv_cart, tv_more;
    public TextView txt_cart_count;
    private ImageView img_home, img_tiffin, img_cart, img_more;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private TextView mTitle;

    // private ImageButton navBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Local Friend");
        actionBar.setTitle("");
        setResponseListener(this);
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
                createItemFor(HOME).setChecked(true),
                createItemFor(ORDER),
                createItemFor(WISH_LIST),
                createItemFor(ADDRESS),
               /* new SpaceItem(48),*/
                createItemFor(NEED_HELP)));
//                createItemFor(LOGOUT)));
        adapter.setListener(this);
        RelativeLayout v = findViewById(R.id.main_drawer_layout);
        // transparent statusbar for marshmallow and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (toolbar
                    != null) {
                toolbar.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            MyApp.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {

            // Set the status bar to dark-semi-transparentish
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // Set paddingTop of toolbar to height of status bar.
            // Fixes statusbar covers toolbar issue
//            RelativeLayout v = (RelativeLayout) findViewById(R.id.toolbar);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
            lp.setMargins(0, MyApp.getApplication().getStatusBarHeight(), 0, -MyApp.getApplication().getStatusBarHeight());
//            v.setPadding(getStatusBarHeight(), getStatusBarHeight(), getStatusBarHeight(), 0);
            MyApp.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        RecyclerView list = findViewById(R.id.list);
//        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.gradient_divider));
//        list.addItemDecoration(itemDecorator);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(HOME);
        toolbar.setBackgroundResource(NULL);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.service_container, new HomeFragment()).commit();

        TextView txt_user_name = findViewById(R.id.txt_user_name);
        txt_user_name.setText(MyApp.getApplication().readUser().getUserInfo().getFullName());
        setupUiElements();

        getCall(AppConstants.BASE_URL + "Category/3", "", 1);

        img_home.setImageResource(R.drawable.ic_home_active);
        tv_home.setTextColor(Color.parseColor("#275B89"));

        int tabNumber = getIntent().getIntExtra(AppConstant.TAB, -1);
        if (tabNumber != 0)
            changeTab(tabNumber);

        getCallWithHeader(AppConstant.BASE_URL + "Cart", 10);
    }

    public void changeTab(int tabNumber) {
        switch (tabNumber) {
            case 1:
                mTitle.setText("Local Friend");
                img_home.setSelected(true);
                img_tiffin.setSelected(false);
                img_cart.setSelected(false);
                img_more.setSelected(false);

                tv_home.setSelected(true);
                tv_tiffin.setSelected(false);
                tv_cart.setSelected(false);
                tv_more.setSelected(false);

                tv_home.setTextColor(Color.parseColor("#275B89"));
                tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
                tv_cart.setTextColor(Color.parseColor("#888F8C"));
                tv_more.setTextColor(Color.parseColor("#888F8C"));

                img_home.setImageResource(R.drawable.ic_home_active);
                img_tiffin.setImageResource(R.drawable.ic_tifin);
                img_cart.setImageResource(R.drawable.ic_cart);
                img_more.setImageResource(R.drawable.ic_more);

                toolbar.setBackgroundResource(NULL);

                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.service_container, new HomeFragment()).commit();

                break;
            case 2:
                mTitle.setText("Tiffin");
                img_home.setSelected(false);
                img_tiffin.setSelected(true);
                img_cart.setSelected(false);
                img_more.setSelected(false);

                tv_home.setSelected(false);
                tv_tiffin.setSelected(true);
                tv_cart.setSelected(false);
                tv_more.setSelected(false);

                tv_home.setTextColor(Color.parseColor("#888F8C"));
                tv_tiffin.setTextColor(Color.parseColor("#275B89"));
                tv_cart.setTextColor(Color.parseColor("#888F8C"));
                tv_more.setTextColor(Color.parseColor("#888F8C"));

                img_home.setImageResource(R.drawable.ic_home);
                img_tiffin.setImageResource(R.drawable.ic_tiffin_active);
                img_cart.setImageResource(R.drawable.ic_cart);
                img_more.setImageResource(R.drawable.ic_more);

                toolbar.setBackgroundResource(R.drawable.main_gradient_bg);

                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.service_container, new TiffinFragment()).commit();

                break;
            case 3:
                mTitle.setText("Cart");
                img_home.setSelected(false);
                img_tiffin.setSelected(false);
                img_cart.setSelected(true);
                img_more.setSelected(false);

                tv_home.setSelected(false);
                tv_tiffin.setSelected(false);
                tv_cart.setSelected(true);
                tv_more.setSelected(false);

                tv_home.setTextColor(Color.parseColor("#888F8C"));
                tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
                tv_cart.setTextColor(Color.parseColor("#275B89"));
                tv_more.setTextColor(Color.parseColor("#888F8C"));

                img_home.setImageResource(R.drawable.ic_home);
                img_tiffin.setImageResource(R.drawable.ic_tifin);
                img_cart.setImageResource(R.drawable.ic_cart_active);
                img_more.setImageResource(R.drawable.ic_more);

                toolbar.setBackgroundResource(R.drawable.main_gradient_bg);

                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.service_container, new CartFragment()).commit();
                break;
            case 4:
                mTitle.setText("More");
                img_home.setSelected(false);
                img_tiffin.setSelected(false);
                img_cart.setSelected(false);
                img_more.setSelected(true);

                tv_home.setSelected(false);
                tv_tiffin.setSelected(false);
                tv_cart.setSelected(false);
                tv_more.setSelected(true);

                tv_home.setTextColor(Color.parseColor("#888F8C"));
                tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
                tv_cart.setTextColor(Color.parseColor("#888F8C"));
                tv_more.setTextColor(Color.parseColor("#275B89"));
                toolbar.setBackgroundResource(R.drawable.main_gradient_bg);
                img_home.setImageResource(R.drawable.ic_home);
                img_tiffin.setImageResource(R.drawable.ic_tifin);
                img_cart.setImageResource(R.drawable.ic_cart);
                img_more.setImageResource(R.drawable.ic_more_active);
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.service_container, new MoreFragment()).commit();
                break;
            default:
                break;

        }

    }

    @Override
    public void onItemSelected(int position) {
        if (position == 0) {
           try{
               mTitle.setText("Local Friend");
               img_home.setSelected(true);
               img_tiffin.setSelected(false);
               img_cart.setSelected(false);
               img_more.setSelected(false);

               tv_home.setSelected(true);
               tv_tiffin.setSelected(false);
               tv_cart.setSelected(false);
               tv_more.setSelected(false);

               tv_home.setTextColor(Color.parseColor("#275B89"));
               tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
               tv_cart.setTextColor(Color.parseColor("#888F8C"));
               tv_more.setTextColor(Color.parseColor("#888F8C"));

               img_home.setImageResource(R.drawable.ic_home_active);
               img_tiffin.setImageResource(R.drawable.ic_tifin);
               img_cart.setImageResource(R.drawable.ic_cart);
               img_more.setImageResource(R.drawable.ic_more);

               toolbar.setBackgroundResource(NULL);

               mFragmentManager = getSupportFragmentManager();
               mFragmentTransaction = mFragmentManager.beginTransaction();
               mFragmentTransaction.replace(R.id.service_container, new HomeFragment()).commit();
           }catch (Exception e){}
        } else if (position == 1) {
            startActivity(new Intent(getContext(), OrderActivity.class));
        } else if (position == 2) {
            mTitle.setText("Wish List");
            img_home.setSelected(false);
            img_tiffin.setSelected(false);
            img_cart.setSelected(false);
            img_more.setSelected(false);

            tv_home.setSelected(false);
            tv_tiffin.setSelected(false);
            tv_cart.setSelected(false);
            tv_more.setSelected(false);

            tv_home.setTextColor(Color.parseColor("#888F8C"));
            tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
            tv_cart.setTextColor(Color.parseColor("#888F8C"));
            tv_more.setTextColor(Color.parseColor("#888F8C"));

            img_home.setImageResource(R.drawable.ic_home);
            img_tiffin.setImageResource(R.drawable.ic_tifin);
            img_cart.setImageResource(R.drawable.ic_cart);
            img_more.setImageResource(R.drawable.ic_more);

            toolbar.setBackgroundResource(R.drawable.main_gradient_bg);

            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.service_container, new WishListFragment()).commit();

        } else if (position == 3) {
            startActivity(new Intent(getContext(), AddressListActivity.class));
        } else if (position == 4) {
            startActivity(new Intent(getContext(), NeedHelp.class));
        } else if (position == 5) {
            MyApp.setStatus(AppConstant.IS_LOGIN, false);
            startActivity(new Intent(getContext(), LoginSignupActivity.class));
            finish();
        }
        slidingRootNav.closeMenu();
        Fragment selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
//        showFragment(selectedScreen);
    }

//    private void showFragment(Fragment fragment) {
//        getFragmentManager().beginTransaction()
//                .replace(R.id.container, fragment)
//                .commit();
//    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.white))
                .withTextTint(color(R.color.white))
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

        img_home = findViewById(R.id.img_home);
        img_tiffin = findViewById(R.id.img_tiffin);
        img_cart = findViewById(R.id.img_cart);
        img_more = findViewById(R.id.img_more);

        tv_home = findViewById(R.id.tv_home);
        tv_tiffin = findViewById(R.id.tv_tiffin);
        tv_cart = findViewById(R.id.tv_cart);
        tv_more = findViewById(R.id.tv_more);
        txt_cart_count = findViewById(R.id.txt_cart_count);
        if (MyApp.getSharedPrefInteger(AppConstant.CART_COUNTER) > 0) {
            txt_cart_count.setVisibility(View.VISIBLE);
            txt_cart_count.setText("" + MyApp.getSharedPrefInteger(AppConstant.CART_COUNTER));
        }

        setClick(R.id.rl_tab_1);
        setClick(R.id.rl_tab_2);
        setClick(R.id.rl_tab_3);
        setClick(R.id.rl_tab_4);
    }


    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.rl_tab_1) {
            mTitle.setText("Local Friend");
            img_home.setSelected(true);
            img_tiffin.setSelected(false);
            img_cart.setSelected(false);
            img_more.setSelected(false);

            tv_home.setSelected(true);
            tv_tiffin.setSelected(false);
            tv_cart.setSelected(false);
            tv_more.setSelected(false);

            tv_home.setTextColor(Color.parseColor("#275B89"));
            tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
            tv_cart.setTextColor(Color.parseColor("#888F8C"));
            tv_more.setTextColor(Color.parseColor("#888F8C"));

            img_home.setImageResource(R.drawable.ic_home_active);
            img_tiffin.setImageResource(R.drawable.ic_tifin);
            img_cart.setImageResource(R.drawable.ic_cart);
            img_more.setImageResource(R.drawable.ic_more);

            toolbar.setBackgroundResource(NULL);

            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.service_container, new HomeFragment()).commit();

        } else if (v.getId() == R.id.rl_tab_2) {
            mTitle.setText("Tiffin");
            img_home.setSelected(false);
            img_tiffin.setSelected(true);
            img_cart.setSelected(false);
            img_more.setSelected(false);

            tv_home.setSelected(false);
            tv_tiffin.setSelected(true);
            tv_cart.setSelected(false);
            tv_more.setSelected(false);

            tv_home.setTextColor(Color.parseColor("#888F8C"));
            tv_tiffin.setTextColor(Color.parseColor("#275B89"));
            tv_cart.setTextColor(Color.parseColor("#888F8C"));
            tv_more.setTextColor(Color.parseColor("#888F8C"));

            img_home.setImageResource(R.drawable.ic_home);
            img_tiffin.setImageResource(R.drawable.ic_tiffin_active);
            img_cart.setImageResource(R.drawable.ic_cart);
            img_more.setImageResource(R.drawable.ic_more);

            toolbar.setBackgroundResource(R.drawable.main_gradient_bg);

            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.service_container, new TiffinFragment()).commit();

        } else if (v.getId() == R.id.rl_tab_3) {
            mTitle.setText("Cart");
            img_home.setSelected(false);
            img_tiffin.setSelected(false);
            img_cart.setSelected(true);
            img_more.setSelected(false);

            tv_home.setSelected(false);
            tv_tiffin.setSelected(false);
            tv_cart.setSelected(true);
            tv_more.setSelected(false);

            tv_home.setTextColor(Color.parseColor("#888F8C"));
            tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
            tv_cart.setTextColor(Color.parseColor("#275B89"));
            tv_more.setTextColor(Color.parseColor("#888F8C"));

            img_home.setImageResource(R.drawable.ic_home);
            img_tiffin.setImageResource(R.drawable.ic_tifin);
            img_cart.setImageResource(R.drawable.ic_cart_active);
            img_more.setImageResource(R.drawable.ic_more);

            toolbar.setBackgroundResource(R.drawable.main_gradient_bg);

            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.service_container, new CartFragment()).commit();
        } else if (v.getId() == R.id.rl_tab_4) {
            toolbar.setBackgroundResource(R.drawable.main_gradient_bg);
            mTitle.setText("More");
            img_home.setSelected(false);
            img_tiffin.setSelected(false);
            img_cart.setSelected(false);
            img_more.setSelected(true);

            tv_home.setSelected(false);
            tv_tiffin.setSelected(false);
            tv_cart.setSelected(false);
            tv_more.setSelected(true);

            tv_home.setTextColor(Color.parseColor("#888F8C"));
            tv_tiffin.setTextColor(Color.parseColor("#888F8C"));
            tv_cart.setTextColor(Color.parseColor("#888F8C"));
            tv_more.setTextColor(Color.parseColor("#275B89"));

            img_home.setImageResource(R.drawable.ic_home);
            img_tiffin.setImageResource(R.drawable.ic_tifin);
            img_cart.setImageResource(R.drawable.ic_cart);
            img_more.setImageResource(R.drawable.ic_more_active);
            mFragmentManager = getSupportFragmentManager();
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.service_container, new MoreFragment()).commit();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (callNumber == 10) {
            try {
                Cart c = new Gson().fromJson(o.getJSONObject("data").toString(), Cart.class);
                if (c.getCartlist().size() > 0) {
                    txt_cart_count.setVisibility(View.VISIBLE);
                    MyApp.setSharedPrefInteger(AppConstant.CART_COUNTER, c.getCartlist().size());
                    HashMap<String, String> map = MyApp.getApplication().readType();
                    for (int i = 0; i < c.getCartlist().size(); i++) {
                        if (!map.containsKey(c.getCartlist().get(i).getId())) {
                            map.put(c.getCartlist().get(i).getId(), c.getCartlist().get(i).getId());
                        }
                    }

                    MyApp.getApplication().writeType(map);
                } else {
                    txt_cart_count.setVisibility(View.GONE);
                    MyApp.setSharedPrefInteger(AppConstant.CART_COUNTER, 0);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            dismissDialog();
            Type listType = new TypeToken<ArrayList<CategoryDetails>>() {
            }.getType();
            try {
                List<CategoryDetails> catList =
                        new GsonBuilder().create().fromJson(o.getJSONArray("data").toString(), listType);
                if (catList.size() == 0) {
//                MyApp.popMessage("Local Friend", "No data available for this category", getContext());
                } else {
                    SingleInstance.getInstance().setCatList(catList);
//                startActivity(new Intent(getContext(), FoodActivity.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onTimeOutRetry(int callNumber) {

    }

    @Override
    public void onErrorReceived(String error) {

    }

    public void makeFlyAnimation(ImageView targetView) {
        new CircleAnimationUtil().attachActivity(this).setTargetView(targetView).setMoveDuration(500)
                .setDestView(txt_cart_count).setAnimationListener(new Animator.AnimatorListener() {
            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {

            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        }).startAnimation();
    }
}
