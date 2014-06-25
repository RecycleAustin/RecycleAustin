package com.austindroids.recycleaustin.activities;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.austindroids.recycleaustin.adapters.DashboardAdapter;
import com.austindroids.recycleaustin.R;

public class MainActivity extends Activity implements OnItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.dashboard_grid);
        gridview.setAdapter(new DashboardAdapter(this));
        gridview.setOnItemClickListener(this);

        // Hack to disable GridView scrolling
        gridview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == MotionEvent.ACTION_MOVE;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Intent intent;

        switch (position) {
            case 0:
                intent = new Intent(this, ScheduleActivity.class);
                break;
            case 1:
                intent = new Intent(this, RecycleLocationsActivity.class);
                break;
            case 2:
                intent = new Intent(this, FindRecycleServiceActivity.class);
                break;
            case 3:
                intent = new Intent(this, RecycleFlowScreenActivity.class);
                break;
            default:
                intent = new Intent(this, ScheduleActivity.class);
                break;
        }

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        ActionBar actionBar = getActionBar();
        actionBar.setSubtitle("Recycler App");
        actionBar.setTitle("Recycler App");
        //actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        // Dim navigation buttons on bottom
        getWindow().
                getDecorView().
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_appteam:
                String LicenseInfo = "Jessica Tyler\nNitin Kurian John\nMatt Hernandez";
                AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(MainActivity.this);
                LicenseDialog.setTitle("App Team");
                LicenseDialog.setMessage(LicenseInfo);
                LicenseDialog.show();
                return true;
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(),
                        "Settings not Available.", Toast.LENGTH_SHORT)
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
