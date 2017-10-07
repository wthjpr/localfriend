package com.localfriend.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.localfriend.MainActivity;
import com.localfriend.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends CustomFragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    private SliderLayout slider_home_frag;
    private LinearLayout lrn_fruit,lrn_vegetable,lrn_tiffin,lrn_food,lrn_mithaiwala,lrn_discount;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_home, container, false);
        slider_home_frag = (SliderLayout) myView.findViewById(R.id.slider_home_frag);
        lrn_fruit = myView.findViewById(R.id.lrn_fruit);
        lrn_vegetable = myView.findViewById(R.id.lrn_vegetable);
        lrn_tiffin = myView.findViewById(R.id.lrn_tiffin);
        lrn_food = myView.findViewById(R.id.lrn_food);
        lrn_mithaiwala = myView.findViewById(R.id.lrn_mithaiwala);
        lrn_discount = myView.findViewById(R.id.lrn_discount);

        /*HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");*/

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Breakfast",R.drawable.slider_one_img);
        file_maps.put("Indian Dish",R.drawable.slider_two);
        file_maps.put("Breakfast",R.drawable.slider_one_img);
        file_maps.put("Dinner", R.drawable.slider_two);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            slider_home_frag.addSlider(textSliderView);
        }
        slider_home_frag.setPresetTransformer(SliderLayout.Transformer.Default);
        slider_home_frag.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider_home_frag.setCustomAnimation(new DescriptionAnimation());
        slider_home_frag.setDuration(4000);
        slider_home_frag.addOnPageChangeListener(this);

        setTouchNClick(lrn_fruit);
        setTouchNClick(lrn_vegetable);
        setTouchNClick(lrn_tiffin);
        setTouchNClick(lrn_food);
        setTouchNClick(lrn_mithaiwala);
        setTouchNClick(lrn_discount);


        return myView;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {



    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
