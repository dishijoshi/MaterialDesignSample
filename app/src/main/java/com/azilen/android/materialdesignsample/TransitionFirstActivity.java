package com.azilen.android.materialdesignsample;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;

public class TransitionFirstActivity extends Activity {

    private View mFabButton;
    private View mHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_first);

        mFabButton = findViewById(R.id.fab_button);
        mHeader = findViewById(R.id.activity_transition_header);

        // Set up the transition of the views
        Slide slideExitTransition = new Slide(Gravity.BOTTOM);
        slideExitTransition.excludeTarget(android.R.id.navigationBarBackground, true);
        slideExitTransition.excludeTarget(android.R.id.statusBarBackground, true);
        slideExitTransition.excludeTarget(R.id.activity_transition_header, true);
        getWindow().setExitTransition(slideExitTransition);
    }

    public void onbtnclk(View v){
        int id = v.getId();
        switch (id){
            case R.id.button:
                break;
        }
    }

    public void onFabPressed(View view) {

        //Transition animation between activities
        Intent i  = new Intent (TransitionFirstActivity.this,  TransitionSecondActivity.class);

        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
            TransitionFirstActivity.this,Pair.create(mFabButton, "fab"), Pair.create(mHeader, "holder1"));

        startActivity(i, transitionActivityOptions.toBundle());
    }
}
