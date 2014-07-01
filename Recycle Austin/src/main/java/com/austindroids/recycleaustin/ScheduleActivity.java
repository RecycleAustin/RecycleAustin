package com.austindroids.recycleaustin;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ScheduleActivity extends ActionBarActivity {


    // constants for Intent string extra names
    public static final String SCHEDULE_STREET = "schedule street";
    public static final String SCHEDULE_CITY_STATE = "schedule city state";
    public static final String SCHEDULE_WEEK = "schedule week";
    public static final String SCHEDULE_DAY = "scedule day";

    private TextView instructionsTV;
    private TextView scheduleStreetTV;
    private TextView scheduleCityStateTV;
    private TextView collectionWeekTV;
    private TextView collectionDayTV;

    private String scheduleStreet;
    private String scheduleCityState;
    private String scheduleWeek;
    private String scheduleDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        // get references to TextViews
        instructionsTV = (TextView) findViewById(R.id.textViewScheduleInstuctions);
        scheduleStreetTV = (TextView) findViewById(R.id.textViewScheduleStreet);
        scheduleCityStateTV = (TextView) findViewById(R.id.textViewScheduleCityState);
        collectionWeekTV = (TextView) findViewById(R.id.textViewCollectionWeek);
        collectionDayTV = (TextView) findViewById(R.id.textViewCollectionDay);

        // Add color to "Not My Address" text in the instructions.
        String instructionsString = getString(R.string.schedule_instructions);
        int startIndex = instructionsString.indexOf("\"");
        int endIndex = instructionsString.indexOf("\"", startIndex + 1);
        Spannable instructionsText = new SpannableString(instructionsString);
        instructionsText.setSpan(new ForegroundColorSpan(Color.RED), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        instructionsTV.setText(instructionsText);

        // Get Address and Schedule from Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            scheduleStreet = extras.getString(SCHEDULE_STREET);
            scheduleCityState = extras.getString(SCHEDULE_CITY_STATE);
            scheduleWeek = extras.getString(SCHEDULE_WEEK);
            scheduleDay = extras.getString(SCHEDULE_DAY);
        }

        // Set textView text from Intent string extras
        scheduleStreet = setDefaultIfNull(scheduleStreet);
        scheduleCityState = setDefaultIfNull(scheduleCityState);
        scheduleWeek = setDefaultIfNull(scheduleWeek);
        scheduleDay = setDefaultIfNull(scheduleDay);

        scheduleStreetTV.setText(scheduleStreet);
        scheduleCityStateTV.setText(scheduleCityState);
        collectionWeekTV.setText(scheduleWeek);
        collectionDayTV.setText(scheduleDay);

    }

    private String setDefaultIfNull(String str){
        if (str == null){
            str = "[value not passed to Intent]";
        }
        return str;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
