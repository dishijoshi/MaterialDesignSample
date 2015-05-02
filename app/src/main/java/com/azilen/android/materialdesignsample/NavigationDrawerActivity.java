package com.azilen.android.materialdesignsample;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

import adapter.MyAdapter;
import adapter.NavigationDrawer_Adapter;

public class NavigationDrawerActivity extends android.support.v7.app.ActionBarActivity implements MyAdapter.OnCallNextAct, View.OnTouchListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView leftDrawerList;
    private NavigationDrawer_Adapter navigationDrawerAdapter;
    //  CustomFAB fab;
    private ImageView fab;
    private float mPrevX = 0, mPrevY = 0, mNextX = 0, mNextY = 0;
    private int setX = 0, setY = 0, height, width;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout);

        // toolbar to replace action bar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Navigation Drawer");
            setSupportActionBar(toolbar);
        }


        // setup navigation drawer
        initDrawer();
        //set up custom floating button
        initViews();
        screenSize();

    }

    // Initialize the different views
    private void initViews() {

        /***************************************************************************************************************/
        // Initializing the FAB imageview
        fab = new ImageView(this);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.abc_ic_go_search_api_mtrl_alpha));
    }


    // Fetching screen size to set floating point's boundary
    private void screenSize() {

        WindowManager wm = (WindowManager) NavigationDrawerActivity.this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        width = metrics.widthPixels;
        height = metrics.heightPixels - 150;
    }

    @Override
    public void callNextAct(String text) {


    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        float currX, currY;
        int action = event.getAction();
        switch (action) {

            // when touch the view
            case MotionEvent.ACTION_DOWN: {

                mPrevX = event.getX();
                mPrevY = event.getY();
                break;
            }

            // when view move
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

            // when remove touch
            case MotionEvent.ACTION_UP:

                mNextX = event.getX();
                mNextY = event.getY();

                break;
        }

        return true;
    }


    private class onClickListner implements View.OnClickListener {

        @Override
        public void onClick(View v) {
                Toast.makeText(NavigationDrawerActivity.this, " Clicked !", Toast.LENGTH_LONG).show();
        }
    }


    // Drawer initialization
    private void initDrawer() {

        leftDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //  navigationDrawerAdapter=new ArrayAdapter<String>( MainActivity.this, android.R.layout.simple_list_item_1, leftSliderData);
        navigationDrawerAdapter = new NavigationDrawer_Adapter();
        leftDrawerList.setAdapter(navigationDrawerAdapter);
        leftDrawerList.setOnItemClickListener(new onItemClickListener());

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Closed drawable layout when menu item selected
    private class onItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            drawerLayout.closeDrawer(leftDrawerList);
        }
    }

}




