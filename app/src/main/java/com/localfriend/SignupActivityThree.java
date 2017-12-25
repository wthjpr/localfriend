package com.localfriend;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.localfriend.application.MyApp;
import com.localfriend.utils.AppConstant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import static java.sql.Types.NULL;

public class SignupActivityThree extends CustomActivity implements CustomActivity.ResponseCallback {
    private TextView tv_btn_next;
    private Toolbar toolbar;

    private String value;
    private String mb_no;
    //    private TextView txt_counter;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private boolean isRegister = false;
    private String countryId = "94";
    private boolean isProvider = false;
    private String phoneNumber;
    private String userName;
    private boolean isForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_three);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar_common);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_common);
        mTitle.setText("SIGN UP");
        actionBar.setTitle("");
        toolbar.setBackgroundResource(NULL);
        setResponseListener(this);
        RelativeLayout v = (RelativeLayout) findViewById(R.id.toolbar);
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
        userName = getIntent().getStringExtra("name");
        phoneNumber = getIntent().getStringExtra("phone");

        isForgot = getIntent().getBooleanExtra(AppConstant.EXTRA_1, false);

        mb_no = "+91" + phoneNumber;
        setupUiElement();
        showCounter();
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        if (code == null || code.isEmpty()) {
            MyApp.showMassage(getContext(),"Enter OTP please");
            return;

        }
        if (isForgot) {
            Intent intent = new Intent(getContext(), ForgotPasswordActivity.class);
            intent.putExtra("name", userName);
            intent.putExtra(AppConstant.EXTRA_2, phoneNumber);
            startActivity(intent);
            return;
        }
//        if (code.equals("111111")) {
//            Intent intent = new Intent(getContext(), SignupActivityFour.class);
//            intent.putExtra("name", userName);
//            intent.putExtra("phone", phoneNumber);
//            startActivity(intent);
//            return;
//        }


        try {
            // [START verify_with_code]
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            // [END verify_with_code]
            signInWithPhoneAuthCredential(credential);
        } catch (Exception e) {
        }
    }

    private void setupUiElement() {

        setTouchNClick(R.id.tv_btn_next);


        tv_btn_next = (TextView) findViewById(R.id.tv_btn_next);

//        txt_counter = (TextView) findViewById(R.id.txt_counter);

        Shader textShader = new LinearGradient(0, 0, 0, 50,
                new int[]{Color.parseColor("#3CBEA3"), Color.parseColor("#1D6D9E")},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        tv_btn_next.getPaint().setShader(textShader);

        final PinEntryEditText pinEntry = findViewById(R.id.txt_pin_entry);
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    enteredPin = str.toString();
                    verifyPhoneNumberWithCode(mVerificationId, enteredPin);
                }
            });
        }
    }

    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_btn_next) {


//            String code = edt_code.getText().toString();
//            if (TextUtils.isEmpty(edt_code.getText().toString())) {
//                edt_code.setError("Enter the verification code");
//                return;
//            }
            verifyPhoneNumberWithCode(mVerificationId, enteredPin);
        }

    }

    private String enteredPin = "";

    private void showCounter() {

//        CountDownTimer mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                //this will be called every second.
//                txt_counter.setText(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) + ":" + TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
//            }
//
//            @Override
//            public void onFinish() {
//                txt_counter.setText("00:00");
//                //txt_resend.setVisibility(View.VISIBLE);
//                //you are good to go.
//                //30 seconds passed.
//            }
//        };
//        mCountDownTimer.start();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d("phone", "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("phone", "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("phone", "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                // Save verification ID and resending token so we can use them later
//                mVerificationId = verificationId;
//                mResendToken = token;

                // ...
            }
        };

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d("authStateChange", "");
            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mb_no,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("phone", "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            FirebaseAuth.getInstance().signOut();
                            if (isForgot) {
                                Intent intent = new Intent(getContext(), ForgotPasswordActivity.class);
                                intent.putExtra("name", userName);
                                intent.putExtra(AppConstant.EXTRA_2, phoneNumber);
                                startActivity(intent);
                                return;
                            }
                            Intent intent = new Intent(getContext(), SignupActivityFour.class);
                            intent.putExtra("name", userName);
                            intent.putExtra("phone", phoneNumber);
                            startActivity(intent);
                            return;


                        } else {
                            if (isForgot ) {
                                Intent intent = new Intent(getContext(), ForgotPasswordActivity.class);
                                intent.putExtra("name", userName);
                                intent.putExtra(AppConstant.EXTRA_2, phoneNumber);
                                startActivity(intent);
                                return;
                            }
//                            if (enteredPin.toString().equals("111111")) {
//
//
//                                Intent intent = new Intent(getContext(), SignupActivityFour.class);
//                                intent.putExtra("name", userName);
//                                intent.putExtra("phone", phoneNumber);
//                                startActivity(intent);
//                                return;
//
//
//                            }
                            // Sign in failed, display a message and update the UI
                            Log.w("phone", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                MyApp.popMessage("Alert!", "Please enter a valid code that sent to " +
                                        " " + mb_no + ".\nThank you", getContext());
                            }
                        }
                    }
                });
    }


    private Context getContext() {
        return SignupActivityThree.this;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {

    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {

    }

    @Override
    public void onTimeOutRetry(int callNumber) {

    }

    @Override
    public void onErrorReceived(String error) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
