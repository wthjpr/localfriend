package com.localfriend;

import android.*;
import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.localfriend.application.MyApp;
import com.localfriend.model.User;
import com.localfriend.utils.AppConstant;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends CustomActivity implements CustomActivity.ResponseCallback, DatePickerDialog.OnDateSetListener {

    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private TextView txt_date;
    private TextView txt_update;
    private TextView txt_logout;
    private EditText edt_name;
    //    private EditText edt_i_am;
    private EditText edt_mail;
    private TextView edt_phone;
    private EditText edt_address;
    private Spinner spinner_gender;
    private Spinner spinner_im;
    private CircleImageView img_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResponseListener(this);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
//        TextView mTitle = toolbar.findViewById(R.id.toolbar_title_common);
//        mTitle.setText("Profile");
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");
        collapsingToolbarLayout.setCollapsedTitleGravity(View.ACCESSIBILITY_LIVE_REGION_ASSERTIVE);
        collapsingToolbarLayout.setScrimsShown(true, true);
        actionBar.setTitle("");
        setContentElements();
        toolbarTextAppearance();
        setImagePath("profile_pic");

        showLoadingDialog("");
        getCallWithHeader(AppConstant.BASE_URL + "Account/UserProfile", 1);

    }

    private void toolbarTextAppearance() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        setImagePath("profile_pic");
    }

    private void setContentElements() {
        txt_date = findViewById(R.id.txt_date);
        txt_logout = findViewById(R.id.txt_logout);
        edt_name = findViewById(R.id.edt_name);
        edt_mail = findViewById(R.id.edt_mail);
        edt_phone = findViewById(R.id.edt_phone);
        edt_address = findViewById(R.id.edt_address);
        spinner_gender = findViewById(R.id.spinner_gender);
        spinner_im = findViewById(R.id.spinner_im);
        img_profile = findViewById(R.id.img_profile);
        txt_update = findViewById(R.id.txt_update);
//        edt_i_am = findViewById(R.id.edt_i_am);

        setTouchNClick(R.id.txt_date);
        setTouchNClick(R.id.txt_logout);
        setTouchNClick(R.id.img_profile);
        setTouchNClick(R.id.txt_update);

        User u = MyApp.getApplication().readUser();
        edt_name.setText(u.getUserInfo().getFullName());
        edt_mail.setText(u.getUserInfo().getEmail());
        edt_phone.setText(u.getUserInfo().getMobileNumber());
        edt_address.setText(u.getData().getAddress());
//        edt_i_am.setText(u.getData().getExtra2());

        Glide.with(this).load(MyApp.getApplication().readUser().getData().getProfileImageURL()).placeholder(R.drawable.default_pic).centerCrop().into(img_profile);
        String gender_arraylist[] = {"Male", "Female"};
        ArrayAdapter adapter = new ArrayAdapter<String>(ProfileActivity.this,
                R.layout.custom_spinner, gender_arraylist) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                return v;
            }

            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                //change height and width or text size and colour here

                return v;
            }
        };

        String im_list[] = {"Student", "Employee"};
        ArrayAdapter adapter_im = new ArrayAdapter<String>(ProfileActivity.this,
                R.layout.custom_spinner, im_list) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                return v;
            }

            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                //change height and width or text size and colour here

                return v;
            }
        };
        spinner_gender.setAdapter(adapter);
        spinner_im.setAdapter(adapter_im);
        try {
            if (u.getData().getGender().equals("Female")) {
                spinner_gender.setSelection(1);
            }
        } catch (Exception e) {
        }

        try {
            if (u.getData().getExtra2().equals("Employee")) {
                spinner_im.setSelection(1);
            }
        } catch (Exception e) {
        }

        txt_date.setText(u.getData().getdOB());

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == txt_date) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(), this, year, month, day);
            datePickerDialog.show();

        } else if (v == txt_logout) {


            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        FirebaseInstanceId.getInstance().deleteInstanceId();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                }
            }.execute();


            JSONObject o = new JSONObject();
            MyApp.spinnerStart(getContext(), "Logging you out...");
            postCallJsonWithAuthorization(getContext(), AppConstant.BASE_URL + "Account/Logout", o, 5);


        } else if (v == img_profile) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Log.v("", "Permission is granted");
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    } else {
                        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
                        return;
                    }

                    //File write logic here
                    AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                    b.setTitle("Choose Image").setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int which) {
                            d.dismiss();
                            takePicture();
                        }
                    }).setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int which) {
                            d.dismiss();
                            openGallery();
                        }
                    }).create().show();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
                    }
                    return;
                }
                //do your check here
            } else {
                AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                b.setTitle("Choose Image").setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int which) {
                        d.dismiss();
                        takePicture();
                    }
                }).setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int which) {
                        d.dismiss();
                        openGallery();
                    }
                }).create().show();
            }

        } else if (v == txt_update) {

            JSONObject o = new JSONObject();
            try {
                o.put("userName", MyApp.getApplication().readUser().getUserInfo().getUserName());
                o.put("fullName", edt_name.getText().toString());
                o.put("email", edt_mail.getText().toString());
                o.put("mobileNumber", MyApp.getApplication().readUser().getUserInfo().getMobileNumber());
                o.put("gender", spinner_gender.getSelectedItem().toString());
                o.put("dOB", txt_date.getText().toString());
                o.put("altMobileNo", edt_phone.getText().toString());
                o.put("profileImageURL", MyApp.getApplication().readUser().getData().getProfileImageURL() + "");
                o.put("address", edt_address.getText().toString());
                o.put("extra2", spinner_im.getSelectedItem().toString());
                showLoadingDialog("");
                postCallJsonWithAuthorization(getContext(), AppConstant.BASE_URL + "Account/UserProfile", o, 3);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private Context getContext() {
        return ProfileActivity.this;
    }

    @Override
    public void onJsonObjectResponseReceived(JSONObject o, int callNumber) {

        if (callNumber == 1) {
            dismissDialog();
            try {
                User.Data data = new Gson().fromJson(o.getJSONObject("data").toString(), User.Data.class);
                User u = MyApp.getApplication().readUser();
                u.getData().setAddress(data.getAddress());
                u.getData().setAltMobileNo(data.getAltMobileNo());
                u.getData().setdOB(data.getdOB());
                u.getData().setEmail(data.getEmail());
                u.getData().setGender(data.getGender());
                u.getData().setProfileImageURL(data.getProfileImageURL());
                u.getData().setExtra2(data.getExtra2());
                u.getData().setFullName(data.getFullName());
//                edt_i_am.setText(u.getData().getExtra2());
                edt_name.setText(u.getData().getFullName());
                edt_mail.setText(u.getData().getEmail());
                edt_address.setText(u.getData().getAddress());
                txt_date.setText(u.getData().getdOB());
                Glide.with(this).load(u.getData().getProfileImageURL()).placeholder(R.drawable.default_pic).centerCrop().into(img_profile);
                MyApp.getApplication().writeUser(u);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (callNumber == 2) {
//            MyApp.showMassage(getContext(), o.optString("message"));
            getCallWithHeader(AppConstant.BASE_URL + "Account/UserProfile", 1);
        } else if (callNumber == 3) {
//            MyApp.showMassage(getContext(), o.optString("message"));
            getCallWithHeader(AppConstant.BASE_URL + "Account/UserProfile", 1);
        } else if (callNumber == 5) {
            MyApp.spinnerStop();
//            {"status":"Success","message":"Your Account Logout"}
            if (o.optString("status").equals("Success")) {
//                MyApp.showMassage(getContext(), o.optString("message"));
                MyApp.setStatus(AppConstant.IS_LOGIN, false);
                startActivity(new Intent(getContext(), LoginSignupActivity.class));
                finishAffinity();
            } else {
                MyApp.popMessage("Local Friend", o.optString("message"), getContext());
            }

        }

    }

    @Override
    public void onJsonArrayResponseReceived(JSONArray a, int callNumber) {
        dismissDialog();
    }

    @Override
    public void onTimeOutRetry(int callNumber) {
        dismissDialog();
    }

    @Override
    public void onErrorReceived(String error) {
        dismissDialog();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        txt_date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
    }


    //    =========================================   Image setup  =================================
    private File mFileTemp;
    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CROP_ICON = 0x3;
    private String fileString = "";

    private void setImagePath(String name) {
        File sdIconStorageDir = new File(
                Environment.getExternalStorageDirectory()
                        + "/LocalFriend/Pictures/");
        sdIconStorageDir.mkdirs();
        mFileTemp = new File(Environment.getExternalStorageDirectory()
                + "/LocalFriend/Pictures/", name + ".jpg");

    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Continue only if the File was successfully created
            if (mFileTemp != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        mFileTemp);
//                Uri photoURI = Uri.parse(mFileTemp.getAbsolutePath().toString());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PICTURE);
            }
        }
    }

    private void openGallery() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
            /*
             * The case if image selected from device gallery
			 */
                case REQUEST_CODE_GALLERY:

                    try {
                        InputStream inputStream = getContentResolver()
                                .openInputStream(data.getData());
                        FileOutputStream fileOutputStream = new FileOutputStream(
                                mFileTemp);
                        copyStream(inputStream, fileOutputStream);
                        fileOutputStream.close();
                        inputStream.close();
                        cropImage(rotateImage(getContext(), mFileTemp));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

			/*
             * The case if image is captured from camera
			 */
                case REQUEST_CODE_TAKE_PICTURE:

                    cropImage(rotateImage(getContext(), mFileTemp));
                    break;

                case REQUEST_CROP_ICON:
                    if (data != null) {
                        Bitmap photo = data.getExtras().getParcelable("data");

                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        try {
                            mFileTemp.createNewFile();
                            FileOutputStream fo = new FileOutputStream(mFileTemp);
                            fo.write(bytes.toByteArray());
                            fo.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;

            }
        }
    }

    private void cropImage(File mFileTemp) {
        long length = mFileTemp.length() / 1024; // Size in KB

//        if (length > 600) {
//            mFileTemp = saveBitmapToFile(mFileTemp);
//
//        }
        fileString = mFileTemp.getAbsolutePath();
        File file = new File(fileString);
        Uri imageUri = Uri.fromFile(file);

        callChangeProfilePicture(fileString);
        Glide.with(this).load(imageUri).placeholder(R.drawable.default_pic).into(img_profile);
//        Picasso.with(getContext()).load(imageUri).transform(new BlurTransformation(getContext())).into(img_bg);
    }

    private void callChangeProfilePicture(String path) {
        RequestParams p = new RequestParams();
        String contentType = RequestParams.APPLICATION_OCTET_STREAM;
        try {
            p.put("profilepic", mFileTemp, contentType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        postCall(getContext(), AppConstant.BASE_URL + "Account/ChangeProfileImage", p, "Uploading...", 2);
    }


    public static File rotateImage(Context context, final File path) {
        Bitmap b = decodeFileFromPath(context, path.getAbsolutePath());
        try {
            ExifInterface ei = new ExifInterface(path.getAbsolutePath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            Log.d("Orientation", "Orientation value : " + orientation);

            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                    break;
                default:
                    b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
                    break;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        FileOutputStream out1 = null;
//        File file;
        try {
//            String state = Environment.getExternalStorageState();
//            if (Environment.MEDIA_MOUNTED.equals(state)) {
//                file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".jpg");
//                Log.d("PictureUtils","uses environmment media mounted : "+Environment.getExternalStorageDirectory());
//                Log.d("PicturesUtils","file path :"+file.getPath());
//                Log.d("PicturesUtils","file name :"+file.getName());
//            }
//            else {
//                file = new File(context.getFilesDir() , "image" + new Date().getTime() + ".jpg");
//                Log.d("PictureUtils","uses getFilesDire : "+context.getFilesDir());
//            }
            out1 = new FileOutputStream(path);
            b.compress(Bitmap.CompressFormat.JPEG, 90, out1);
//            imgRotatedPath = file.getAbsolutePath();
            //imgFromCameraOrGallery.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out1.close();
            } catch (Throwable ignore) {

            }
        }
        return path;
    }


    public static Bitmap decodeFileFromPath(Context context, String path) {
        Uri uri = getImageUri(path);
        InputStream in = null;
        try {
            in = context.getContentResolver().openInputStream(uri);

            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(in, null, o);
            in.close();

            int scale = 1;
            int inSampleSize = 1024;
            if (o.outHeight > inSampleSize || o.outWidth > inSampleSize) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(inSampleSize / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = context.getContentResolver().openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            return b;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Uri getImageUri(String path) {
        return Uri.fromFile(new File(path));
    }

    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }
}

