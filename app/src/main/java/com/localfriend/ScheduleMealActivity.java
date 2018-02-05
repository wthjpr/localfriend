package com.localfriend;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;
import com.localfriend.application.MyApp;
import com.localfriend.utils.AppConstant;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Calendar;

import me.bendik.simplerangeview.SimpleRangeView;


public class ScheduleMealActivity extends CustomActivity implements SimpleRangeView.OnRangeLabelsListener, SimpleRangeView.OnTrackRangeListener{
    private CheckBox chk_break_fast, chk_lunch, chk_dinner;
    private TextView tv_breakfast_time, tv_lunch_time, tv_dinner_time;
  //  private RangeBar rangebar, rangebar_dinner, rangebar_lunch;
    private TextView tv_schedule_breakfast, tv_schedule_lunch, tv_schedule_dinner;
    private TextView tv_selected_days, tv_selected_days_lunch, tv_selected_days_dinner;
    private RelativeLayout rel_sun_bf, rel_mon_bf, rel_tue_bf, rel_wed_bf, rel_thurs_bf, rel_fri_bf, rel_sat_bf;
    private TextView tv_sunday_bf, tv_monday_bf, tv_tuesday_bf, tv_wednesday_bf, tv_thursday_bf, tv_friday_bf, tv_saturday_bf;

    private RelativeLayout rel_sun_lnch, rel_mon_lnch, rel_tue_lnch, rel_wed_lnch, rel_thurs_lnch, rel_fri_lnch, rel_sat_lnch;
    private TextView tv_sunday_lnch, tv_monday_lnch, tv_tuesday_lnch, tv_wednesday_lnch, tv_thursday_lnch, tv_friday_lnch, tv_saturday_lnch;

    private RelativeLayout rel_sun, rel_mon, rel_tue, rel_wed, rel_thurs, rel_fri, rel_sat;
    int rel_sun_lnchc = 0, rel_mon_lnchc = 0, rel_tue_lnchc = 0, rel_wed_lnchc = 0, rel_thurs_lnchc = 0, rel_fri_lnchc = 0, rel_sat_lnchc = 0;
    int rel_sunc = 0, rel_monc = 0, rel_tuec = 0, rel_wedc = 0, rel_thursc = 0, rel_fric = 0, rel_satc = 0;
    int rel_sun_bfc = 0, rel_mon_bfc = 0, rel_tue_bfc = 0, rel_wed_bfc = 0, rel_thurs_bfc = 0, rel_fri_bfc = 0, rel_sat_bfc = 0;
    private TextView tv_sunday, tv_monday, tv_tuesday, tv_wednesday, tv_thursday, tv_friday, tv_saturday;
    int minHour, minMinute, maxHour, maxMinute;
    private String breakfast = "", lunch = "", dinner = "";

    private LinearLayout lnr_breakfast, lnr_lunch, lnr_dinner;
    private String[] labels = new String[] {"7:00","7:30","8:00","8:30","9:00","9:30","10:00","10:30","11:00"};
    private String[] labelsLunch = new String[] {"12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00"};
    private String[] labelsDinner = new String[] {"19:00","19:30","20:00","20:30","21:00","21:30","22:00","22:30","23:00"};
    SimpleRangeView rangeView,rangebar_dinner,rangebar_lunch;
    String breakfast_start="",breakfast_end="";
    String lunch_start="",lunch_end="";
    String dinner_start="",dinner_end="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_meal);

        setupUiElement();
        checkLayout();
    }

    private void setupUiElement() {

        chk_break_fast = (CheckBox) findViewById(R.id.chk_break_fast);
        chk_lunch = (CheckBox) findViewById(R.id.chk_lunch);
        chk_dinner = (CheckBox) findViewById(R.id.chk_dinner);

// Schedule Label Above the layout

        tv_schedule_breakfast = (TextView) findViewById(R.id.tv_schedule_breakfast);
        tv_schedule_breakfast.setVisibility(View.GONE);
        tv_schedule_lunch = (TextView) findViewById(R.id.tv_schedule_lunch);
        tv_schedule_lunch.setVisibility(View.GONE);
        tv_schedule_dinner = (TextView) findViewById(R.id.tv_schedule_dinner);
        tv_schedule_dinner.setVisibility(View.GONE);


//Linear layout the layout

        lnr_breakfast = (LinearLayout) findViewById(R.id.lnr_breakfast);
        lnr_breakfast.setVisibility(View.GONE);
        lnr_lunch = (LinearLayout) findViewById(R.id.lnr_lunch);
        lnr_lunch.setVisibility(View.GONE);
        lnr_dinner = (LinearLayout) findViewById(R.id.lnr_dinner);
        lnr_dinner.setVisibility(View.GONE);

       /* if (chk_break_fast.isChecked()) {
            tv_schedule_breakfast.setVisibility(View.VISIBLE);
            lnr_breakfast.setVisibility(View.VISIBLE);
        } else if (!chk_break_fast.isChecked()) {
            tv_schedule_breakfast.setVisibility(View.GONE);
            lnr_breakfast.setVisibility(View.GONE);
        } else if (chk_lunch.isChecked()) {
            tv_schedule_lunch.setVisibility(View.VISIBLE);
            lnr_lunch.setVisibility(View.VISIBLE);
        } else if (!chk_lunch.isChecked()) {
            tv_schedule_lunch.setVisibility(View.GONE);
            lnr_lunch.setVisibility(View.GONE);
        } else if (chk_dinner.isChecked()) {
            tv_schedule_dinner.setVisibility(View.VISIBLE);
            lnr_dinner.setVisibility(View.VISIBLE);
        } else if (!chk_dinner.isChecked()) {
            tv_schedule_dinner.setVisibility(View.GONE);
            lnr_dinner.setVisibility(View.GONE);
        }*/
// time range texviews
        tv_breakfast_time = (TextView) findViewById(R.id.tv_breakfast_time);
        tv_lunch_time = (TextView) findViewById(R.id.tv_lunch_time);
        tv_dinner_time = (TextView) findViewById(R.id.tv_dinner_time);

        //Range bars for setting time range
        rangeView = (SimpleRangeView) findViewById(R.id.rangeview);
        rangebarBreakfast();
        rangebar_dinner = (SimpleRangeView) findViewById(R.id.rangebar_dinner);
        rangebarDinner();
        rangebar_lunch = (SimpleRangeView) findViewById(R.id.rangebar_lunch);
        rangebarLunch();

//Textview for showing selected days.

        tv_selected_days = (TextView) findViewById(R.id.tv_selected_days);
        tv_selected_days_lunch = (TextView) findViewById(R.id.tv_selected_days_lunch);
        tv_selected_days_dinner = (TextView) findViewById(R.id.tv_selected_days_dinner);

////////////////////////////////////////////Breakfast/////////////////////////////////////////////////
        //relative layout for breakfast days

        rel_sun_bf = (RelativeLayout) findViewById(R.id.rel_sun_bf);
        rel_mon_bf = (RelativeLayout) findViewById(R.id.rel_mon_bf);
        rel_tue_bf = (RelativeLayout) findViewById(R.id.rel_tue_bf);
        rel_wed_bf = (RelativeLayout) findViewById(R.id.rel_wed_bf);
        rel_thurs_bf = (RelativeLayout) findViewById(R.id.rel_thurs_bf);
        rel_fri_bf = (RelativeLayout) findViewById(R.id.rel_fri_bf);
        rel_sat_bf = (RelativeLayout) findViewById(R.id.rel_sat_bf);

        //Textview for breakfast days
        tv_sunday_bf = (TextView) findViewById(R.id.tv_sunday_bf);
        tv_monday_bf = (TextView) findViewById(R.id.tv_monday_bf);
        tv_tuesday_bf = (TextView) findViewById(R.id.tv_tuesday_bf);
        tv_wednesday_bf = (TextView) findViewById(R.id.tv_wednesday_bf);
        tv_thursday_bf = (TextView) findViewById(R.id.tv_thursday_bf);
        tv_friday_bf = (TextView) findViewById(R.id.tv_friday_bf);
        tv_saturday_bf = (TextView) findViewById(R.id.tv_saturday_bf);

// click event for breakfast
        setTouchNClick(R.id.rel_sun_bf);
        setTouchNClick(R.id.rel_mon_bf);
        setTouchNClick(R.id.rel_tue_bf);
        setTouchNClick(R.id.rel_wed_bf);
        setTouchNClick(R.id.rel_thurs_bf);
        setTouchNClick(R.id.rel_fri_bf);
        setTouchNClick(R.id.rel_sat_bf);

////////////////////////////////////////LUNCH//////////////////////////////////////////
        //relative layout for lunch days

        rel_sun_lnch = (RelativeLayout) findViewById(R.id.rel_sun_lnch);
        rel_mon_lnch = (RelativeLayout) findViewById(R.id.rel_mon_lnch);
        rel_tue_lnch = (RelativeLayout) findViewById(R.id.rel_tue_lnch);
        rel_wed_lnch = (RelativeLayout) findViewById(R.id.rel_wed_lnch);
        rel_thurs_lnch = (RelativeLayout) findViewById(R.id.rel_thurs_lnch);
        rel_fri_lnch = (RelativeLayout) findViewById(R.id.rel_fri_lnch);
        rel_sat_lnch = (RelativeLayout) findViewById(R.id.rel_sat_lnch);

        //Textview for lunch days

        tv_sunday_lnch = (TextView) findViewById(R.id.tv_sunday_lnch);
        tv_monday_lnch = (TextView) findViewById(R.id.tv_monday_lnch);
        tv_tuesday_lnch = (TextView) findViewById(R.id.tv_tuesday_lnch);
        tv_wednesday_lnch = (TextView) findViewById(R.id.tv_wednesday_lnch);
        tv_thursday_lnch = (TextView) findViewById(R.id.tv_thursday_lnch);
        tv_friday_lnch = (TextView) findViewById(R.id.tv_friday_lnch);
        tv_saturday_lnch = (TextView) findViewById(R.id.tv_saturday_lnch);
// click event for lunch
        setTouchNClick(R.id.rel_sun_lnch);
        setTouchNClick(R.id.rel_mon_lnch);
        setTouchNClick(R.id.rel_tue_lnch);
        setTouchNClick(R.id.rel_wed_lnch);
        setTouchNClick(R.id.rel_thurs_lnch);
        setTouchNClick(R.id.rel_fri_lnch);
        setTouchNClick(R.id.rel_sat_lnch);

/////////////Dinner///////////////////////////////////////////////////////
        //relative layout for Dinner days

        rel_sun = (RelativeLayout) findViewById(R.id.rel_sun);
        rel_mon = (RelativeLayout) findViewById(R.id.rel_mon);
        rel_tue = (RelativeLayout) findViewById(R.id.rel_tue);
        rel_wed = (RelativeLayout) findViewById(R.id.rel_wed);
        rel_thurs = (RelativeLayout) findViewById(R.id.rel_thurs);
        rel_fri = (RelativeLayout) findViewById(R.id.rel_fri);
        rel_sat = (RelativeLayout) findViewById(R.id.rel_sat);

        //Textview for Dinner days

        tv_sunday = (TextView) findViewById(R.id.tv_sunday);
        tv_monday = (TextView) findViewById(R.id.tv_monday);
        tv_tuesday = (TextView) findViewById(R.id.tv_tuesday);
        tv_wednesday = (TextView) findViewById(R.id.tv_wednesday);
        tv_thursday = (TextView) findViewById(R.id.tv_thursday);
        tv_friday = (TextView) findViewById(R.id.tv_friday);
        tv_saturday = (TextView) findViewById(R.id.tv_saturday);

// click event for diner
        setTouchNClick(R.id.rel_sun);
        setTouchNClick(R.id.rel_mon);
        setTouchNClick(R.id.rel_tue);
        setTouchNClick(R.id.rel_wed);
        setTouchNClick(R.id.rel_thurs);
        setTouchNClick(R.id.rel_fri);
        setTouchNClick(R.id.rel_sat);

        //////////////////////////////////////////////////////////////

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        /////////// Click Functionalities for Dinner ///////////////////////////

        if (v == rel_sun) {
            if (rel_sunc == 0) {
                rel_sun.setSelected(true);
                rel_sun.setBackgroundResource(R.drawable.selected_bg);
                tv_sunday.setTextColor(Color.parseColor("#FFFFFF"));
                rel_sunc = 1;
                dinner = dinner + "Sunday,";

            } else {
                rel_sun.setSelected(false);
                rel_sun.setBackgroundResource(R.drawable.edt_bg);
                tv_sunday.setTextColor(Color.parseColor("#999999"));
                rel_sunc = 0;
                dinner = dinner.replace("Sunday,", "");
            }
            tv_selected_days_dinner.setText(dinner);
        } else if (v == rel_mon) {
            if (rel_monc == 0) {
                rel_mon.setSelected(true);
                rel_mon.setBackgroundResource(R.drawable.selected_bg);
                tv_monday.setTextColor(Color.parseColor("#FFFFFF"));
                rel_monc = 1;
                dinner = dinner + "Monday,";
            } else {
                rel_mon.setSelected(false);
                rel_mon.setBackgroundResource(R.drawable.edt_bg);
                tv_monday.setTextColor(Color.parseColor("#999999"));
                rel_monc = 0;
                dinner = dinner.replace("Monday,", "");
            }
            tv_selected_days_dinner.setText(dinner);
        } else if (v == rel_tue) {
            if (rel_tuec == 0) {
                rel_tue.setSelected(true);
                rel_tue.setBackgroundResource(R.drawable.selected_bg);
                tv_tuesday.setTextColor(Color.parseColor("#FFFFFF"));
                rel_tuec = 1;
                dinner = dinner + "Tuesday,";
            } else {
                rel_tue.setSelected(false);
                rel_tue.setBackgroundResource(R.drawable.edt_bg);
                tv_tuesday.setTextColor(Color.parseColor("#999999"));
                rel_tuec = 0;
                dinner = dinner.replace("Tuesday,", "");
            }
            tv_selected_days_dinner.setText(dinner);
        } else if (v == rel_wed) {
            if (rel_wedc == 0) {
                rel_wed.setSelected(true);
                rel_wed.setBackgroundResource(R.drawable.selected_bg);
                tv_wednesday.setTextColor(Color.parseColor("#FFFFFF"));
                rel_wedc = 1;
                dinner = dinner + "Wednesday,";
            } else {
                rel_wed.setSelected(false);
                rel_wed.setBackgroundResource(R.drawable.edt_bg);
                tv_wednesday.setTextColor(Color.parseColor("#999999"));
                rel_wedc = 0;
                dinner = dinner.replace("Wednesday,", "");
            }
            tv_selected_days_dinner.setText(dinner);
        } else if (v == rel_thurs) {
            if (rel_thursc == 0) {
                rel_thurs.setSelected(true);
                rel_thurs.setBackgroundResource(R.drawable.selected_bg);
                tv_thursday.setTextColor(Color.parseColor("#FFFFFF"));
                rel_thursc = 1;
                dinner = dinner + "Thursday,";
            } else {
                rel_thurs.setSelected(false);
                rel_thurs.setBackgroundResource(R.drawable.edt_bg);
                tv_thursday.setTextColor(Color.parseColor("#999999"));
                rel_thursc = 0;
                dinner = dinner.replace("Thursday,", "");
            }
            tv_selected_days_dinner.setText(dinner);
        } else if (v == rel_fri) {
            if (rel_fric == 0) {
                rel_fri.setSelected(true);
                rel_fri.setBackgroundResource(R.drawable.selected_bg);
                tv_friday.setTextColor(Color.parseColor("#FFFFFF"));
                rel_fric = 1;
                dinner = dinner + "Friday,";
            } else {
                rel_fri.setSelected(false);
                rel_fri.setBackgroundResource(R.drawable.edt_bg);
                tv_friday.setTextColor(Color.parseColor("#999999"));
                rel_fric = 0;
                dinner = dinner.replace("Friday,", "");
            }
            tv_selected_days_dinner.setText(dinner);
        } else if (v == rel_sat) {
            if (rel_satc == 0) {
                rel_sat.setSelected(true);
                rel_sat.setBackgroundResource(R.drawable.selected_bg);
                tv_saturday.setTextColor(Color.parseColor("#FFFFFF"));
                rel_satc = 1;
                dinner = dinner + "Saturday,";
            } else {
                rel_sat.setSelected(false);
                rel_sat.setBackgroundResource(R.drawable.edt_bg);
                tv_saturday.setTextColor(Color.parseColor("#999999"));
                rel_satc = 0;
                dinner = dinner.replace("Saturday,", "");
            }
            tv_selected_days_dinner.setText(dinner);
        }

        /////////// Click Functionalities for Lunch ///////////////////////////

        else if (v == rel_sun_lnch) {
            if (rel_sun_lnchc == 0) {
                rel_sun_lnch.setSelected(true);
                rel_sun_lnch.setBackgroundResource(R.drawable.selected_bg);
                tv_sunday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                rel_sun_lnchc = 1;
                lunch = lunch + "Sunday,";
            } else {
                rel_sun_lnch.setSelected(false);
                rel_sun_lnch.setBackgroundResource(R.drawable.edt_bg);
                tv_sunday_lnch.setTextColor(Color.parseColor("#999999"));
                rel_sun_lnchc = 0;
                lunch = lunch.replace("Sunday,", "");
            }
            tv_selected_days_lunch.setText(lunch);
        } else if (v == rel_mon_lnch) {
            if (rel_mon_lnchc == 0) {
                rel_mon_lnch.setSelected(true);
                rel_mon_lnch.setBackgroundResource(R.drawable.selected_bg);
                tv_monday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                rel_mon_lnchc = 1;
                lunch = lunch + "Monday,";
            } else {
                rel_mon_lnch.setSelected(false);
                rel_mon_lnch.setBackgroundResource(R.drawable.edt_bg);
                tv_monday_lnch.setTextColor(Color.parseColor("#999999"));
                rel_mon_lnchc = 0;
                lunch = lunch.replace("Monday,", "");
            }
            tv_selected_days_lunch.setText(lunch);
        } else if (v == rel_tue_lnch) {
            if (rel_tue_lnchc == 0) {
                rel_tue_lnch.setSelected(true);
                rel_tue_lnch.setBackgroundResource(R.drawable.selected_bg);
                tv_tuesday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                rel_tue_lnchc = 1;
                lunch = lunch + "Tuesday,";
            } else {
                rel_tue_lnch.setSelected(false);
                rel_tue_lnch.setBackgroundResource(R.drawable.edt_bg);
                tv_tuesday_lnch.setTextColor(Color.parseColor("#999999"));
                rel_tue_lnchc = 0;
                lunch = lunch.replace("Tuesday,", "");
            }
            tv_selected_days_lunch.setText(lunch);
        } else if (v == rel_wed_lnch) {
            if (rel_wed_lnchc == 0) {
                rel_wed_lnch.setSelected(true);
                rel_wed_lnch.setBackgroundResource(R.drawable.selected_bg);
                tv_wednesday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                rel_wed_lnchc = 1;
                lunch = lunch + "Wednesday,";
            } else {
                rel_wed_lnch.setSelected(false);
                rel_wed_lnch.setBackgroundResource(R.drawable.edt_bg);
                tv_wednesday_lnch.setTextColor(Color.parseColor("#999999"));
                rel_wed_lnchc = 0;
                lunch = lunch.replace("Wednesday,", "");
            }
            tv_selected_days_lunch.setText(lunch);
        } else if (v == rel_thurs_lnch) {//, , , , , , ;
            if (rel_thurs_lnchc == 0) {
                rel_thurs_lnch.setSelected(true);
                rel_thurs_lnch.setBackgroundResource(R.drawable.selected_bg);
                tv_thursday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                rel_thurs_lnchc = 1;
                lunch = lunch + "Thursday,";
            } else {
                rel_thurs_lnch.setSelected(false);
                rel_thurs_lnch.setBackgroundResource(R.drawable.edt_bg);
                tv_thursday_lnch.setTextColor(Color.parseColor("#999999"));
                rel_thurs_lnchc = 0;
                lunch = lunch.replace("Thursday,", "");
            }
            tv_selected_days_lunch.setText(lunch);
        } else if (v == rel_fri_lnch) {
            if (rel_fri_lnchc == 0) {
                rel_fri_lnch.setSelected(true);
                rel_fri_lnch.setBackgroundResource(R.drawable.selected_bg);
                tv_friday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                rel_fri_lnchc = 1;
                lunch = lunch + "Friday,";
            } else {
                rel_fri_lnch.setSelected(false);
                rel_fri_lnch.setBackgroundResource(R.drawable.edt_bg);
                tv_friday_lnch.setTextColor(Color.parseColor("#999999"));
                rel_fri_lnchc = 0;
                lunch = lunch.replace("Friday,", "");
            }
            tv_selected_days_lunch.setText(lunch);
        } else if (v == rel_sat_lnch) {
            if (rel_sat_lnchc == 0) {
                rel_sat_lnch.setSelected(true);
                rel_sat_lnch.setBackgroundResource(R.drawable.selected_bg);
                tv_saturday_lnch.setTextColor(Color.parseColor("#FFFFFF"));
                rel_sat_lnchc = 1;
                lunch = lunch + "Saturday,";
            } else {
                rel_sat_lnch.setSelected(false);
                rel_sat_lnch.setBackgroundResource(R.drawable.edt_bg);
                tv_saturday_lnch.setTextColor(Color.parseColor("#999999"));
                rel_sat_lnchc = 0;
                lunch = lunch.replace("Saturday,", "");
            }
            tv_selected_days_lunch.setText(lunch);
        }

/////////// Click Functionalities for Breakfast///////////////////////////
        // , , , , , ,
        else if (v == rel_sun_bf) {
            if (rel_sun_bfc == 0) {
                rel_sun_bf.setSelected(true);
                rel_sun_bf.setBackgroundResource(R.drawable.selected_bg);
                tv_sunday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                rel_sun_bfc = 1;
                breakfast = breakfast + "Sunday,";

            } else {
                rel_sun_bf.setSelected(false);
                rel_sun_bf.setBackgroundResource(R.drawable.edt_bg);
                tv_sunday_bf.setTextColor(Color.parseColor("#999999"));
                rel_sun_bfc = 0;
                breakfast = breakfast.replace("Sunday,", "");
            }
            tv_selected_days.setText(breakfast);

        } else if (v == rel_mon_bf) {
            if (rel_mon_bfc == 0) {
                rel_mon_bf.setSelected(true);
                rel_mon_bf.setBackgroundResource(R.drawable.selected_bg);
                tv_monday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                rel_mon_bfc = 1;
                breakfast = breakfast + "Monday,";

            } else {
                rel_mon_bf.setSelected(false);
                rel_mon_bf.setBackgroundResource(R.drawable.edt_bg);
                tv_monday_bf.setTextColor(Color.parseColor("#999999"));
                rel_mon_bfc = 0;
                breakfast = breakfast.replace("Monday,", "");
            }
            tv_selected_days.setText(breakfast);
        } else if (v == rel_tue_bf) {
            if (rel_tue_bfc == 0) {
                rel_tue_bf.setSelected(true);
                rel_tue_bf.setBackgroundResource(R.drawable.selected_bg);
                tv_tuesday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                rel_tue_bfc = 1;
                breakfast = breakfast + "Tuesday,";

            } else {
                rel_tue_bf.setSelected(false);
                rel_tue_bf.setBackgroundResource(R.drawable.edt_bg);
                tv_tuesday_bf.setTextColor(Color.parseColor("#999999"));
                rel_tue_bfc = 0;
                breakfast = breakfast.replace("Tuesday,", "");
            }
            tv_selected_days.setText(breakfast);
        } else if (v == rel_wed_bf) {
            if (rel_wed_bfc == 0) {
                rel_wed_bf.setSelected(true);
                rel_wed_bf.setBackgroundResource(R.drawable.selected_bg);
                tv_wednesday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                rel_wed_bfc = 1;
                breakfast = breakfast + "Wednesday,";

            } else {
                rel_wed_bf.setSelected(false);
                rel_wed_bf.setBackgroundResource(R.drawable.edt_bg);
                tv_wednesday_bf.setTextColor(Color.parseColor("#999999"));
                rel_wed_bfc = 0;
                breakfast = breakfast.replace("Wednesday,", "");
            }
            tv_selected_days.setText(breakfast);
        } else if (v == rel_thurs_bf) {
            if (rel_thurs_bfc == 0) {
                rel_thurs_bf.setSelected(true);
                rel_thurs_bf.setBackgroundResource(R.drawable.selected_bg);
                tv_thursday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                rel_thurs_bfc = 1;
                breakfast = breakfast + "Thursday,";
            } else {
                rel_thurs_bf.setSelected(false);
                rel_thurs_bf.setBackgroundResource(R.drawable.edt_bg);
                tv_thursday_bf.setTextColor(Color.parseColor("#999999"));
                rel_thurs_bfc = 0;
                breakfast = breakfast.replace("Thursday,", "");
            }
            tv_selected_days.setText(breakfast);
        } else if (v == rel_fri_bf) {
            if (rel_fri_bfc == 0) {
                rel_fri_bf.setSelected(true);
                rel_fri_bf.setBackgroundResource(R.drawable.selected_bg);
                tv_friday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                rel_fri_bfc = 1;
                breakfast = breakfast + "Friday,";
            } else {
                rel_fri_bf.setSelected(false);
                rel_fri_bf.setBackgroundResource(R.drawable.edt_bg);
                tv_friday_bf.setTextColor(Color.parseColor("#999999"));
                rel_fri_bfc = 0;
                breakfast = breakfast.replace("Friday,", "");
            }
            tv_selected_days.setText(breakfast);
        } else if (v == rel_sat_bf) {
            if (rel_sat_bfc == 0) {
                rel_sat_bf.setSelected(true);
                rel_sat_bf.setBackgroundResource(R.drawable.selected_bg);
                tv_saturday_bf.setTextColor(Color.parseColor("#FFFFFF"));
                rel_sat_bfc = 1;
                breakfast = breakfast + "Saturday,";

            } else {
                rel_sat_bf.setSelected(false);
                rel_sat_bf.setBackgroundResource(R.drawable.edt_bg);
                tv_saturday_bf.setTextColor(Color.parseColor("#999999"));
                rel_sat_bfc = 0;
                breakfast = breakfast.replace("Saturday,", "");
            }
            tv_selected_days.setText(breakfast);
        }

    }


    //// Method for setting visibility of the layouts////

    private void checkLayout() {

        chk_break_fast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (chk_break_fast.isChecked()) {
                    tv_schedule_breakfast.setVisibility(View.VISIBLE);
                    lnr_breakfast.setVisibility(View.VISIBLE);
                } else if (!chk_break_fast.isChecked()) {
                    tv_schedule_breakfast.setVisibility(View.GONE);
                    lnr_breakfast.setVisibility(View.GONE);
                }
            }
        });

        chk_lunch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (chk_lunch.isChecked()) {
                    tv_schedule_lunch.setVisibility(View.VISIBLE);
                    lnr_lunch.setVisibility(View.VISIBLE);
                } else if (!chk_lunch.isChecked()) {
                    tv_schedule_lunch.setVisibility(View.GONE);
                    lnr_lunch.setVisibility(View.GONE);
                }
            }
        });
        chk_dinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (chk_dinner.isChecked()) {
                    tv_schedule_dinner.setVisibility(View.VISIBLE);
                    lnr_dinner.setVisibility(View.VISIBLE);
                } else if (!chk_dinner.isChecked()) {
                    tv_schedule_dinner.setVisibility(View.GONE);
                    lnr_dinner.setVisibility(View.GONE);
                }
            }
        });

    }
    /////Methods for Range Bar functionalities///////////////////////

    private void rangebarBreakfast() {
        rangeView.setOnRangeLabelsListener(this);
        rangeView.setOnTrackRangeListener(this);

        rangeView.setActiveLineColor(getResources().getColor(R.color.colorPrimaryDark));
        rangeView.setActiveThumbColor(getResources().getColor(R.color.colorPrimaryDark));
        rangeView.setActiveLabelColor(getResources().getColor(R.color.colorPrimaryDark));
        rangeView.setActiveThumbLabelColor(getResources().getColor(R.color.colorPrimaryDark));
        rangeView.setActiveFocusThumbColor(getResources().getColor(R.color.colorPrimaryDark));
        rangeView.setActiveFocusThumbAlpha(0.26f);
      //  rangebar.setTickCount(25 * 4);//SMALLEST_HOUR_FRACTION = 4;
      //  rangebar.setTickHeight(20);
       // rangebar.setThumbRadius(10);
       // rangebar.setConnectingLineWeight(3);
        // rangebar.setTickCount(11);
        /*rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                DecimalFormat deciFormat = new DecimalFormat("00");
                tv_breakfast_time.setText("" + leftThumbIndex + "-" + "" + rightThumbIndex);
                //  leftIndexValue.setText("" + leftThumbIndex);
                //  rightIndexValue.setText("" + rightThumbIndex);

                minHour = leftThumbIndex / 4;
                minMinute = 4 * (leftThumbIndex % 4);
                maxHour = rightThumbIndex / 4;
                maxMinute = 30 + (rightThumbIndex - rightThumbIndex);
                tv_breakfast_time.setText(minHour + ":" + minMinute + "-" + maxHour + ":" + maxMinute);
                //leftIndexValue.setText(minHour + ":" + minMinute);
                // rightIndexValue.setText(maxHour + ":" + maxMinute);
                tv_breakfast_time.setText((deciFormat.format(minHour) + ":" + deciFormat.format(minMinute) + "-" + deciFormat.format(maxHour) + ":" + deciFormat.format(maxMinute)));
                //leftIndexValue.setText(deciFormat.format(minHour) + ":" + deciFormat.format(minMinute));
                //  rightIndexValue.setText(deciFormat.format(maxHour) + ":" + deciFormat.format(maxMinute));
            }
        });*/
       // tv_breakfast_time.setText(breakfast_start+"-"+breakfast_end);
    }

    private void rangebarLunch() {
       /* rangebar_lunch.setTickCount(25 * 4);//SMALLEST_HOUR_FRACTION = 4;
        rangebar_lunch.setTickHeight(20);
        rangebar_lunch.setThumbRadius(10);
        rangebar_lunch.setConnectingLineWeight(3);
        // rangebar.setTickCount(11);
        rangebar_lunch.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                DecimalFormat deciFormat = new DecimalFormat("00");
                tv_lunch_time.setText("" + leftThumbIndex + "-" + "" + rightThumbIndex);
                //  leftIndexValue.setText("" + leftThumbIndex);
                //  rightIndexValue.setText("" + rightThumbIndex);

                minHour = leftThumbIndex / 4;
                minMinute = 4 * (leftThumbIndex % 4);
                maxHour = rightThumbIndex / 4;
                maxMinute = 30 + (rightThumbIndex - rightThumbIndex);
                tv_lunch_time.setText(minHour + ":" + minMinute + "-" + maxHour + ":" + maxMinute);
                //leftIndexValue.setText(minHour + ":" + minMinute);
                // rightIndexValue.setText(maxHour + ":" + maxMinute);
                tv_lunch_time.setText((deciFormat.format(minHour) + ":" + deciFormat.format(minMinute) + "-" + deciFormat.format(maxHour) + ":" + deciFormat.format(maxMinute)));
                //leftIndexValue.setText(deciFormat.format(minHour) + ":" + deciFormat.format(minMinute));
                //  rightIndexValue.setText(deciFormat.format(maxHour) + ":" + deciFormat.format(maxMinute));
            }
        });*/


        rangebar_lunch.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @Nullable
            @Override
            public String getLabelTextForPosition(SimpleRangeView simpleRangeView, int i, SimpleRangeView.State state) {
                return labelsLunch[i];
            }
        });
        rangebar_lunch.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(SimpleRangeView simpleRangeView, int i) {
               lunch_start=labelsLunch[i];
                tv_lunch_time.setText(lunch_start+"-"+lunch_end);
            }

            @Override
            public void onEndRangeChanged(SimpleRangeView simpleRangeView, int i) {
                lunch_end=labelsLunch[i];
                tv_lunch_time.setText(lunch_start+"-"+lunch_end);
            }
        });

        rangebar_lunch.setActiveLineColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_lunch.setActiveThumbColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_lunch.setActiveLabelColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_lunch.setActiveThumbLabelColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_lunch.setActiveFocusThumbColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_lunch.setActiveFocusThumbAlpha(0.26f);

    }

    private void rangebarDinner() {
      /*  rangebar_dinner.setTickCount(25 * 4);//SMALLEST_HOUR_FRACTION = 4;
        rangebar_dinner.setTickHeight(20);
        rangebar_dinner.setThumbRadius(10);
        rangebar_dinner.setConnectingLineWeight(3);
        // rangebar.setTickCount(11);
        rangebar_dinner.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                DecimalFormat deciFormat = new DecimalFormat("00");
                tv_dinner_time.setText("" + leftThumbIndex + "-" + "" + rightThumbIndex);
                //  leftIndexValue.setText("" + leftThumbIndex);
                //  rightIndexValue.setText("" + rightThumbIndex);

                minHour = leftThumbIndex / 4;
                minMinute = 4 * (leftThumbIndex % 4);
                maxHour = rightThumbIndex / 4;
                maxMinute = 30 + (rightThumbIndex - rightThumbIndex);
                tv_dinner_time.setText(minHour + ":" + minMinute + "-" + maxHour + ":" + maxMinute);
                //leftIndexValue.setText(minHour + ":" + minMinute);
                // rightIndexValue.setText(maxHour + ":" + maxMinute);
                tv_dinner_time.setText((deciFormat.format(minHour) + ":" + deciFormat.format(minMinute) + "-" + deciFormat.format(maxHour) + ":" + deciFormat.format(maxMinute)));
                //leftIndexValue.setText(deciFormat.format(minHour) + ":" + deciFormat.format(minMinute));
                //  rightIndexValue.setText(deciFormat.format(maxHour) + ":" + deciFormat.format(maxMinute));
            }
        });*/
        rangebar_dinner.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @Nullable
            @Override
            public String getLabelTextForPosition(SimpleRangeView simpleRangeView, int i, SimpleRangeView.State state) {
                return labelsDinner[i];
            }
        });
        rangebar_dinner.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(SimpleRangeView simpleRangeView, int i) {
                dinner_start=labelsDinner[i];
                tv_dinner_time.setText(dinner_start+"-"+dinner_end);

            }

            @Override
            public void onEndRangeChanged(SimpleRangeView simpleRangeView, int i) {
                dinner_end=labelsDinner[i];
                tv_dinner_time.setText(dinner_start+"-"+dinner_end);
            }
        });

        rangebar_dinner.setActiveLineColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_dinner.setActiveThumbColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_dinner.setActiveLabelColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_dinner.setActiveThumbLabelColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_dinner.setActiveFocusThumbColor(getResources().getColor(R.color.colorPrimaryDark));
        rangebar_dinner.setActiveFocusThumbAlpha(0.26f);
    }

    @Nullable
    @Override
    public String getLabelTextForPosition(SimpleRangeView simpleRangeView, int i, SimpleRangeView.State state) {
        return labels[i];
    }

    @Override
    public void onStartRangeChanged(SimpleRangeView simpleRangeView, int i) {
breakfast_start=labels[i];
        tv_breakfast_time.setText(breakfast_start+"-"+breakfast_end);
    }

    @Override
    public void onEndRangeChanged(SimpleRangeView simpleRangeView, int i) {
breakfast_end=labels[i];
        tv_breakfast_time.setText(breakfast_start+"-"+breakfast_end);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
