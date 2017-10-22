package com.localfriend;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.localfriend.application.MyApp;
import com.localfriend.utils.AppConstant;

import java.util.Locale;

public class LoginSignupActivity extends CustomActivity implements
        TextToSpeech.OnInitListener {
    private TextView tv_already_account, tv_lets_get_started, tv_hello_label, tv_help;
    private static final int SPLASH_DURATION_MS = 800;
    private static final int SPLASH_DURATION_two = 2000;
    private static final int SPLASH_DURATION_three = 3000;
    private static final int SPLASH_DURATION_four = 4000;
    private static final int SPLASH_DURATION_five = 5000;
    private Handler mHandler = new Handler();
    private ImageView img_logo;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (MyApp.getStatus(AppConstant.IS_LOGIN)) {
            startActivity(new Intent(LoginSignupActivity.this, MainActivity.class));
            finish();
            return;
        }
        Shader textShader=new LinearGradient(0, 0, 0, 50,
                new int[]{Color.parseColor("#3CBEA3"),Color.parseColor("#1D6D9E")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);

        tts = new TextToSpeech(this, this);
        setContentView(R.layout.activity_login_signup);
        img_logo =  findViewById(R.id.img_logo);
        img_logo.setVisibility(View.GONE);
        tv_already_account =  findViewById(R.id.tv_already_account);
        tv_already_account.setVisibility(View.GONE);
        tv_already_account.setText(Html.fromHtml("<u>Already have an account ? </u>"));
        tv_lets_get_started =  findViewById(R.id.tv_lets_get_started);
        tv_lets_get_started.setVisibility(View.GONE);
        tv_lets_get_started.getPaint().setShader(textShader);
        tv_hello_label =  findViewById(R.id.tv_hello_label);
        tv_hello_label.setVisibility(View.GONE);
        tv_help =  findViewById(R.id.tv_help);
        tv_help.setVisibility(View.GONE);
        tv_already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginSignupActivity.this, SigninActivityOne.class));
            }
        });
        tv_lets_get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginSignupActivity.this, SignupActivityOne.class));
            }
        });
        LinearLayout v =  findViewById(R.id.ll_main);
        // transparent statusbar for marshmallow and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (v != null) {
                v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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
            MyApp.setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
//        if (Build.VERSION.SDK_INT >= 21) {
//
//            // Set the status bar to dark-semi-transparentish
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//
//            // Set paddingTop of toolbar to height of status bar.
//            // Fixes statusbar covers toolbar issue
//            View v = findViewById(R.id.imageview);
//            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) v.getLayoutParams();
//            lp.setMargins(0, MyApp.getApplication().getStatusBarHeight(), 0, -MyApp.getApplication().getStatusBarHeight());
////            v.setPadding(getStatusBarHeight(), getStatusBarHeight(), getStatusBarHeight(), 0);
//        }

        mHandler.postDelayed(new Runnable() {


            @Override
            public void run() {

                img_logo.setVisibility(View.VISIBLE);

            }
        }, SPLASH_DURATION_MS);


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_hello_label.setVisibility(View.VISIBLE);
            }
        }, SPLASH_DURATION_two);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_help.setVisibility(View.VISIBLE);
            }
        }, SPLASH_DURATION_three);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_lets_get_started.setVisibility(View.VISIBLE);
            }
        }, SPLASH_DURATION_four);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_already_account.setVisibility(View.VISIBLE);
            }
        }, SPLASH_DURATION_five);


    }

    @Override
    protected void onStop() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut("Hello there, I am your local friend. I am here to help you with anything, anytime. Let's get started.");
            }

        } else {
            Log.e("TTS", "Initialization Failed!");
        }
    }

    private void speakOut(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
