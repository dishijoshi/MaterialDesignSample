package adapter;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.azilen.android.materialdesignsample.NavigationDrawerActivity;
import com.azilen.android.materialdesignsample.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public Context context;
    private int lastPosition = -1;
    private ArrayList<String> mDataSet = new ArrayList<String>();
    private float preX = 0, nextX = 0;
    private OnCallNextAct mCallback;

    public MyAdapter(ArrayList<String> mDataset, NavigationDrawerActivity mainActivity) {
        this.mDataSet = mDataset;
        context = mainActivity.getApplicationContext();
        mCallback = (OnCallNextAct) mainActivity;
    }

    public MyAdapter(NavigationDrawerActivity mainActivity) {
        this.context = mainActivity.getApplicationContext();

    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);
        final MyAdapter.ViewHolder vh = new ViewHolder(view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    toggleInformationView(vh);
                }

            });
        }

        return vh;
    }


    private void toggleInformationView(ViewHolder viewHolder) {

        Animator animator = ViewAnimationUtils.createCircularReveal(
                viewHolder.mImageView,
                viewHolder.mImageView.getWidth() / 2,
                viewHolder.mImageView.getHeight() / 2,
                0,
                (float) Math.hypot(viewHolder.mImageView.getWidth(), viewHolder.mImageView.getHeight()));

        animator.setDuration(3000);
        // Set a natural ease-in/ease-out interpolator.
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        // Finally start the animation
        animator.start();
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        viewHolder.mTextView.setText(mDataSet.get(position));

        viewHolder.mCardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Event : " + event);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        preX = event.getX();
                        break;

                    case MotionEvent.ACTION_UP:
                        nextX = event.getX();
                        if (Math.abs(preX - nextX) <= 10)
                            mCallback.callNextAct(viewHolder.mTextView.getText().toString());
                        break;

                    case MotionEvent.ACTION_MOVE:
                        nextX = event.getX();
                        if (nextX - preX >= 100) {
                            removeRow(viewHolder.mCardView, position);
                        }
                        break;

                }

                return false;
            }
        });

    }

    private void removeRow(View view, final int position) {

        Animation anim = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right
        );
        anim.setDuration(500);
        view.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDataSet.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public CardView mCardView;
        public Button buttonDelete;
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv_info);
            mCardView = (CardView) v.findViewById(R.id.cv_cardview);
            mImageView = (ImageView) v.findViewById(R.id.iv_image);

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;


        }
    }

    public interface OnCallNextAct {
        void callNextAct(String text);
    }
}



