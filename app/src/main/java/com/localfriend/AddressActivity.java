package com.localfriend;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.localfriend.application.MyApp;
import com.localfriend.application.SingleInstance;
import com.localfriend.model.User;
import com.localfriend.utils.AppConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class AddressActivity extends CustomActivity implements CustomActivity.ResponseCallback {
    private Toolbar toolbar;
    private RadioButton rd_btn_home, rd_btn_office, rd_btn_other;
    private EditText edt_full_name, edt_city, edt_area, edt_colony, edt_flat_no, edt_phone_no, edt_pin_no;
    private TextView txt_update;
    private String type = "Home";
    private User user;
    private boolean isUpdate = false;
    private com.localfriend.model.Address updatingAddress = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        user = MyApp.getApplication().readUser();
        setContentView(R.layout.activity_address);
        toolbar = findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("Address");
        actionBar.setTitle("");
        isUpdate = getIntent().getBooleanExtra(AppConstant.EXTRA_1, false);
        updatingAddress = SingleInstance.getInstance().getUpdatingAddress();
        setupUiElements();
    }

    private void setupUiElements() {
        rd_btn_home = findViewById(R.id.rd_btn_home);
        rd_btn_office = findViewById(R.id.rd_btn_office);
        rd_btn_other = findViewById(R.id.rd_btn_other);
        edt_phone_no = findViewById(R.id.edt_phone_no);
        edt_flat_no = findViewById(R.id.edt_flat_no);
        edt_colony = findViewById(R.id.edt_colony);
        edt_area = findViewById(R.id.edt_area);
        edt_city = findViewById(R.id.edt_city);
        edt_pin_no = findViewById(R.id.edt_pin_no);
        edt_full_name = findViewById(R.id.edt_full_name);
        txt_update = findViewById(R.id.txt_update);

        edt_full_name.setText(user.getUserInfo().getFullName());
        edt_phone_no.setText(user.getUserInfo().getMobileNumber());
        edt_city.setText("Jaipur");

        if (isUpdate) {
            txt_update.setText("Update");
        } else {
            txt_update.setText("Save");
        }

        setTouchNClick(R.id.rd_btn_home);
        setTouchNClick(R.id.rd_btn_office);
        setTouchNClick(R.id.rd_btn_other);
        setTouchNClick(R.id.txt_update);

        if (isUpdate) {
            edt_full_name.setText(updatingAddress.getAddName());
            edt_phone_no.setText(updatingAddress.getAddContactNo());
            edt_city.setText(updatingAddress.getAddCity());
            edt_area.setText(updatingAddress.getAddDetails2());
            edt_colony.setText(updatingAddress.getAddDetails1());
            edt_flat_no.setText(updatingAddress.getAddDetails());
            edt_pin_no.setText(updatingAddress.getAddZipCode());
        }
    }


    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.rd_btn_home) {
            type = "Home";
            rd_btn_home.setChecked(true);
            rd_btn_home.setTextColor(getResources().getColor(R.color.blue_text));
            rd_btn_office.setChecked(false);
            rd_btn_home.setTextColor(getResources().getColor(R.color.black));
            rd_btn_other.setChecked(false);
            rd_btn_other.setTextColor(getResources().getColor(R.color.black));

        } else if (v.getId() == R.id.rd_btn_office) {
            type = "Office";
            rd_btn_home.setChecked(false);
            rd_btn_home.setTextColor(getResources().getColor(R.color.black));
            rd_btn_office.setChecked(true);
            rd_btn_office.setTextColor(getResources().getColor(R.color.blue_text));
            rd_btn_other.setChecked(false);
            rd_btn_other.setTextColor(getResources().getColor(R.color.black));

        } else if (v.getId() == R.id.rd_btn_other) {
            type = "Other";
            rd_btn_home.setChecked(false);
            rd_btn_home.setTextColor(getResources().getColor(R.color.black));
            rd_btn_office.setChecked(false);
            rd_btn_office.setTextColor(getResources().getColor(R.color.black));
            rd_btn_other.setChecked(true);
            rd_btn_other.setTextColor(getResources().getColor(R.color.blue_text));

        } else if (v == txt_update) {
            if (edt_full_name.getText().toString().isEmpty()) {
                edt_full_name.setError("Please enter your name");
                return;
            }
            if (edt_phone_no.getText().toString().isEmpty() || edt_phone_no.length() < 10) {
                AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                b.setTitle("Alert");
                b.setIcon(R.drawable.home_icon);
                b.setMessage("The number you have entered is not valid, do you want to use your default number instead?");
                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int which) {
                        edt_phone_no.setText(MyApp.getApplication().readUser().getUserInfo().getMobileNumber());
                        d.dismiss();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int which) {
                        d.dismiss();
                    }
                }).create().show();
                return;
            }
            JSONObject o = new JSONObject();
            try {
                o.put("access_token", MyApp.getApplication().readUser().getData().getAccess_token());
                o.put("addType", type);
                o.put("addName", edt_full_name.getText().toString());
                o.put("addContactNo", edt_phone_no.getText().toString());
                o.put("addDetails", edt_flat_no.getText().toString());
                o.put("addDetails1", edt_colony.getText().toString());
                o.put("addDetails2", edt_area.getText().toString());
                o.put("addZipCode", edt_pin_no.getText().toString());
                o.put("addCity", edt_city.getText().toString());
                o.put("addSatate", "Rajasthan");
                o.put("addCountry", "India");
                String address = edt_flat_no.getText().toString() + " "
                        + edt_colony.getText().toString() + " "
                        + edt_area.getText().toString() + " "
                        + edt_pin_no.getText().toString() + " "
                        + edt_city.getText().toString() + " ";
                LatLng latlng = getLocationFromAddress(getContext(), address);
                o.put("addLatitude", latlng.latitude + "");
                o.put("addLongitude", latlng.longitude + "");
                o.put("addIsActive", "true");
                o.put("addCreationTime", "");
                o.put("addEmailID", "");
                if (isUpdate) {
                    o.put("addID", updatingAddress.getAddID());
                } else {
                    o.put("addID", "0");
                }

                o.put("addDate", "");
                o.put("addCompany", "");
                o.put("addUserID", "");
                o.put("addModifiedTime", "");

                showLoadingDialog("");
                if (isUpdate) {
                    putCallJsonWithAuthorization(getContext(), AppConstant.BASE_URL + "Address", o, 2);
                } else {
                    postCallJsonWithAuthorization(getContext(), AppConstant.BASE_URL + "Address", o, 1);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private Context getContext() {
        return AddressActivity.this;
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
          try{
              Address location = address.get(0);
              location.getLatitude();
              location.getLongitude();
              p1 = new LatLng(location.getLatitude(), location.getLongitude());
          }catch (Exception e){
              p1 = new LatLng(0.0, 0.0);
          }

        } catch (IOException ex) {
            p1 = new LatLng(0.0, 0.0);
            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        dismissDialog();
        if (callNumber == 2) {
            MyApp.showMassage(getContext(), o.optString("message"));
            SingleInstance.getInstance().setUpdateDone(true);
            finish();
            return;
        }

        MyApp.showMassage(getContext(), o.optString("message"));
        if (o.optString("status").equals("success")) {
            SingleInstance.getInstance().setUpdateDone(true);
            finish();
        } else {

        }
    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {
        dismissDialog();
    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        dismissDialog();
        MyApp.popMessage("Error", "Time-out error occurred, please try again.", getContext());
    }

    @Override
    public void onErrorReceived(String error) {
        dismissDialog();
        MyApp.popMessage("Error", error, getContext());
    }
}
