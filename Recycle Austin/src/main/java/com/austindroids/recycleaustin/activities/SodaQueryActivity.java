package com.austindroids.recycleaustin.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.austindroids.recycleaustin.R;
import com.austindroids.recycleaustin.service.SodaQueryService;

import java.util.ArrayList;
import java.util.List;


public class SodaQueryActivity extends Activity{
    private Button sendButton;
    private EditText addressField;
    private TextView addressTest;

    public static final String RESULTS = "Results";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sodaquery);



        addressTest = (TextView) findViewById(R.id.test_address);
        sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressField = (EditText) findViewById(R.id.address_field);
                String address = addressField.getText().toString();

                ///////////// Check if address is blank //////////////////////
                if (address.equals("")) {
                    Toast.makeText(v.getContext(), "Please enter a valid address", Toast.LENGTH_SHORT).show();
                } else {
                    ///////////// Show Alert before AsyncTask starts ////////////
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Loading");
                    AlertDialog alert = builder.create();
                    alert.setCancelable(false);
                    alert.show();

                    ArrayList<String> results = new SodaQueryService(address).send();
                    alert.dismiss();
                    if (results.isEmpty() || results.contains("Timeout")) {
                        Toast.makeText(SodaQueryActivity.this, "Problem contacting database", Toast.LENGTH_LONG).show();
                    } else if (results.contains("Address Problem") || results.contains("JSON Address Problem")) {
                        Toast.makeText(SodaQueryActivity.this, "Problem getting address", Toast.LENGTH_LONG).show();
                    } else if (results.contains("Schedule JSON Exception")) {
                        Toast.makeText(SodaQueryActivity.this, "No schedule for address", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(SodaQueryActivity.this, ScheduleActivity.class);
                        intent.putExtra(ScheduleActivity.SCHEDULE_STREET, results.get(0));
                        intent.putExtra(ScheduleActivity.SCHEDULE_CITY_STATE, results.get(1));
                        intent.putExtra(ScheduleActivity.SCHEDULE_DAY, results.get(2));
                        intent.putExtra(ScheduleActivity.SCHEDULE_WEEK, results.get(3));
                        startActivity(intent);
                    }
                }
            }
        });
    }

}
