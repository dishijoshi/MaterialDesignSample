package com.azilen.android.materialdesignsample;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;

import CustomView.ScrollViewX;


public class FadingActionBarActivity extends android.support.v7.app.ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actionbar_act);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Navigation Drawer Transition Effect");
            setSupportActionBar(toolbar);
        }

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.color.black);
        final BitmapDrawable bd = new BitmapDrawable(bitmap);
        final ColorDrawable cd = new ColorDrawable(Color.rgb(68, 74, 83));//33b5e5  68,74,83

        toolbar.setBackgroundDrawable(cd);

        cd.setAlpha(0);
        toolbar.setTitle(Html.fromHtml("<b><font color='#ffffff'>Fading Action Bar</font></b>"));
        toolbar.setSubtitle(Html.fromHtml("<font color='#ffffff'>Subtitle</font>"));

        // custom scrollview for fading action bar.
        ScrollViewX scrollView = (ScrollViewX) findViewById(R.id.scroll_view);
        scrollView.setOnScrollViewListener(new ScrollViewX.OnScrollViewListener() {

            @Override
            public void onScrollChanged(ScrollViewX v, int l, int t, int oldl, int oldt) {

                cd.setAlpha(getAlphaforActionBar(v.getScrollY()));
            }

            private int getAlphaforActionBar(int scrollY) {
                int minDist = 0,maxDist = 650;
                if(scrollY>maxDist){
                    return 255;
                }
                else if(scrollY<minDist){
                    return 0;
                }
                else {
                    int alpha = 0;
                    alpha = (int)  ((255.0/maxDist)*scrollY);
                    return alpha;
                }
            }
        });

    }
}
