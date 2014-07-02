package com.austindroids.recycleaustin.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.austindroids.recycleaustin.R;


/**
 * Provides unit testing of an Activity. This test creates the Activity in isolation, and provides it with a context.
 *
 * @author Dutch
 */
public class MainActivityUnitTest extends
        android.test.ActivityUnitTestCase<MainActivity> {

    private int gridviewId;
    private MainActivity activity;

    public MainActivityUnitTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                MainActivity.class);
        startActivity(intent, null, null);
        activity = getActivity();
    }

    @SmallTest
    public void testLayout() {
        gridviewId = R.id.dashboard_grid;
        assertNotNull(activity.findViewById(gridviewId));
        GridView view = (GridView) activity.findViewById(gridviewId);

        assertEquals("Incorrect stretchmode", GridView.STRETCH_COLUMN_WIDTH, view.getStretchMode());
    }

    @SmallTest
    public void testIntentTriggerViaOnClick() {
        int position = 1;
        assertNotNull(activity);
        GridView gridview = (GridView) activity.findViewById(R.id.dashboard_grid);
        ListAdapter adapter = gridview.getAdapter();


        gridview.setSelection(position);
        assertEquals(position, gridview.getSelectedItemPosition());

        AdapterView.OnItemClickListener listener = (AdapterView.OnItemClickListener) activity;

        gridview.performItemClick(
                gridview.getAdapter().getView(position, null, null),
                position,
                gridview.getAdapter().getItemId(position));

        // TouchUtils cannot be used, only allowed in
        // InstrumentationTestCase or ActivityInstrumentationTestCase2

        // Check the intent which was started
        Intent triggeredIntent = getStartedActivityIntent();
        ComponentName className = triggeredIntent.getComponent();
        assertEquals("Activity doesn't match",
                "com.austindroids.recycleaustin.activities.RecycleLocationsActivity",
                className.getClassName());
    }
}