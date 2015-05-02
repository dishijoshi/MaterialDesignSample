package com.azilen.android.materialdesignsample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

public class ColorActivity extends Activity {

    private SharedPreferences sharedpreferences;
    private View revealView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Set the saved theme
        sharedpreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        setTheme(sharedpreferences.getInt("theme", R.style.AppTheme));
        setContentView(R.layout.activity_color);

        // Views
        // SwitchView
        Switch themeSwitch = (Switch) findViewById(R.id.theme_switch);
        themeSwitch.setChecked(sharedpreferences.getBoolean(themeSwitch.getId()+"", false));
        themeSwitch.setOnCheckedChangeListener(checkedListener);

        //CheckBox
        CheckBox themeCheck = (CheckBox) findViewById(R.id.theme_check);
        themeCheck.setChecked(sharedpreferences.getBoolean(themeCheck.getId()+"", false));
        themeCheck.setOnCheckedChangeListener(checkedListener);

        // View having reveal effect
        revealView = findViewById(R.id.reveal_view);

        // Show the unReveal effect
        final int cx = sharedpreferences.getInt("x", 0);
        final int cy = sharedpreferences.getInt("y", 0);

        // hide unreveal effect method
        startHideRevealEffect(cx, cy);
    }

    private void startHideRevealEffect(final int cx, final int cy) {

        if (cx != 0 && cy != 0) {
            // Show the unReveal effect when the view is attached to the window
            revealView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {

                    // Get the accent color
                    TypedValue outValue = new TypedValue();
                    getTheme().resolveAttribute(android.R.attr.colorPrimary, outValue, true);
                    revealView.setBackgroundColor(outValue.data);

                    hideRevealEffect(revealView, cx, cy, 1920);
                }

                @Override
                public void onViewDetachedFromWindow(View v) {}
            });
        }
    }

    // hide navigation status
    private void hideNavigationStatus() {

        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

// change color of the view with reveal animation
    Animator.AnimatorListener revealAnimationListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {}

        @Override
        public void onAnimationEnd(Animator animation) {

            Intent i = new Intent(ColorActivity.this, ColorActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
            overridePendingTransition(0, 0);
            finish();
        }

        @Override
        public void onAnimationCancel(Animator animation) {}

        @Override
        public void onAnimationRepeat(Animator animation) {}
    };


    // Changing color of the view by click on circle
    public void view(View view) {

        int selectedTheme = 0;
        int primaryColor = 0;

        switch (view.getId()) {
            case R.id.circle1:
                selectedTheme = R.style.theme1;
                primaryColor = getResources().getColor(R.color.color_set_1_primary);
                break;

            case R.id.circle2:
                selectedTheme = R.style.theme2;
                primaryColor = getResources().getColor(R.color.color_set_2_primary);
                break;

            case R.id.circle3:
                selectedTheme = R.style.theme3;
                primaryColor = getResources().getColor(R.color.color_set_3_primary);
                break;

            case R.id.circle4:
                selectedTheme = R.style.theme4;
                primaryColor = getResources().getColor(R.color.color_set_4_primary);
                break;
        }

        int [] location = new int[2];
        revealView.setBackgroundColor(primaryColor);
        view.getLocationOnScreen(location);

        int cx = (location[0] + (view.getWidth() / 2));
        int cy = location[1] + (getStatusBarHeight(this) / 2);

        // store selected theme and position
        SharedPreferences.Editor ed = sharedpreferences.edit();
        ed.putInt("x", cx);
        ed.putInt("y", cy);
        ed.putInt("theme", selectedTheme);
        ed.apply();

        hideNavigationStatus();
        showRevealEffect(revealView, cx, cy, revealAnimationListener);
    }

    //get mobile status bar height
    public static int getStatusBarHeight(Context c) {

        int result = 0;
        int resourceId = c.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = c.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    // set up to hide reveal effect
    public static void hideRevealEffect (final View v, int centerX, int centerY, int initialRadius) {

        v.setVisibility(View.VISIBLE);

        // create the animation (the final radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(
                v, centerX, centerY, initialRadius, 0);

        anim.setDuration(350);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                v.setVisibility(View.INVISIBLE);
            }
        });

        anim.start();
    }

    // set up to show reveal effect
    public static void showRevealEffect(final View v, int centerX, int centerY, Animator.AnimatorListener lis) {

        v.setVisibility(View.VISIBLE);

        int height = v.getHeight();

        Animator anim = ViewAnimationUtils.createCircularReveal(
                v, centerX, centerY, 0, height);

        anim.setDuration(350);

        anim.addListener(lis);
        anim.start();
    }

    // save checked status of the Checkbox
    CompoundButton.OnCheckedChangeListener checkedListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SharedPreferences.Editor ed = sharedpreferences.edit();
            ed.putInt("theme", (isChecked) ? R.style.Base_Theme_AppCompat : R.style.Base_Theme_AppCompat_Light);
            ed.putBoolean(buttonView.getId()+"", isChecked);
            ed.apply();
        }
    };
}
