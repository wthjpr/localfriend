package com.localfriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;

import java.text.DecimalFormat;


public class ScheduleMealActivity extends CustomActivity {
private CheckBox chk_break_fast, chk_lunch,chk_dinner;
private TextView tv_breakfast_time;
  RangeBar  rangebar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_meal);
        rangebar = (RangeBar) findViewById(R.id.rangebar1);
        rangebar.setTickCount(25 * 4);//SMALLEST_HOUR_FRACTION = 4;
        rangebar.setTickHeight(0);
        rangebar.setThumbRadius(8);
        rangebar.setConnectingLineWeight(3);

        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                DecimalFormat deciFormat= new DecimalFormat("00");

                //leftIndexValue.setText("" + leftThumbIndex);
               // rightIndexValue.setText("" + rightThumbIndex);

                int minHour = leftThumbIndex / 4;
                int minMinute = 4 * (leftThumbIndex % 4);
                int maxHour = rightThumbIndex / 4;
                int maxMinute = 4 * (rightThumbIndex % 4);
              //  leftIndexValue.setText(minHour + ":" + minMinute);
                //rightIndexValue.setText(maxHour + ":" + maxMinute);

               // leftIndexValue.setText(deciFormat.format(minHour) + ":" + deciFormat.format(minMinute));
                //rightIndexValue.setText(deciFormat.format(maxHour) + ":" + deciFormat.format(maxMinute));
            }
        });
     /*   final CrystalRangeSeekbar rangeSeekbar = (Rangebar)findViewById(R.id.rangeSeekbar1);
        tv_breakfast_time=(TextView)findViewById(R.id.tv_breakfast_time);
        rangeSeekbar.setTickCount(25 * SMALLEST_HOUR_FRACTION);//SMALLEST_HOUR_FRACTION = 4;
        rangeSeekbar.setTickHeight(0);
        rangeSeekbar.setThumbRadius(8);
        rangeSeekbar.setConnectingLineWeight(3);
        rangeSeekbar.setNotifyWhileDragging(true);*/
// get min and max text view


/*
// set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tv_breakfast_time.setText(String.valueOf(minValue)+"-"+String.valueOf(maxValue));

            }
        });

// set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });*/
    }
}
