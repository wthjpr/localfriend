package com.localfriend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.localfriend.application.AppConstants;
import com.localfriend.application.MyApp;
import com.localfriend.utils.AppConstant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.paytm.pgsdk.PaytmConstants;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class OrganisationActivity extends CustomActivity implements CustomActivity.ResponseCallback {

    private Toolbar toolbar;
    private EditText edt_company_name, edt_authority_name, edt_phone, edt_thali_per_day, edt_address, edt_note;
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        setContentView(R.layout.activity_organisation);
        toolbar = findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("Organisation");
        actionBar.setTitle("");
        setupUiElements();
    }

    private void setupUiElements() {
        edt_company_name = findViewById(R.id.edt_company_name);
        edt_authority_name = findViewById(R.id.edt_authority_name);
        edt_phone = findViewById(R.id.edt_phone);
        edt_thali_per_day = findViewById(R.id.edt_thali_per_day);
        edt_address = findViewById(R.id.edt_address);
        edt_note = findViewById(R.id.edt_note);

        setTouchNClick(R.id.txt_send_request);
        setTouchNClick(R.id.paytm_btn);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.txt_send_request) {
            if (edt_company_name.getText().toString().isEmpty()) {
                edt_company_name.setError("Enter your company name");
                return;
            }
            if (edt_authority_name.getText().toString().isEmpty()) {
                edt_authority_name.setError("Enter authority name");
                return;
            }
            if (edt_phone.getText().toString().isEmpty()) {
                edt_phone.setError("Enter phone number");
                return;
            }
            if (edt_thali_per_day.getText().toString().isEmpty()) {
                edt_thali_per_day.setError("Enter quantity");
                return;
            }
            if (edt_address.getText().toString().isEmpty()) {
                edt_address.setError("Enter your address");
                return;
            }

            JSONObject o = new JSONObject();
            try {
                o.put("name", edt_company_name.getText().toString());
                o.put("authorityname", edt_authority_name.getText().toString());
                o.put("phonenumber", edt_phone.getText().toString());
                o.put("thaliqty", edt_thali_per_day.getText().toString());
                o.put("address", edt_address.getText().toString());
                o.put("specialnote", edt_note.getText().toString());
                showLoadingDialog("");
                postCallJsonWithAuthorization(getContext(), AppConstant.BASE_URL + "Order/Company", o, 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (v.getId() == R.id.paytm_btn) {
            orderId = "10";
            createPay("10",orderId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!getIntent().getBooleanExtra(AppConstant.EXTRA_1, false)) {
            startActivity(new Intent(getContext(), MainActivity.class));
            finishAffinity();
        }

    }

    private Context getContext() {
        return OrganisationActivity.this;
    }


    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {
        if (o.optString("status").equals("success")) {
            MyApp.popFinishableMessage("LocalFriend", o.optString("message"), OrganisationActivity.this);
        }
        dismissDialog();
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


    private void createPay(String orderId, String payment) {
        MyApp.spinnerStart(getContext(), "Please wait...");
        String url = "http://floter.in/floterapi/paytm/generateChecksum.php";
        RequestParams p = new RequestParams();
        p.put(PaytmConstants.MERCHANT_ID, "FLOTER55912639344993");
        p.put("ORDER_ID", orderId);
        p.put("CUST_ID", "User_" + MyApp.getApplication().readUser().getUserInfo().getId());
        p.put("INDUSTRY_TYPE_ID", "Retail109");
        p.put("CHANNEL_ID", "WAP");
        p.put("TXN_AMOUNT", payment);
        p.put("WEBSITE", "FLOTERWAP");
        p.put("CALLBACK_URL", "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp");
        p.put("EMAIL", MyApp.getApplication().readUser().getUserInfo().getEmail());
        p.put("MOBILE_NO", MyApp.getApplication().readUser().getUserInfo().getMobileNumber());
        Log.d("URl:", url);
        Log.d("Request:", p.toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(30000);
        client.get(url, p, new C10994());
    }

    class C10994 extends JsonHttpResponseHandler {
        C10994() {
        }

        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            MyApp.spinnerStop();
            Log.d("Response:", response.toString());
            try {
                if (response.optString("status").equals("ok")) {
                    createPaymentFlow(response.getJSONObject("response").optString("CHECKSUMHASH"));
                } else {
                    MyApp.popMessage("Error", response.optString("error"), getContext());
                }
            } catch (JSONException e) {
                MyApp.showMassage(getContext(), "Parsing error");
                e.printStackTrace();
            }
        }

        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            MyApp.spinnerStop();
            if (statusCode == 0) {
                MyApp.popMessage("Error!", "Time-out error occurred.\nPlease try again", getContext());
            } else {
                MyApp.popMessage("Error!", "Error_" + statusCode + "Some error occurred", getContext());
            }
        }

        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            MyApp.spinnerStop();
            if (statusCode == 0) {
                MyApp.popMessage("Error!", "Time-out error occurred.\nPlease try again", getContext());
            } else {
                MyApp.popMessage("Error!", "Error_" + statusCode + "Some error occurred", getContext());
            }
        }
    }

    private void createPaymentFlow(String checksum) {
        PaytmPGService Service = PaytmPGService.getProductionService();
        Map<String, String> paramMap = new HashMap();
        paramMap.put("ORDER_ID", orderId);
        paramMap.put(PaytmConstants.MERCHANT_ID, "FLOTER55912639344993");
        paramMap.put("CUST_ID", "User_" + MyApp.getApplication().readUser().getUserInfo().getId());
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail109");
        paramMap.put("WEBSITE", "FLOTERWAP");
        paramMap.put("TXN_AMOUNT", "10");
        paramMap.put("CALLBACK_URL", "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp");
        paramMap.put("EMAIL", MyApp.getApplication().readUser().getUserInfo().getEmail());
        paramMap.put("MOBILE_NO", MyApp.getApplication().readUser().getUserInfo().getMobileNumber());
        paramMap.put("CHECKSUMHASH", checksum);
        Service.initialize(new PaytmOrder(paramMap), null);
        Service.startPaymentTransaction(this, true, true, new C09585());
    }

    class C09585 implements PaytmPaymentTransactionCallback {

        class C11001 extends JsonHttpResponseHandler {
            C11001() {
            }

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                MyApp.spinnerStop();
                Log.d("Response:", response.toString());
                try {
                    Log.d("Response:", response.getJSONObject("response").toString());
                    checkStatus(response.getJSONObject("response").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                MyApp.spinnerStop();
                MyApp.popMessage("Error", "Payment did not processed\nPlease try again", getContext());
            }

            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                MyApp.spinnerStop();
                MyApp.popMessage("Error", "Payment did not processed\nPlease try again", getContext());
            }
        }

        C09585() {
        }

        public void someUIErrorOccurred(String inErrorMessage) {
            Log.d("LOG", "UI Error Occur.");
            MyApp.popMessage("Error!", "UI Error Occurred for the paytm payment flow, Do you want to pay with paytm?", getContext());
        }

        public void onTransactionResponse(Bundle inResponse) {
            if (!inResponse.getString("RESPMSG").contains("Successful")) {
                MyApp.popMessage("Error", inResponse.getString("RESPMSG"), getContext());
                return;
            }
            Log.d("LOG", "Payment Transaction : " + inResponse);
            MyApp.spinnerStart(getContext(), "Please wait...");
            String url = "http://floter.in/floterapi/paytm/getTransactionStatus.php";
            RequestParams p = new RequestParams();
            p.put(PaytmConstants.MERCHANT_ID, "FLOTER55912639344993");
            p.put("ORDER_ID", orderId);
            Log.d("URl:", url);
            Log.d("Request:", p.toString());
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(30000);
            client.get(url, p, new C11001());
        }

        public void networkNotAvailable() {
            Log.d("LOG", "Network Error Occur.");
            MyApp.popMessage("Error!", "Network Error Occurred for the paytm payment flow.", getContext());
        }

        public void clientAuthenticationFailed(String inErrorMessage) {
            MyApp.popMessage("Error!", "Client authentication Error Occurred for the paytm payment flow.", getContext());
        }

        public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
            MyApp.popMessage("Error!", "Some Error Occurred for the paytm payment flow.", getContext());
        }

        public void onBackPressedCancelTransaction() {
            MyApp.popMessage("Error!", "Payment cancelled for the paytm payment flow by you.", getContext());
        }

        public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
            MyApp.popMessage("Error!", "Paytm Transaction failed.", getContext());
        }
    }

    class C10983 extends JsonHttpResponseHandler {
        C10983() {
        }

        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            MyApp.spinnerStop();
            Log.d("Response:", response.toString());
            if (response.optString("status").equals("OK")) {
//                if (!isFromHistory) {
//                    MyApp.setSharedPrefString("SHOW_PAY", "NO");
//                    MyApp.setSharedPrefString(AppConstants.PAYBLE_TRIP_ID, "");
//                }
//                ll_feedback.setVisibility(View.VISIBLE);
//                slideUp.show();
            }
        }

        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            MyApp.spinnerStop();
        }

        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            MyApp.spinnerStop();
        }
    }

    private void checkStatus(String json) {
        MyApp.spinnerStart(getContext(), "Validating Payment...");
        String url = "https://secure.paytm.in/oltp/HANDLER_INTERNAL/getTxnStatus";
        RequestParams p = new RequestParams();
        p.put("JsonData", json);
        Log.d("URl:", url);
        Log.d("Request:", p.toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(30000);
        client.get(url, p, new C11016());
    }
    class C11016 extends JsonHttpResponseHandler {
        C11016() {
        }

        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            MyApp.spinnerStop();
            Log.d("Response:", response.toString());
            if (response.optString(PaytmConstants.STATUS).equals("TXN_SUCCESS")) {
                updatePaymentApi(response.optString(PaytmConstants.TRANSACTION_ID), response.optString(PaytmConstants.ORDER_ID));
                return;
            }
//            ll_feedback.setVisibility(View.VISIBLE);
//            slideUp.show();
        }

        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            MyApp.spinnerStop();
//            ll_feedback.setVisibility(View.VISIBLE);
//            slideUp.show();
        }

        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            MyApp.spinnerStop();
//            ll_feedback.setVisibility(View.VISIBLE);
//            slideUp.show();
        }
    }

    private void updatePaymentApi(final String txnId, final String orderId) {
        // do something for payment done info to be send on server
    }
}
