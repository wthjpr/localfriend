package com.localfriend.fonts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.localfriend.utils.PtSansUtils;

/**
 * Created by SONI on 10/9/2017.
 */

public class CustomFontPtSansRadioButton extends android.support.v7.widget.AppCompatRadioButton {

    public CustomFontPtSansRadioButton(Context context) {
        super(context);

        PtSansUtils.applyCustomFont(this, context, null);
    }

    public CustomFontPtSansRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        PtSansUtils.applyCustomFont(this, context, attrs);
    }

    public CustomFontPtSansRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        PtSansUtils.applyCustomFont(this, context, attrs);
    }
}