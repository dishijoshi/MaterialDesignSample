package com.azilen.android.materialdesignsample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ShowErrorActivity extends ActionBarActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button showError, showSuccess, showInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.show_error);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Error Showing");
            // replace toolbar to action bar
            setSupportActionBar(toolbar);
        }

        // showing error above screen
        showError = (Button) findViewById(R.id.showError);
        showError.setOnClickListener(this);
        // showing success above screen
        showSuccess = (Button) findViewById(R.id.showSuccess);
        showSuccess.setOnClickListener(this);
        // showing info above screen
        showInfo = (Button) findViewById(R.id.showInfo);
        showInfo.setOnClickListener(this);

    }

    // click event
    @Override
    public void onClick(View v) {

        System.out.println("Click Event");
        switch (v.getId()) {
            case R.id.showError:
               new ShowErrorMessage(ShowErrorActivity.this, 1, "Error Message");
                break;
            case R.id.showSuccess:
                new ShowErrorMessage(ShowErrorActivity.this, 2, "Success Message");
                break;
            case R.id.showInfo:
                new ShowErrorMessage(ShowErrorActivity.this, 3, "Info Message");
                break;
        }

    }
}
