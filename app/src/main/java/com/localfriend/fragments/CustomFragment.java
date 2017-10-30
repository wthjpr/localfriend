package com.localfriend.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.localfriend.CustomActivity;
import com.localfriend.R;
import com.localfriend.application.MyApp;
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

/**
 * Created by android3 on 05-Oct-16.
 */
public class CustomFragment extends Fragment implements View.OnClickListener {
    private ResponseCallback responseCallback;

    public void setResponseListener(ResponseCallback responseCallback) {
        this.responseCallback = responseCallback;
    }

    public View setTouchNClick(View v) {

        v.setOnClickListener(this);
        v.setOnTouchListener(CustomActivity.TOUCH);
        return v;
    }

    public View setClick(View v) {

        v.setOnClickListener(this);
        return v;
    }

    public void postCallJsonObject(Context c, String url, JSONObject params, String loadingMsg) {
        Log.d("URl:", url);
        Log.d("Request:", params.toString());
        StringEntity entity = null;
        try {
            entity = new StringEntity(params.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(30000);
        client.post(c, url, entity, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                responseCallback.onJsonObjectResponseReceived(response, 0);
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

    public void postCallJsonWithAuthorization(Context c, String url, JSONObject params, String loadingMsg) {
        Log.d("URl:", url);
        Log.d("Request:", params.toString());
        StringEntity entity = null;
        try {
            entity = new StringEntity(params.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization","bearer "+MyApp.getApplication().readUser().getData().getAccess_token());
        client.setTimeout(30000);
        client.post(c, url, entity, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                responseCallback.onJsonObjectResponseReceived(response, 0);
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
        client.setTimeout(30000);
        client.post(url, p, new JsonHttpResponseHandler() {

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

    public void getCallWithHeader(String url, final int callNumber) {
//        if (!TextUtils.isEmpty(loadingMsg))
//            MyApp.spinnerStart(c, loadingMsg);
        Log.d("URl:", url);
//        Log.d("Request:", p.toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization","bearer "+MyApp.getApplication().readUser().getData().getAccess_token());
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

    public void postCall10Sec(Context c, String url, RequestParams p, String loadingMsg, final int callNumber) {
        if (!TextUtils.isEmpty(loadingMsg))
            MyApp.spinnerStart(c, loadingMsg);
        Log.d("URl:", url);
        Log.d("Request:", p.toString());
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        client.post(url, p, new JsonHttpResponseHandler() {

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

    @Override
    public void onClick(View v) {

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

    public void showLoadingDialog(String message) {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_loader);

        TextView txt_load_message =  dialog.findViewById(R.id.txt_load_message);
        txt_load_message.setText(message);

//        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
//        dialogButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });

        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = -1;
        lp.height = -1;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }
}
