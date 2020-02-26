package com.vickykdv.cashitkdvdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.vickykdv.cashitkdvdialog.utils.DeviceUtil;
import com.vickykdv.cashitkdvdialog.animViews.cashitKDVPushButton;

import java.util.ArrayList;
import java.util.List;

public class Dlg extends AppCompatDialog {

    // region Enums

    public enum Position {
        TOP, CENTER, BOTTOM
    }

    public enum Style {
        STYLE1,
        STYLE2,
        STYLE3,
        STYLE4
    }
    // region Properties
    private DialogParams params;

    private RelativeLayout dlgBackground, dlgContainer;
    private LinearLayout dlgHeaderLinearLayout, dlgBodyContainer, btnContainerLinearLayout,btnContainerLinearLayoutHoriz,
            dlgFooterLinearlayout, iconTitleContainer;
    private CardView dlgCardView;
    private TextView dlgTitleTextView, dlgMessageTextView;
    private ImageView dlgIconImageView;
    private ScrollView dlgScrollView;

    // endregion

    // region Setup methods
    private Dlg(Context context) {
        super(context, R.style.cashitdialog);
    }

    private Dlg(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the view
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.cashitdialog_layout, null);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);

        // Setup the dialog
        setupSubviews(view);

        // Set the size to adjust when keyboard shown
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setEnabled(false);
    }

    private void setupSubviews(View view) {

        dlgBackground = ((RelativeLayout) view.findViewById(R.id.cashitdialog_background));
        setupBackground();

        dlgContainer = (RelativeLayout) view.findViewById(R.id.cashitdialog_container);

        createCardView();
    }

    private void setupBackground() {

        // Background
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dlgBackground.setBackgroundColor(params.backgroundColor);

        dlgBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (params.cancelable) {

                    dismiss();
                }
            }
        });

        adjustBackgroundGravity();
    }

    private void createCardView() {

        dlgCardView = (CardView) findViewById(R.id.cashitdialog_cardview);

        bindCardSubviews();

        dlgScrollView.setBackgroundColor(params.dialogBackgroundColor);

        adjustDialogLayoutParams();

        populateCardView();
        setupCardBehaviour();
    }

    private void bindCardSubviews() {
        dlgScrollView = (ScrollView) dlgCardView.findViewById(R.id.cashitdialog_scrollview);
        dlgBodyContainer = (LinearLayout) dlgCardView.findViewById(R.id.alert_body_container);
        dlgHeaderLinearLayout = (LinearLayout) dlgCardView.findViewById(R.id.alert_header_container);
        dlgHeaderLinearLayout.requestLayout();
        dlgHeaderLinearLayout.setVisibility(View.GONE);
        dlgTitleTextView = (TextView) dlgCardView.findViewById(R.id.tv_dialog_title);
        iconTitleContainer = (LinearLayout) dlgCardView.findViewById(R.id.icon_title_container);
        dlgIconImageView = (ImageView) dlgCardView.findViewById(R.id.cashitdialog_icon_imageview);
        dlgMessageTextView = (TextView) dlgCardView.findViewById(R.id.tv_dialog_content_desc);
        btnContainerLinearLayout = (LinearLayout) dlgCardView.findViewById(R.id.alert_buttons_container);
        btnContainerLinearLayoutHoriz = (LinearLayout) dlgCardView.findViewById(R.id.alert_button_container_horiz);
        dlgFooterLinearlayout = (LinearLayout) dlgCardView.findViewById(R.id.alert_footer_container);
    }

    private void populateCardView() {
        // Icon
        if (params.iconDrawableId != -1) {
            setIcon(params.iconDrawableId);
        } else if (params.iconDrawable != null) {
            setIcon(params.iconDrawable);
        } else { setIcon(null); }

        // Title
        setTitle(params.title);

        // Message
        setDescription(params.description);

        // Text color
        if (params.textColor != -1) {
            setTitleColor(params.textColor);
            setDescriptionColor(params.textColor);
        }

        // Cancel
        setCancelable(params.cancelable);

        // Buttons
        populateButtons(params.context, params.buttons);

        // Buttons
        populateButtonsHoriz(params.context, params.buttonsHoriz);

        // Text gravity
        setTextAligment(params.textGravity);


        if (params.isDialogBodyEmpty()) {
            dlgBodyContainer.setVisibility(View.GONE);
        }

        // Image/Header
        if (params.contentImageId != -1) {
            setContentImage(params.contentImageId);
        } else if (params.contentImage != null) {
            setContentImage(params.contentImage);
        } else if (params.headerView != null) {
            setHeaderView(params.headerView);
        } else if (params.headerViewId != -1) {
            setHeaderView(params.headerViewId);
        }

//        // Footer
//        if (params.footerView != null) {
//            setFooterView(params.footerView);
//        } else if (params.footerViewId != -1) {
//            setFooterView(params.footerViewId);
//        }
    }

    private void setupCardBehaviour() {
        // Style specific behaviour
        switch (params.dialogStyle) {

            case CENTER:

            case BOTTOM:
                break;
        }
    }
    // endregion

    @Override
    public void show() {
        super.show();

        startPresentAnimation();
    }

    @Override
    public void dismiss() {

        setEnabled(false);

        startDismissAnimation();

    }

    private void alertPresented() {

        setEnabled(true);

        // Auto dismiss if needed
        if (params.autoDismissDuration > 0) {
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            };
            handler.postDelayed(runnable, params.autoDismissDuration);
        }
    }

    // region - Setters

    private void setDialogParams(DialogParams params) {
        this.params = params;
    }

    public void setEnabled(boolean enabled) {
        setViewEnabled(dlgBackground, enabled);
    }

//    public void setBackgroundColor(int color, boolean animated){
//
//        if (animated) {
//            int colorFrom = ((ColorDrawable) dlgBackground.getBackground()).getColor();
//            int colorTo = color;
//            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
//            colorAnimation.setDuration(getContext().getResources().getInteger(R.integer.cashitdialog_animation_duration)); // milliseconds
//            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//
//                @Override
//                public void onAnimationUpdate(ValueAnimator animator) {
//                    dlgBackground.setBackgroundColor((int) animator.getAnimatedValue());
//                }
//
//            });
//            colorAnimation.start();
//        }
//        else {
//            dlgBackground.setBackgroundColor(color);
//        }
//    }
//
//    public void setDialogBackgroundColor(int color, boolean animated) {
//        if (animated) {
//            int colorFrom = ((ColorDrawable) dlgCardView.getBackground()).getColor();
//            int colorTo = color;
//            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
//            colorAnimation.setDuration(getContext().getResources().getInteger(R.integer.cashitdialog_animation_duration)); // milliseconds
//            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//
//                @Override
//                public void onAnimationUpdate(ValueAnimator animator) {
//                    dlgCardView.setBackgroundColor((int) animator.getAnimatedValue());
//                }
//
//            });
//            colorAnimation.start();
//        }
//        else {
//            dlgCardView.setBackgroundColor(color);
//        }
//    }

    public void setCornerRadius(float radius) {
        this.params.dialogCornerRadius = radius;

        dlgCardView.setRadius(getCornerRadius());
    }

    public void setOutMargin(int margin) {
        this.params.dialogOuterMargin = margin;

        adjustDialogLayoutParams();
    }

    @Override
    public void setTitle(CharSequence title) {
        if (TextUtils.isEmpty(title)) {
            dlgTitleTextView.setVisibility(View.GONE);
            if (dlgIconImageView.getVisibility() == View.GONE) {
                iconTitleContainer.setVisibility(View.GONE);
            }
        } else {
            dlgTitleTextView.setText(title);
            dlgTitleTextView.setVisibility(View.VISIBLE);
            iconTitleContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getContext().getString(titleId));
    }

    public void setTitleColor (@ColorInt int color) {
        dlgTitleTextView.setTextColor(color);
    }

    public void setDescription(CharSequence message) {
        if (TextUtils.isEmpty(message)) {
            dlgMessageTextView.setVisibility(View.GONE);
        } else {
            dlgMessageTextView.setText(message);
            dlgMessageTextView.setVisibility(View.VISIBLE);
        }
    }

    public void setDescription(int messageId) {
        setDescription(getContext().getString(messageId));
    }

    public void setDescriptionColor(@ColorInt int color) {
        dlgMessageTextView.setTextColor(color);
    }

    /**
     * @param dialogStyle
     */
    public void setDlgPosition(Position dialogStyle) {
        params.dialogStyle = dialogStyle;

        adjustBackgroundGravity();
        adjustDialogLayoutParams();
    }

    /**
     * @param textGravity @see android.view.Gravity
     */
    public void setTextAligment(int textGravity) {
        ((LinearLayout.LayoutParams) iconTitleContainer.getLayoutParams()).gravity = textGravity;
        dlgMessageTextView.setGravity(textGravity);
    }

    /**
     * @param headerView pass null to remove header
     */
    public void setHeaderView(View headerView) {
        dlgHeaderLinearLayout.removeAllViews();
        if (headerView != null) {
            dlgHeaderLinearLayout.setVisibility(View.VISIBLE);
            dlgHeaderLinearLayout.addView(headerView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            // Allows the header view to overlap the alert content if needed
            disableClipOnParents(headerView);
        } else {
            dlgHeaderLinearLayout.setVisibility(View.GONE);
        }
    }

    public void setHeaderView(@LayoutRes int headerResId) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(headerResId, null);
        setHeaderView(view);
    }

    public void setIcon(@DrawableRes int iconDrawableId) {
        setIcon(ContextCompat.getDrawable(getContext(), iconDrawableId));
    }

    public void setIcon(Drawable iconDrawable) {
        if (iconDrawable == null) {
            dlgIconImageView.setVisibility(View.GONE);
            if (dlgTitleTextView.getVisibility() == View.GONE) {
                iconTitleContainer.setVisibility(View.GONE);
            }
        } else {
            dlgIconImageView.setVisibility(View.VISIBLE);
            iconTitleContainer.setVisibility(View.VISIBLE);
            dlgIconImageView.setImageDrawable(iconDrawable);
        }
    }

    /**
     * @param imageDrawableId value -1 will remove image
     */
    public void setContentImage(@DrawableRes int imageDrawableId) {
        setContentImage(ContextCompat.getDrawable(getContext(), imageDrawableId));
    }

    public void setContentImage(Drawable imageDrawable) {
        if (imageDrawable != null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.cashitdialog_imageview_header, dlgHeaderLinearLayout).findViewById(R.id.cashitdialog_imageview_content);
            imageView.setImageDrawable(imageDrawable);
            imageView.setTag(111);
            dlgHeaderLinearLayout.setVisibility(View.VISIBLE);
        } else {
            for (int i = 0; i < dlgHeaderLinearLayout.getChildCount(); i++) {
                View view = dlgHeaderLinearLayout.getChildAt(i);
                if (view instanceof ImageView && (int) view.getTag() == 111) {
                    dlgHeaderLinearLayout.removeView(view);
                    dlgHeaderLinearLayout.setVisibility(View.GONE);
                    break;
                }
            }
        }
    }

    /**
//     * @param footerView pass null to remove footer
     */
//    public void setFooterView(View footerView) {
//        dlgFooterLinearlayout.removeAllViews();
//        if (footerView != null) {
//            dlgFooterLinearlayout.addView(footerView,
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);
//            dlgFooterLinearlayout.setVisibility(View.VISIBLE);
//
//            // Allows the footer view to overlap the alert content if needed
//            disableClipOnParents(footerView);
//        } else {
//            dlgFooterLinearlayout.setVisibility(View.GONE);
//        }
//    }

//    public void setFooterView(@LayoutRes int footerResId) {
//        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(footerResId, null);
//        setFooterView(view);
//    }

    private void setViewEnabled(ViewGroup layout, boolean enabled) {
        layout.setEnabled(enabled);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                setViewEnabled((ViewGroup) child, enabled);
            } else {
                child.setEnabled(enabled);
            }
        }
    }

    private void disableClipOnParents(View v) {
        if (v.getParent() == null) {
            return;
        }

        if (v instanceof ViewGroup) {
            ((ViewGroup) v).setClipChildren(false);
        }

        if (v.getParent() instanceof View) {
            disableClipOnParents((View) v.getParent());
        }
    }

    private void populateButtons(Context context, List<cashitDialogActionButton> buttons) {
        btnContainerLinearLayout.removeAllViews();
        if (buttons.size() > 0) {
            for (int i = 0; i < buttons.size(); i++) {
                View buttonView = createButton(context, buttons.get(i));
                btnContainerLinearLayout.addView(buttonView);
            }
            btnContainerLinearLayout.setVisibility(View.VISIBLE);
        } else {
            btnContainerLinearLayout.setVisibility(View.GONE);
        }
    }

    private void populateButtonsHoriz(Context context, List<cashitDialogActionButtonHoriz> buttons) {
        btnContainerLinearLayoutHoriz.removeAllViews();

        //Set Layout Weight

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);


        if (buttons.size() > 0) {
            for (int i = 0; i < buttons.size(); i++) {
                View buttonView = createButtonHoriz(context, buttons.get(i));
                btnContainerLinearLayoutHoriz.addView(buttonView);
                buttonView.setLayoutParams(params);
            }
            btnContainerLinearLayoutHoriz.setVisibility(View.VISIBLE);

            if(buttons.size() > 1){
                //margin
                int margin = ((int) btnContainerLinearLayoutHoriz.getResources().getDimension(R.dimen.cashitdialog_button_padding_button_horiz));
                params.weight = 1.0f;
                params.setMargins(margin, margin, margin, margin);
            }
        } else {
            btnContainerLinearLayoutHoriz.setVisibility(View.GONE);
        }
    }

    private View createButtonHoriz(Context context, final cashitDialogActionButtonHoriz actionButton) {
        cashitKDVPushButton button = new cashitKDVPushButton(context, null, R.style.cashitdialog_Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionButton.onClickListener.onClick(Dlg.this, 0);
            }
        });

        setButtonLayoutHoriz(button, actionButton);

        button.setText(actionButton.buttonText);

        setButtonColorsHoriz(button, actionButton);

        return button;
    }

    private View createButton(Context context, final cashitDialogActionButton actionButton) {
        cashitKDVPushButton button = new cashitKDVPushButton(context, null, R.style.cashitdialog_Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionButton.onClickListener.onClick(Dlg.this, 0);
            }
        });

        setButtonLayout(button, actionButton);

        button.setText(actionButton.buttonText);

        setButtonColors(button, actionButton);

        return button;
    }

    private void setButtonLayout(View buttonView, cashitDialogActionButton actionButton) {
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        buttonView.setLayoutParams(buttonParams);

        int padding = ((int) buttonView.getResources().getDimension(R.dimen.cashitdialog_button_padding));
        buttonView.setPadding(padding, padding, padding, padding);
    }

    private void setButtonLayoutHoriz(View buttonView, cashitDialogActionButtonHoriz actionButton) {
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        buttonView.setLayoutParams(buttonParams);

        int padding = ((int) buttonView.getResources().getDimension(R.dimen.cashitdialog_button_padding));
        buttonView.setPadding(padding, padding, padding, padding);

    }

    private void setButtonColors(cashitKDVPushButton button, cashitDialogActionButton actionButton) {

        //Button background color
        if (actionButton.backgroundColor != -1) {
            GradientDrawable buttonDrawable = new GradientDrawable();
            buttonDrawable.setColor(actionButton.backgroundColor);
            buttonDrawable.setCornerRadius(getContext().getResources().getDimension(R.dimen.cashitdialog_button_corner_radius));
            ViewCompat.setBackground(button, buttonDrawable);
        }
        else if (actionButton.backgroundDrawableId != -1) {
            ViewCompat.setBackground(button, ContextCompat.getDrawable(getContext(), actionButton.backgroundDrawableId));
        }

        // Button text colors
        button.setTextColor(actionButton.textColor);
    }

    private void setButtonColorsHoriz(cashitKDVPushButton button, cashitDialogActionButtonHoriz actionButton) {

        //Button background color
        if (actionButton.backgroundColor != -1) {
            GradientDrawable buttonDrawable = new GradientDrawable();
            buttonDrawable.setColor(actionButton.backgroundColor);
            buttonDrawable.setCornerRadius(getContext().getResources().getDimension(R.dimen.cashitdialog_button_corner_radius));
            ViewCompat.setBackground(button, buttonDrawable);
        }
        else if (actionButton.backgroundDrawableId != -1) {
            ViewCompat.setBackground(button, ContextCompat.getDrawable(getContext(), actionButton.backgroundDrawableId));
        }

        // Button text colors
        button.setTextColor(actionButton.textColor);
    }

    public void setElevation(float elevation) {
        dlgCardView.setCardElevation(elevation);
    }

    // endregion

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }

    private void startPresentAnimation() {
        Animation presentAnimation = getPresentAnimation(params.dialogStyle);
        presentAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                alertPresented();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        dlgCardView.startAnimation(presentAnimation);
    }

    private void startDismissAnimation() {
        // Perform the dismiss animation and after that dismiss the dialog
        Animation dismissAnimation = getDismissAnimation(params.dialogStyle);
        dismissAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Dlg.super.dismiss();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        dlgCardView.startAnimation(dismissAnimation);

    }

    private Animation getPresentAnimation(Position style) {
        switch (style) {
            case TOP:
                return AnimationUtils.loadAnimation(params.context, R.anim.dialog_present_top);
            case CENTER:
                return AnimationUtils.loadAnimation(params.context, R.anim.dialog_present_center);
            case BOTTOM:
                return AnimationUtils.loadAnimation(params.context, R.anim.dialog_present_bottom);
            default:
                return AnimationUtils.loadAnimation(params.context, R.anim.dialog_present_center);
        }
    }

    private Animation getDismissAnimation(Position style) {
        switch (style) {
            case TOP:
                return AnimationUtils.loadAnimation(params.context, R.anim.dialog_dismiss_top);
            case CENTER:
                return AnimationUtils.loadAnimation(params.context, R.anim.dialog_dismiss_center);
            case BOTTOM:
                return AnimationUtils.loadAnimation(params.context, R.anim.dialog_dismiss_bottom);
            default:
                return AnimationUtils.loadAnimation(params.context, R.anim.dialog_dismiss_center);
        }
    }


    private void adjustBackgroundGravity() {
        switch (params.dialogStyle) {
            case TOP:
                dlgBackground.setGravity(Gravity.TOP);
                break;
            case CENTER:
                dlgBackground.setGravity(Gravity.CENTER_VERTICAL);
                break;
            case BOTTOM:
                dlgBackground.setGravity(Gravity.BOTTOM);
                break;
        }
    }

    private void adjustDialogLayoutParams() {

        // Corner radius
        dlgCardView.setRadius(getCornerRadius());

        RelativeLayout.LayoutParams cardContainerLayoutParams = (RelativeLayout.LayoutParams) dlgContainer.getLayoutParams();
        int margin = getOuterMargin();

        int horizontalMargin = margin;
        int topMargin = margin;
        int bottomMargin = margin;
        int maxWidth = (int) getContext().getResources().getDimension(R.dimen.cashitdialog_maxwidth);
        int screenWidth = DeviceUtil.getScreenWidth(getContext());

        switch (params.dialogStyle) {
            case TOP:
                horizontalMargin = 0;
                topMargin = 0;
                maxWidth = screenWidth;
                break;
        }

        if (isCustomMargin()) {
            maxWidth = screenWidth;
        }

        int width = screenWidth - (2 * horizontalMargin);
        width = Math.min(width, maxWidth);
        cardContainerLayoutParams.width = width;

        cardContainerLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        cardContainerLayoutParams.setMargins(horizontalMargin, topMargin, horizontalMargin, bottomMargin);

        dlgContainer.setLayoutParams(cardContainerLayoutParams);
    }

    private float getCornerRadius(){
        float cornerRadius = getContext().getResources().getDimension(R.dimen.cashitdialog_card_corner_radius);

        switch (params.dialogStyle) {
            case TOP:
                cornerRadius = 0;
                break;
        }

        if (params.dialogCornerRadius != -1) {
            cornerRadius = params.dialogCornerRadius;
        }

        return cornerRadius;
    }

    private int getOuterMargin() {
        int margin = (int)getContext().getResources().getDimension(R.dimen.cashitdialog_outer_margin);

        switch (params.dialogStyle) {

            case CENTER:
                int center_margin = (int)getContext().getResources().getDimension(R.dimen.cashitdialog_center_margin);
                params.dialogOuterMargin = center_margin;
                if (params.dialogOuterMargin != center_margin) {
                    margin = params.dialogOuterMargin;
                }
            case BOTTOM:
                if (params.dialogOuterMargin != -1) {
                    margin = params.dialogOuterMargin;
                }
                break;
        }



        return margin;
    }

    private boolean isCustomMargin() {
        return params.dialogOuterMargin != -1;
    }

    // endregion

    public static class Builder {

        private DialogParams params;

        public Builder(Context context) {
            params = new DialogParams();
            this.params.context = context;
        }


//        public Builder setBackgroundResource(@ColorRes int backgroundResource) {
//            this.params.backgroundColor = ResourcesCompat.getColor(this.params.context.getResources(), backgroundResource, null);
//            return this;
//        }

//        public Builder setBackgroundColor(@ColorInt int backgroundColor) {
//            this.params.backgroundColor = backgroundColor;
//            return this;
//        }

//        public Builder setDialogBackgroundResource(@ColorRes int backgroundResource) {
//            this.params.dialogBackgroundColor = ResourcesCompat.getColor(this.params.context.getResources(), backgroundResource, null);
//            return this;
//        }
//
//        public Builder setDialogBackgroundColor(@ColorInt int backgroundColor) {
//            this.params.dialogBackgroundColor = backgroundColor;
//            return this;
//        }

        public Builder setCornerRadius(float cornerRadius) {
            this.params.dialogCornerRadius = cornerRadius;
            return this;
        }

        public Builder setOutMargin(int margin) {
            this.params.dialogOuterMargin = margin;
            return this;
        }

        public Builder setDescription(CharSequence message) {
            this.params.description = message;
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.params.title = title;
            return this;
        }

        public Builder setDescription(@StringRes int description) {
            this.params.description = params.context.getString(description);
            return this;
        }

        public Builder setTitle(@StringRes int titleId) {
            this.params.title = params.context.getString(titleId);
            return this;
        }

        public Builder setTextColor(@ColorInt int color) {
            this.params.textColor = color;
            return this;
        }

        public Builder setContentImage(@DrawableRes int contentImageId) {
            this.params.contentImageId = contentImageId;
            this.params.contentImage = null;
            return this;
        }

        public Builder setContentImage(Drawable contentImage) {
            this.params.contentImage = contentImage;
            this.params.contentImageId = -1;
            return this;
        }

        public Builder setIcon(@DrawableRes int iconDrawableId) {
            this.params.iconDrawableId = iconDrawableId;
            this.params.iconDrawable = null;
            return this;
        }

        public Builder setIcon(Drawable iconDrawable) {
            this.params.iconDrawable = iconDrawable;
            this.params.iconDrawableId = -1;
            return this;
        }

        public Builder onDismissListener(OnDismissListener onDismissListener) {
            this.params.onDismissListener = onDismissListener;
            return this;
        }

        public Builder setDlgPosition(Position style) {
            this.params.dialogStyle = style;
            return this;
        }

        /**
         * @param textAligment @see android.view.Gravity
         */
        public Builder setTextAligment(int textAligment) {
            this.params.textGravity = textAligment;
            return this;
        }

        public Builder addButtonVert(String buttonText, Style style, OnClickListener onClickListener) {
            cashitDialogActionButton button = new cashitDialogActionButton(this.params.context, buttonText, style, onClickListener);
            this.params.buttons.add(button);
            return this;
        }

        public Builder addButtonHoriz(String buttonText, Style style, OnClickListener onClickListener) {
            cashitDialogActionButtonHoriz button = new cashitDialogActionButtonHoriz(this.params.context, buttonText, style, onClickListener);
            this.params.buttonsHoriz.add(button);
            return this;
        }

        public Builder setHeaderView(View headerView) {
            this.params.headerView = headerView;
            this.params.headerViewId = -1;
            return this;
        }

        public Builder setHeaderView(@LayoutRes int headerViewId) {
            this.params.headerViewId = headerViewId;
            this.params.headerView = null;
            return this;
        }

//        public Builder setFooterView(View footerView) {
//            this.params.footerView = footerView;
//            this.params.footerViewId = -1;
//            return this;
//        }

//        public Builder setFooterView(@LayoutRes int footerViewId) {
//            this.params.footerViewId = footerViewId;
//            this.params.footerView = null;
//            return this;
//        }

        /**
         * default is true
         *
         * @param cancelable
         */
        public Builder setCancelable(boolean cancelable) {
            this.params.cancelable = cancelable;
            return this;
        }

//        public Builder setAutoDismissAfter(long duration) {
//            this.params.autoDismissDuration = duration;
//            return this;
//        }

        public Dlg create() {
            Dlg cashitDialog;
            if (params.theme == 0) {
                cashitDialog = new Dlg(params.context);
            } else {
                cashitDialog = new Dlg(params.context, params.theme);
            }

            cashitDialog.setOnDismissListener(params.onDismissListener);
            cashitDialog.setDialogParams(params);
            return cashitDialog;
        }

        public Dlg show() {
            final Dlg dialog = create();
            dialog.show();
            return dialog;
        }
    }

    private static class DialogParams {

        private Context context;
        private @ColorInt int backgroundColor = Color.parseColor("#B3000000");
        private @ColorInt int dialogBackgroundColor = Color.parseColor("#FFFFFF");
        private float dialogCornerRadius = -1;
        private int dialogOuterMargin = -1;
        private CharSequence description, title;
        private @ColorInt int textColor = -1;
        private int theme = R.style.cashitdialog,
                textGravity = Gravity.LEFT,
                iconDrawableId = -1,
                contentImageId = -1;
        private Position dialogStyle = Position.CENTER;
        private View headerView, footerView;
        private int headerViewId = -1, footerViewId = -1;
        private Drawable contentImage, iconDrawable;
        private List<cashitDialogActionButton> buttons = new ArrayList<>();
        private List<cashitDialogActionButtonHoriz> buttonsHoriz = new ArrayList<>();
        private OnDismissListener onDismissListener;
        private boolean cancelable = true;
        private long autoDismissDuration = -1;

        public boolean isDialogBodyEmpty() {
            if (!TextUtils.isEmpty(title)) return false;
            if (!TextUtils.isEmpty(description)) return false;
            if (buttons != null && buttons.size() > 0) return false;

            return true;
        }
    }

    private static class cashitDialogActionButton {
        private Context context;
        private String buttonText;
        private DialogInterface.OnClickListener onClickListener;
        private int textColor = -1;
        private Style style;
//        private align alignment = align.JUSTIFIED;
        private int backgroundColor = -1;
        private int backgroundDrawableId = -1;

        public cashitDialogActionButton(Context context, String buttonText, Style style, OnClickListener onClickListener) {
            this.context = context;
            this.buttonText = buttonText;
            this.style = style;
            this.backgroundDrawableId = getBackgroundDrawable(style);
//            this.alignment = alignment;
            this.onClickListener = onClickListener;
            this.textColor = getTextColor(style);
        }

        private @DrawableRes int getBackgroundDrawable(Style style) {
            switch (style) {
                case STYLE1:
                    backgroundDrawableId = R.drawable.cashitdialog_button_style_primary;
                    break;
                case STYLE2:
                    backgroundDrawableId = R.drawable.cashitdialog_button_style_secondary;
                    break;
                case STYLE3:
                    backgroundDrawableId = R.drawable.cashitdialog_button_style_tertiary;
                    break;
                case STYLE4:
                    backgroundDrawableId = R.drawable.cashitdialog_button_style_primary_outborder;
                    break;
            }
            return backgroundDrawableId;
        }

        private @ColorInt int getTextColor(Style style) {
            switch (style) {
                case STYLE1:
                    textColor = ContextCompat.getColor(context, R.color.cashitDlgColorWhite);
                    break;
                case STYLE2:
                    textColor = ContextCompat.getColor(context, R.color.cashitDlgColorWhite);
                    break;
                case STYLE3:
                    textColor = ContextCompat.getColor(context, R.color.cashitDlgColorWhite);
                    break;
                case STYLE4:
                    textColor = ContextCompat.getColor(context, R.color.cashitDlgColorSecondary);
                    break;
            }
            return textColor;
        }
    }

    private static class cashitDialogActionButtonHoriz {
        private Context context;
        private String buttonText;
        private DialogInterface.OnClickListener onClickListener;
        private int textColor = -1;
        private Style style;
        //        private align alignment = align.JUSTIFIED;
        private int backgroundColor = -1;
        private int backgroundDrawableId = -1;

        public cashitDialogActionButtonHoriz(Context context, String buttonText, Style style, OnClickListener onClickListener) {
            this.context = context;
            this.buttonText = buttonText;
            this.style = style;
            this.backgroundDrawableId = getBackgroundDrawable(style);
            //            this.alignment = alignment;
            this.onClickListener = onClickListener;
            this.textColor = getTextColor(style);
        }

        private @DrawableRes int getBackgroundDrawable(Style style) {
            switch (style) {
                case STYLE1:
                    backgroundDrawableId = R.drawable.cashitdialog_button_style_primary;
                    break;
                case STYLE2:
                    backgroundDrawableId = R.drawable.cashitdialog_button_style_secondary;
                    break;
                case STYLE3:
                    backgroundDrawableId = R.drawable.cashitdialog_button_style_tertiary;
                    break;
                case STYLE4:
                    backgroundDrawableId = R.drawable.cashitdialog_button_style_primary_outborder;
                    break;
            }
            return backgroundDrawableId;
        }

        private @ColorInt int getTextColor(Style style) {
            switch (style) {
                case STYLE1:
                    textColor = ContextCompat.getColor(context, R.color.cashitDlgColorWhite);
                    break;
                case STYLE2:
                    textColor = ContextCompat.getColor(context, R.color.cashitDlgColorWhite);
                    break;
                case STYLE3:
                    textColor = ContextCompat.getColor(context, R.color.cashitDlgColorWhite);
                    break;
                case STYLE4:
                    textColor = ContextCompat.getColor(context, R.color.cashitDlgColorSecondary);
                    break;
            }
            return textColor;
        }
    }
}
