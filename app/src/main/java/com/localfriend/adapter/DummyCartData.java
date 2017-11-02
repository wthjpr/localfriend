//package com.localfriend.adapter;
//
//import com.localfriend.R;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
///**
// * Created by Abhishek on 29-03-2017.
// */
//
//public class DummyCartData {
//    private static final String[] ItemName = {"Veggie Burger","Veggie Burger","Veggie Burger","Veggie Burger","Veggie Burger"};
//    private static final Integer[] ItemImage = {R.drawable.fast_food,R.drawable.fast_food,R.drawable.fast_food,R.drawable.fast_food,R.drawable.fast_food};
//    private static final String[] ItemCost = {"Rs.100","Rs.100","Rs.100","Rs.100","Rs.100"};
//
//
//    public static List<DummyCartItem> getListData(){
//        List<DummyCartItem> data = new ArrayList<>();
//
//        for (int i=0;i< 4;i++){
//            for (int x =0; x<ItemName.length; x++){
//                DummyCartItem item = new DummyCartItem();
//                item.setItemName(ItemName[x]);
//                item.setItemImage(ItemImage[x]);
//                item.setItemCost(ItemCost[x]);
//                data.add(item);
//            }
//        }
//        return (data);
//    }
//    public static DummyCartItem getRandomListItem(){
//        int rand = new Random().nextInt(5);
//
//        DummyCartItem item = new DummyCartItem();
//
//        item.setItemName(ItemName[rand]);
//        item.setItemImage(ItemImage[rand]);
//        item.setItemCost(ItemCost[rand]);
//
//
//        return item;
//    }
//}
