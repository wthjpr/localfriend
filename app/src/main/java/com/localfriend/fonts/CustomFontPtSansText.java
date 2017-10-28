package com.localfriend.fonts;

import android.content.Context;
import android.util.AttributeSet;

import com.localfriend.utils.CustomFontUtils;
import com.localfriend.utils.PtSansUtils;

/**
 * Created by SONI on 10/9/2017.
 */

public class CustomFontPtSansText extends android.support.v7.widget.AppCompatTextView {

    public CustomFontPtSansText(Context context) {
        super(context);

        PtSansUtils.applyCustomFont(this, context, null);
    }

    public CustomFontPtSansText(Context context, AttributeSet attrs) {
        super(context, attrs);

        PtSansUtils.applyCustomFont(this, context, attrs);
    }

    public CustomFontPtSansText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        PtSansUtils.applyCustomFont(this, context, attrs);
    }
}