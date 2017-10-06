package com.localfriend.adapter;



import com.localfriend.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class DummyBreakfastData {
    private static final String[] BreakFastName = {"Aloo Pratha","Omelette","Dhosa","Poha","Poori Sabji"};
    private static final Integer[] BreakFastImage = {R.drawable.slider_one_img,R.drawable.slider_one_img,R.drawable.slider_one_img,R.drawable.slider_one_img, R.drawable.slider_one_img};
    private static final String[] BreakFastCost = {"Rs.50","Rs.50","Rs.100","Rs.100","Rs.100"};


    public static List<DummyBreakfastItem> getListData(){
        List<DummyBreakfastItem> data = new ArrayList<>();

        for (int i=0;i< 4;i++){
            for (int x =0; x<BreakFastName.length; x++){
                DummyBreakfastItem item = new DummyBreakfastItem();
                item.setBreakFastName(BreakFastName[x]);
                item.setBreakFastImage(BreakFastImage[x]);
                item.setBreakFastCost(BreakFastCost[x]);
                data.add(item);
            }
        }
        return (data);
    }
    public static DummyBreakfastItem getRandomListItem(){
        int rand = new Random().nextInt(5);

        DummyBreakfastItem item = new DummyBreakfastItem();

        item.setBreakFastName(BreakFastName[rand]);
        item.setBreakFastImage(BreakFastImage[rand]);
        item.setBreakFastCost(BreakFastCost[rand]);


        return item;
    }
}
