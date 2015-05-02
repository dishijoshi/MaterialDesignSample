package com.azilen.android.materialdesignsample;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by dishi.joshi on 4/18/2015.
 */
public class FloatingButtonActivity extends Activity {


    private ImageButton fabbtn;
    private float mPrevX = 0, mPrevY = 0, mNextX = 0, mNextY = 0;
    private int setX = 0, setY = 0, height, width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);

        fabbtn = (ImageButton)findViewById(R.id.fab);

        // dragging floating button
        fabbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                float currX, currY;
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN: {

                        mPrevX = event.getX();
                        mPrevY = event.getY();
                        break;
                    }

                    case MotionEvent.ACTION_MOVE: {

                        currX = event.getRawX();
                        currY = event.getRawY();

                        setX = (int) (currX - mPrevX);
                        setY = (int) (currY - mPrevY);

                        if (setX >= width) setX = width;
                        else if (setX <= 0) setX = 0;
                        if (setY >= height) setY = height;
                        else if (setY <= 0) setY = 0;

                        Log.d("TOUCH", "" + currX + " : " + currY + " --- " + setX + " : " + setY + " Height - " + height + " Width - " + width);

                        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
                        marginParams.setMargins(setX, setY - view.getHeight(), 0, 0);
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(marginParams);
                        view.setLayoutParams(layoutParams);


                        break;
                    }


                    case MotionEvent.ACTION_CANCEL:
                        break;

                    case MotionEvent.ACTION_UP:

                        mNextX = event.getX();
                        mNextY = event.getY();
                        break;
                }

                return true;
            }
        });



    }

}
