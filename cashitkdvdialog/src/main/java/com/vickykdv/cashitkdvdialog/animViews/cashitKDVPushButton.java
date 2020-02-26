package com.vickykdv.cashitkdvdialog.animViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatButton;

import com.vickykdv.cashitkdvdialog.utils.ButtonEffect;

public class cashitKDVPushButton extends AppCompatButton {

    public cashitKDVPushButton(Context context) {
        this(context, null, 0);
    }

    public cashitKDVPushButton(Context context, AttributeSet attrs) {
        this(context,attrs, 0);
    }

    public cashitKDVPushButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initButton(context, attrs, defStyleAttr);
    }

    void initButton(Context context, AttributeSet attributeSet, int defStyleAttr) {

        // Set centered text alignment
        setGravity(Gravity.CENTER);
        ButtonEffect.addBounceEffect(this);

        setTypeface(getTypeface(), Typeface.BOLD);
    }

}
