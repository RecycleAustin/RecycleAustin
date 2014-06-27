package com.austindroids.recycleaustin.activities;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Spinner;
import android.widget.TextView;

import com.austindroids.recycleaustin.R;

/**
 * Created by Dutch on 6/27/2014.
 */
public class RecycleFlowScreenActivityFunctionalTest
        extends
        ActivityInstrumentationTestCase2<RecycleFlowScreenActivity> {

    private RecycleFlowScreenActivity activity;
    /** The Spinner widget to be tested */
    Spinner mSpinner;
    TextView spinnerText;


    String [] items = new String [] {"paper", "aluminumMetals", "boxCardBoard", "glass", "plastics", "rigidPlastics", "organicMatter"};



    String [] itemsdesc = new String [] {"Paper", "Aluminum and Metals", "Boxboards and Cardboard", "Glass", "Plastics", "Rigid Plastic Containers (Labeled #1-7)", "Organic Matter"};

    public RecycleFlowScreenActivityFunctionalTest() {
        super(RecycleFlowScreenActivity.class);
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

    public void testClickSpinner() {
        mSpinner = (Spinner) activity.findViewById(R.id.spinnerOps1);

        final int INITIAL_POSITION=0;
        final int FINAL_POSITION=mSpinner.getAdapter().getCount();
        String expected = "";
        String actual = "";

        /** Iterates through each item in the Spinner widget */
        for(int i=INITIAL_POSITION;i<=FINAL_POSITION;i++){
            try {
                runTestOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /** Sets the first item as the current item in the Spinner widget */
                        mSpinner.setSelection(INITIAL_POSITION);
                    }
                });
            }catch (Throwable e) {
                e.printStackTrace();
            }

            /** Taps the Spinner Widget */
            TouchUtils.tapView(this, mSpinner);

            /** Send i number of Down KeyPad event to Spinner widget,
             * since it is the currently selected widget */
            sendRepeatedKeys(i,KeyEvent.KEYCODE_DPAD_DOWN);

            /** Sets the currently selected item as the current item of the Spinner */
            sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);

            /** Getting the currently selected item */
            actual = mSpinner.getSelectedItem().toString();

            /** Getting the text displayed in the TextView widget */
            expected = itemsdesc[i];

            /** Checks, whether currently selected Spinner item and the text displayed are same
             * If same, then the test is success, otherwise failed*/
            assertEquals("Text of Spinner was not expected.", expected, actual);

            /** This Thread.sleep() is not necessary, but provided just to slow down the testing process
             * so that we can manually monitor the happenings while testing in progress
             */
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
