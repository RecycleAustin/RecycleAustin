package com.austindroids.recycleaustin.activities;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.austindroids.recycleaustin.R;
import com.austindroids.recycleaustin.service.GeoCodingService;

public class StubActivity extends Activity {

    Button button;

Location currentLocation=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createLayout();
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        GeoCodingService service = new GeoCodingService((getApplicationContext()));
        this.currentLocation = service.getCurrentLocation();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                GeoCodingService service = new GeoCodingService(getApplicationContext());
                Toast.makeText(getApplicationContext(),
                        "Location:" + service.getCurrentLocation(), Toast.LENGTH_SHORT)
                        .show();


            }
        });
    }

    void createLayout() {
        LinearLayout lLayout = new LinearLayout(this);
        lLayout.setOrientation(LinearLayout.VERTICAL);
        //-1(LayoutParams.MATCH_PARENT) is fill_parent or match_parent since API level 8
        //-2(LayoutParams.WRAP_CONTENT) is wrap_content
        lLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        this.button = new Button(this);
        TextView tView = new TextView(this);
        tView.setText("Hello, This is a view created programmatically! " +
                "You CANNOT change me that easily :-)");
        tView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        lLayout.addView(tView);
        lLayout.addView(this.button);
        setContentView(lLayout);

    }


}
