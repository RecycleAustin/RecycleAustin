package com.austindroids.recycleaustin.service;

import android.test.InstrumentationTestCase;

import java.util.ArrayList;

public class SodaQueryServiceTest extends InstrumentationTestCase {
    private SodaQueryService query;
    private String address;

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {

    }

    public void testSend() throws Exception {
        address = "6608 Marble Creek Loop";
        query = new SodaQueryService(address);
        ArrayList<String> results = query.send();
        boolean check1 = results.contains("Google JSON Address Problem");
        boolean check2 = results.contains("Address Problem");
        boolean check3 = results.contains("Schedule JSON Exception");
        boolean check4 = results.contains("Not in Austin");
        boolean check5 = results.size() == 4;
        assertEquals("A problem parsing the Google JSON response happened.  If this occurs, God help us!", false, check1);
        assertEquals("This will be true if a badly formatted address was passed", false, check2);
        assertEquals("This will be true if an Austin address was passed, but the schedule doesn't exist", false, check3);
        assertEquals("This will be true if someone typed in a different city than Austin", false, check4);
        assertEquals("Successful queries should always produce 4 items in results", true, check5);
        assertNotNull("Queries should never return null for results even if they fail", results);

        address = "";
        query = new SodaQueryService(address);
        results = query.send();
        check1 = results.contains("Google JSON Address Problem");
        check2 = results.contains("Address Problem");
        check3 = results.contains("Schedule JSON Exception");
        check4 = results.contains("Not in Austin");
        check5 = results.size() == 4;
        assertEquals("A problem parsing the Google JSON response happened.  If this occurs, God help us!", false, check1);
        assertEquals("This will be true if a badly formatted address was passed", true, check2);
        assertEquals("This will be true if an Austin address was passed, but the schedule doesn't exist", false, check3);
        assertEquals("This will be true if someone typed in a different city than Austin", false, check4);
        assertEquals("Failed queries should always produce less than 4 items in results", false, check5);
        assertNotNull("Queries should never return null for results even if they fail", results);

        address = "bjkaldhngaw";
        query = new SodaQueryService(address);
        results = query.send();
        check1 = results.contains("Google JSON Address Problem");
        check2 = results.contains("Address Problem");
        check3 = results.contains("Schedule JSON Exception");
        check4 = results.contains("Not in Austin");
        check5 = results.size() == 4;
        assertEquals("A problem parsing the Google JSON response happened.  If this occurs, God help us!", false, check1);
        assertEquals("This will be true if a badly formatted address was passed", true, check2);
        assertEquals("This will be true if an Austin address was passed, but the schedule doesn't exist", false, check3);
        assertEquals("This will be true if someone typed in a different city than Austin", false, check4);
        assertEquals("Failed queries should always produce less than 4 items in results", false, check5);
        assertNotNull("Queries should never return null for results even if they fail", results);

        address = "2028 Greenlee Dr, El Paso, Tx 79936";
        query = new SodaQueryService(address);
        results = query.send();
        check1 = results.contains("Google JSON Address Problem");
        check2 = results.contains("Address Problem");
        check3 = results.contains("Schedule JSON Exception");
        check4 = results.contains("Not in Austin");
        check5 = results.size() == 4;
        assertEquals("A problem parsing the Google JSON response happened.  If this occurs, God help us!", false, check1);
        assertEquals("This will be true if a badly formatted address was passed", false, check2);
        assertEquals("This will be true if an Austin address was passed, but the schedule doesn't exist", false, check3);
        assertEquals("This will be true if someone typed in a different city than Austin", true, check4);
        assertEquals("Failed queries should always produce less than 4 items in results", false, check5);
        assertNotNull("Queries should never return null for results even if they fail", results);

        address = "1016 La Posada Dr";
        query = new SodaQueryService(address);
        results = query.send();
        check1 = results.contains("Google JSON Address Problem");
        check2 = results.contains("Address Problem");
        check3 = results.contains("Schedule JSON Exception");
        check4 = results.contains("Not in Austin");
        check5 = results.size() == 4;
        assertEquals("A problem parsing the Google JSON response happened.  If this occurs, God help us!", false, check1);
        assertEquals("This will be true if a badly formatted address was passed", false, check2);
        assertEquals("This will be true if an Austin address was passed, but the schedule doesn't exist", true, check3);
        assertEquals("This will be true if someone typed in a different city than Austin", false, check4);
        assertEquals("Failed queries should always produce less than 4 items in results", false, check5);
        assertNotNull("Queries should never return null for results even if they fail", results);
    }

}