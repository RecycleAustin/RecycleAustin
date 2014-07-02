package com.austindroids.recycleaustin.service;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;


/**
 *  Provides unit testing of a service, but does so in an Android environment. Provides a real (not mock) context to
 *  the service being tested.
 *
 *  @author Dutch
 */
public class GeoCodingServiceTest extends ApplicationTestCase<TestApplication> {



    public GeoCodingServiceTest() {
        super(TestApplication.class);
    }

    public void testService() {
        //testApplicationTestCaseSetUpProperly();
        Context context = getSystemContext();
        GeoCodingService service = new GeoCodingService(context);
        assertNotNull("Context is null.", context);
        Location location =  service.getCurrentLocation();
        assertNotNull("Returned location is null.", location);
        assertTrue("Location is not valid in austin", (location.getLatitude() > 0));

    }

}
