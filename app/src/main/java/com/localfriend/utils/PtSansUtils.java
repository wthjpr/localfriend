package com.localfriend.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.localfriend.R;


/**
 * Created by SONI on 10/9/2017.
 */

public class PtSansUtils {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public static void applyCustomFont(TextView customFontTextView, Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CustomFontTextView);

//        String fontName = attributeArray.getString(R.styleable.CustomFontTextView_font);

        // check if a special textStyle was used (e.g. extra bold)
        int textStyle = attributeArray.getInt(R.styleable.CustomFontTextView_textStyle, 0);

        // if nothing extra was used, fall back to regular android:textStyle parameter
        if (textStyle == 0) {
            textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);
        }

        Typeface customFont = selectTypeface(context, textStyle);
        customFontTextView.setTypeface(customFont);

        attributeArray.recycle();
    }

    private static Typeface selectTypeface(Context context, int textStyle) {
//        if (fontName.contentEquals("some text")) {//R.string.font_name_fontawesome
//            return FontCache.getTypeface("fontawesome.ttf", context);
//        } else if (fontName.contentEquals(context.getString(R.string.font_name_source_sans_pro))) {
        /*
        information about the TextView textStyle:
        http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
        */
            switch (textStyle) {
                case 10: // light
                    return FontCache.getTypeface("PTSans-Regular.ttf", context);

                case 11: // bold
                    return FontCache.getTypeface("PTSans-Regular.ttf", context);

                case 12: // regular
                    return FontCache.getTypeface("PTSans-Regular.ttf", context);

                case 13: // extra light, equals @integer/font_style_extra_light
                    return FontCache.getTypeface("PTSans-Regular.ttf", context);


                case Typeface.NORMAL: // regular
                default:
                    return FontCache.getTypeface("PTSans-Regular.ttf", context);
            }
//        } else {
//            // no matching font found
//            // return null so Android just uses the standard font (Roboto)
//            return null;
//        }
    }
}