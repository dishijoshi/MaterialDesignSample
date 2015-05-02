package com.azilen.android.materialdesignsample;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.os.Handler;


public class ShowErrorMessage {

    private Context context;
    private TextView txtMsg;
    private Dialog mDialog;

    public ShowErrorMessage(Context context, int msgType, String message) {

        this.context = context;

        mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setContentView(R.layout.error);
        mDialog.setCancelable(false);
        txtMsg = (TextView) mDialog.findViewById(R.id.txt_msg);

        txtMsg.setText(message);
        setMessageTypeLayout(msgType);
        mDialog.show();


        // dismiss dialog after 2 sec
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
            }
        },2000);

        //Grab the window of the dialog, and change the width
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = mDialog.getWindow();
        lp.copyFrom(window.getAttributes());

        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        // Set Gravity
        lp.gravity = Gravity.TOP;
        lp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;

        // Setting layout params to windows
        window.setAttributes(lp);

        // Slider animation calling for showing the messages
        AlphaAnimationFunc(true);

    }

    /**
     * Animation on view for showing the errors
     * This function is used for slide in and slide out based on flag set
     *
     * @param flag
     */
    private void AlphaAnimationFunc(final boolean flag) {

        Animation anim;
        if (flag) anim = AnimationUtils.loadAnimation(context, R.anim.error_down);
        else anim = AnimationUtils.loadAnimation(context, R.anim.error_up);
        txtMsg.startAnimation(anim);
        anim.setDuration(1000);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                if (flag) txtMsg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (flag) AlphaAnimationFunc(false);
                else {
                    mDialog.dismiss();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    /* Setting background Color for related Messages */
    private void setMessageTypeLayout(int msgType) {

        switch (msgType) {
            case 1:
                txtMsg.setBackgroundColor(Color.parseColor("#EF5350"));
                break;
            case 2:
                txtMsg.setBackgroundColor(Color.parseColor("#26A69A"));
                break;
            case 3:
                txtMsg.setBackgroundColor(Color.parseColor("#4FC3F7"));
                break;
        }
    }

}
