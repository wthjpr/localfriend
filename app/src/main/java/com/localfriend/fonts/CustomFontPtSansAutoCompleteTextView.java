package com.localfriend.fonts;

import android.content.Context;
import android.util.AttributeSet;

import com.localfriend.utils.PtSansUtils;

/**
 * Created by SONI on 10/9/2017.
 */

public class CustomFontPtSansAutoCompleteTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {

    public CustomFontPtSansAutoCompleteTextView(Context context) {
        super(context);

        PtSansUtils.applyCustomFont(this, context, null);
    }

    public CustomFontPtSansAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        PtSansUtils.applyCustomFont(this, context, attrs);
    }

    public CustomFontPtSansAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        PtSansUtils.applyCustomFont(this, context, attrs);
    }
}