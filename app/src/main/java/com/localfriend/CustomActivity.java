package com.localfriend;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.localfriend.application.MyApp;
import com.localfriend.utils.TouchEffect;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * This is a common activity that all other activities of the app can extend to
 * inherit the common behaviors like setting a Theme to activity.
 */
public class CustomActivity extends AppCompatActivity implements OnClickListener {

    /**
     * Apply this Constant as touch listener for views to provide alpha touch
     * effect. The view must have a Non-Transparent background.
     */
    public static final TouchEffect TOUCH = new TouchEffect();

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        // setupActionBar();

        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        // getWindow()
        // .addFlags(
        // WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // //
        // getWindow().setStatusBarColor(getResources().getColor(R.color.main_color_dk));
        // }
    }

    /*
     * (non-Jav-adoc)
     *
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    /*
     * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method will setup the top title bar (Action bar) content and display
     * values. It will also setup the custom background theme for ActionBar. You
     * can override this method to change the behavior of ActionBar for
     * particular Activity
     */
    // protected void setupActionBar() {
    // final ActionBar actionBar = getSupportActionBar();
    //
    // if (actionBar == null)
    // return;
    // actionBar.setDisplayShowTitleEnabled(true);
    // // actionBar.setDisplayUseLogoEnabled(true);
    // // actionBar.setLogo(R.drawable.icon);
    // actionBar.setDisplayHomeAsUpEnabled(false);
    // actionBar.setHomeButtonEnabled(true);
    // actionBar.setTitle(null);
    //
    // }

	/*
     * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    /**
     * Sets the touch and click listeners for a view..
     *
     * @param id the id of View
     * @return the view
     */
    public View setTouchNClick(int id) {

        View v = setClick(id);
        v.setOnTouchListener(TOUCH);
        return v;
    }

    private CustomActivity.ResponseCallback responseCallback;

    public void setResponseListener(CustomActivity.ResponseCallback responseCallback) {
        this.responseCallback = responseCallback;
    }

    /**
     * Sets the click listener for a view.
     *
     * @param id the id of View
     * @return the view
     */
    public View setClick(int id) {

        View v = findViewById(id);
        v.setOnClickListener(this);
        return v;
    }

    public void postCallJsonWithAuthorization(Context c, String url, JSONObject params, final int callNumber) {
        Log.d("URl:", url);
        Log.d("Request:", params.toString());
        StringEntity entity = null;
        try {
            entity = new StringEntity(params.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "bearer " + MyApp.getApplication().readUser().getData().getAccess_token());
        client.setTimeout(30000);
        client.post(c, url, entity, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                responseCallback.onJsonObjectResponseReceived(response, callNumber);
                Log.d("Response:", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {
                MyApp.spinnerStop();
                responseCallback.onErrorReceived(getString(R.string.something_wrong));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                responseCallback.onErrorReceived(getString(R.string.something_wrong));
            }
        });

    }

    public void putCallJsonWithAuthorization(Context c, String url, JSONObject params, final int callNumber) {
        Log.d("URl:", url);
        Log.d("Request:", params.toString());
        StringEntity entity = null;
        try {
            entity = new StringEntity(params.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "bearer " + MyApp.getApplication().readUser().getData().getAccess_token());
        client.setTimeout(30000);
        client.put(c, url, entity, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                responseCallback.onJsonObjectResponseReceived(response, callNumber);
                Log.d("Response:", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {
                MyApp.spinnerStop();
                responseCallback.onErrorReceived(getString(R.string.something_wrong));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                responseCallback.onErrorReceived(getString(R.string.something_wrong));
            }
        });
    }

    public void postCall(Context c, String url, RequestParams p, String loadingMsg, final int callNumber) {
        if (!TextUtils.isEmpty(loadingMsg))
            MyApp.spinnerStart(c, loadingMsg);
        Log.d("URl:", url);
        Log.d("Request:", p.toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(60000);
        client.addHeader("Authorization", "bearer " + MyApp.getApplication().readUser().getData().getAccess_token());
        client.post(c, url, p, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                MyApp.spinnerStop();
                responseCallback.onJsonObjectResponseReceived(response, callNumber);
                Log.d("Response:", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                MyApp.spinnerStop();
                Log.d("error message:", throwable.getMessage());
                responseCallback.onErrorReceived(getString(R.string.something_wrong));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                MyApp.spinnerStop();
                Log.d("error message:", throwable.getMessage());
                responseCallback.onErrorReceived(getString(R.string.something_wrong));
            }
        });
    }

    public void postCall(Context c, String url, JSONObject object, String loadingMsg, final int callNumber) {
        if (!TextUtils.isEmpty(loadingMsg))
            MyApp.spinnerStart(c, loadingMsg);
        Log.d("URl:", url);
        Log.d("Request:", object.toString());
        StringEntity entity = null;
        try {
            entity = new StringEntity(object.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(30000);
        client.post(c, url, entity, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                MyApp.spinnerStop();
                Log.d("Response:", response.toString());
                try {
                    responseCallback.onJsonObjectResponseReceived(response, callNumber);
                } catch (Exception e) {
                    responseCallback.onErrorReceived(getString(R.string.no_data_avail));
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                MyApp.spinnerStop();
                if (statusCode == 0) {
                    responseCallback.onTimeOutRetry(callNumber);
                } else {
                    responseCallback.onErrorReceived(getString(R.string.something_wrong) + "_" + statusCode);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                MyApp.spinnerStop();
                if (statusCode == 0) {
                    responseCallback.onTimeOutRetry(callNumber);
                } else {
                    responseCallback.onErrorReceived(getString(R.string.something_wrong) + "_" + statusCode);
                }
            }
        });
    }

    public void deleteCall(Context c, String url, final int callNumber) {
//        if (!TextUtils.isEmpty(loadingMsg))
//            MyApp.spinnerStart(c, loadingMsg);
        Log.d("URl:", url);
//        Log.d("Request:", object.toString());
        StringEntity entity = null;
//        try {
//            entity = new StringEntity(object.toString());
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(30000);
        client.delete(c, url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                MyApp.spinnerStop();
                Log.d("Response:", response.toString());
                try {
                    responseCallback.onJsonObjectResponseReceived(response, callNumber);
                } catch (Exception e) {
                    responseCallback.onErrorReceived(getString(R.string.no_data_avail));
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                MyApp.spinnerStop();
                if (statusCode == 0) {
                    responseCallback.onTimeOutRetry(callNumber);
                } else {
                    responseCallback.onErrorReceived(getString(R.string.something_wrong) + "_" + statusCode);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                MyApp.spinnerStop();
                if (statusCode == 0) {
                    responseCallback.onTimeOutRetry(callNumber);
                } else {
                    responseCallback.onErrorReceived(getString(R.string.something_wrong) + "_" + statusCode);
                }
            }
        });
    }

    public void postCall10Sec(Context c, String url, RequestParams p, String loadingMsg, final int callNumber) {
        if (!TextUtils.isEmpty(loadingMsg))
            MyApp.spinnerStart(c, loadingMsg);
        Log.d("URl:", url);
        Log.d("Request:", p.toString());
        AsyncHttpClient client = new AsyncHttpClient();
        Header[] headers = new Header[2];
        Header h = new BasicHeader("Authorization", "bearer " + MyApp.getApplication().readUser().getData().getAccess_token());
        Header h1 = new BasicHeader("Content-Type", "application/x-www-form-urlencoded");
        headers[0] = h;
        headers[1] = h1;
        client.setTimeout(10000);
//        client.addHeader("Authorization", "bearer " + MyApp.getApplication().readUser().getData().getAccess_token());
//        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        client.post(c, url, headers, p, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                MyApp.spinnerStop();
                Log.d("Response:", response.toString());
                try {
                    responseCallback.onJsonObjectResponseReceived(response, callNumber);
                } catch (Exception e) {
                    responseCallback.onErrorReceived(getString(R.string.no_data_avail));
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                MyApp.spinnerStop();
                if (statusCode == 0) {
                    responseCallback.onTimeOutRetry(callNumber);
                } else {
                    responseCallback.onErrorReceived(getString(R.string.something_wrong) + "_" + statusCode);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                MyApp.spinnerStop();
                if (statusCode == 0) {
                    responseCallback.onTimeOutRetry(callNumber);
                } else {
                    responseCallback.onErrorReceived(getString(R.string.something_wrong) + "_" + statusCode);
                }
            }
        });
    }


    public interface ResponseCallback {
        void onJsonObjectResponseReceived(JSONObject o, int callNumber);

        void onJsonArrayResponseReceived(JSONArray a, int callNumber);

        void onTimeOutRetry(int callNumber);

        void onErrorReceived(String error);

    }

    private Dialog dialog;

    public void dismissDialog() {
        try {
            dialog.dismiss();
        } catch (Exception e) {
        }

    }

    public void getCallWithHeader(String url, final int callNumber) {
//        if (!TextUtils.isEmpty(loadingMsg))
//            MyApp.spinnerStart(c, loadingMsg);
        Log.d("URl:", url);
//        Log.d("Request:", p.toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "bearer " + MyApp.getApplication().readUser().getData().getAccess_token());
        client.setTimeout(30000);
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                MyApp.spinnerStop();
                Log.d("Response:", response.toString());
                try {
                    responseCallback.onJsonObjectResponseReceived(response, callNumber);
                } catch (Exception e) {
                    responseCallback.onErrorReceived(getString(R.string.no_data_avail));
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                MyApp.spinnerStop();
                if (statusCode == 0) {
                    responseCallback.onTimeOutRetry(callNumber);
                } else {
                    responseCallback.onErrorReceived(getString(R.string.something_wrong) + "_" + statusCode);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                MyApp.spinnerStop();
                if (statusCode == 0) {
                    responseCallback.onTimeOutRetry(callNumber);
                } else {
                    responseCallback.onErrorReceived(getString(R.string.something_wrong) + "_" + statusCode);
                }
            }
        });
    }


    public void getCallWithHeaderForJsonArray(String url, final int callNumber) {
//        if (!TextUtils.isEmpty(loadingMsg))
//            MyApp.spinnerStart(c, loadingMsg);
        Log.d("URl:", url);
//        Log.d("Request:", p.toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization", "bearer " + MyApp.getApplication().readUser().getData().getAccess_token());
        client.setTimeout(30000);
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {
                MyApp.spinnerStop();
                Log.d("Response:", response.toString());
                try {
                    responseCallback.onJsonArrayResponseReceived(response, callNumber);
                } catch (Exception e) {
                    responseCallback.onErrorReceived(getString(R.string.no_data_avail));
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                MyApp.spinnerStop();
                if (statusCode == 0) {
                    responseCallback.onTimeOutRetry(callNumber);
                } else {
                    responseCallback.onErrorReceived(getString(R.string.something_wrong) + "_" + statusCode);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                MyApp.spinnerStop();
                if (statusCode == 0) {
                    responseCallback.onTimeOutRetry(callNumber);
                } else {
                    responseCallback.onErrorReceived(getString(R.string.something_wrong) + "_" + statusCode);
                }
            }
        });
    }

    public void getCall(String url, String loadingMsg, final int callNumber) {
//        if (!TextUtils.isEmpty(loadingMsg))
//            MyApp.spinnerStart(c, loadingMsg);
        Log.d("URl:", url);
//        Log.d("Request:", p.toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(30000);
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                MyApp.spinnerStop();
                Log.d("Response:", response.toString());
                try {
                    responseCallback.onJsonObjectResponseReceived(response, callNumber);
                } catch (Exception e) {
                    responseCallback.onErrorReceived(getString(R.string.no_data_avail));
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                MyApp.spinnerStop();
                if (statusCode == 0) {
                    responseCallback.onTimeOutRetry(callNumber);
                } else {
                    responseCallback.onErrorReceived(getString(R.string.something_wrong) + "_" + statusCode);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                MyApp.spinnerStop();
                if (statusCode == 0) {
                    responseCallback.onTimeOutRetry(callNumber);
                } else {
                    responseCallback.onErrorReceived(getString(R.string.something_wrong) + "_" + statusCode);
                }
            }
        });
    }

    public void showLoadingDialog(String message) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_loader);

        TextView txt_load_message = dialog.findViewById(R.id.txt_load_message);
        txt_load_message.setText(message);

        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -1;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    public void showComingSoon() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_coming_soon);
        ImageView img_close = dialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -1;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }
}
