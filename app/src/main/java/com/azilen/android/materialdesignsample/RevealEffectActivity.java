/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.azilen.android.materialdesignsample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;


/**
 * This sample shows a view that is revealed when a button is clicked.
 */
public class RevealEffectActivity extends Activity {

    private final static String TAG = "RevealEffectBasicFragment";
    private boolean flag = true;
    Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_activity);


        View button = (View)findViewById(R.id.button);
        final ImageView shape = (ImageView)findViewById(R.id.circle);
        shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, FloatingButtonActivity.class));
            }
        });

        // Set a listener to reveal the view when clicked.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Create a reveal {@link Animator} that starts clipping the view from
                // the top left corner until the whole view is covered.

                Animator animator = ViewAnimationUtils.createCircularReveal(
                        shape,
                        shape.getWidth()/2,
                        shape.getHeight()/2,
                        0,
                        (float) Math.hypot(shape.getWidth(), shape.getHeight()));

                animator.setDuration(3000);
                // Set a natural ease-in/ease-out interpolator.
                animator.setInterpolator(new AccelerateDecelerateInterpolator());

                // Finally start the animation
                animator.start();

               /* if(flag) {hideImageCircular(shape); flag=false;}
                else {revealImageCircular(shape); flag=true;}*/
            }
        });


//        // Opening Activity to show the trailer
//
//        Button button2 = (Button)findViewById(R.id.button2);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(RevealEffectActivity.this,ShowErrorActivity.class);
//                startActivity(i);
//            }
//        });

    }

    //hide reveal effect
    private void hideImageCircular(final ImageView mImageView) {
        int x = (int)mImageView.getX();
        int y = (int)mImageView.getY();
        int radius = 10;
        Animator anim =
                ViewAnimationUtils.createCircularReveal(mImageView, x, y, radius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mImageView.setVisibility( View.INVISIBLE );
            }
        });
        anim.start();
    }

    //show reveal effect
    private void revealImageCircular(final ImageView mImageView) {
        int x = (int)mImageView.getX();
        int y = (int)mImageView.getY();
        int radius = 10;
        Animator anim =
                ViewAnimationUtils.createCircularReveal(mImageView, x, y, 0, radius);
        anim.setDuration( 1000 );
        anim.addListener( new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mImageView.setVisibility( View.VISIBLE );
            }
        });
        anim.start();
    }
}