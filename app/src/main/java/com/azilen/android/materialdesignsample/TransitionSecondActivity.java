package com.azilen.android.materialdesignsample;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;

import adapter.AnimatorAdapter;
import adapter.TransitionAdapter;


public class TransitionSecondActivity extends Activity {

    private static final int SCALE_DELAY = 30;
    private LinearLayout rowContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_second);

        rowContainer = (LinearLayout) findViewById(R.id.row_container2);

        //sliding transition animation when exit
        Slide slideExitTransition = new Slide(Gravity.BOTTOM);
        slideExitTransition.excludeTarget(android.R.id.navigationBarBackground, true);
        slideExitTransition.excludeTarget(android.R.id.statusBarBackground, true);


        // windows enter transition animation
        getWindow().getEnterTransition().addListener(new TransitionAdapter() {

            @Override
            public void onTransitionEnd(Transition transition) {

                super.onTransitionEnd(transition);

                getWindow().getEnterTransition().removeListener(this);

                for (int i = 0; i < rowContainer.getChildCount(); i++) {

                    View rowView = rowContainer.getChildAt(i);
                    rowView.animate().setStartDelay(i * SCALE_DELAY)
                        .scaleX(1).scaleY(1);
                }
            }
        });
    }

    //when back pressed
    @Override
    public void onBackPressed() {

        for (int i = 0; i < rowContainer.getChildCount(); i++) {

            View rowView = rowContainer.getChildAt(i);

            ViewPropertyAnimator propertyAnimator = rowView.animate()
                .setStartDelay(i * SCALE_DELAY)
                .scaleX(0).scaleY(0)
                .setListener(new AnimatorAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        super.onAnimationEnd(animation);
                        finishAfterTransition();
                    }
                });
        }
    }
}
