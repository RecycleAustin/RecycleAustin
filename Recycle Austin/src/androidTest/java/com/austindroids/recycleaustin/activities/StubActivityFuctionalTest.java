package com.austindroids.recycleaustin.activities;

import android.app.KeyguardManager;
import android.content.Context;
import android.location.Location;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Spinner;
import android.widget.TextView;

import com.austindroids.recycleaustin.R;
import com.austindroids.recycleaustin.service.GeoCodingService;

/**
 * Created by Dutch on 6/27/2014.
 */
public class StubActivityFuctionalTest
        extends
        ActivityInstrumentationTestCase2<StubActivity> {

    private StubActivity activity;


    String [] items = new String [] {"paper", "aluminumMetals", "boxCardBoard", "glass", "plastics", "rigidPlastics", "organicMatter"};



    String [] itemsdesc = new String [] {"Paper", "Aluminum and Metals", "Boxboards and Cardboard", "Glass", "Plastics", "Rigid Plastic Containers (Labeled #1-7)", "Organic Matter"};

    public StubActivityFuctionalTest() {
        super(StubActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        activity = getActivity();
        Context context = activity.getApplicationContext();
        KeyguardManager km =
                (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        if (km.inKeyguardRestrictedInputMode())
        {
            KeyguardManager.KeyguardLock lock = km.newKeyguardLock("some_tag");
            lock.disableKeyguard();
            SystemClock.sleep(2000);
        }
    }

    public void testStub() {
        GeoCodingService service = new GeoCodingService((activity.getApplicationContext()));
        Location location = service.getCurrentLocation();
        assertNotNull("Location is null", service.getCurrentLocation());
    }

}
