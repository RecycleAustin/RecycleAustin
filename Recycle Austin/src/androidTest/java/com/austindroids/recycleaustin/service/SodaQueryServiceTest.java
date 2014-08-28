package com.austindroids.recycleaustin.service;

import android.test.InstrumentationTestCase;

import com.austindroids.recycleaustin.sodaquery.SodaListener;

import java.util.ArrayList;
import java.util.List;

public class SodaQueryServiceTest extends InstrumentationTestCase implements SodaListener {
    private SodaQueryService query;
    private String address;
    private List<String> results = new ArrayList<String>();

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {

    }

    public void testSend() throws Exception {
        address = "6608 Marble Creek Loop";
        query = new SodaQueryService(address);
        query.setSodaListener(this);
        query.send(SodaQueryService.ADDRESS_LCOATION);
        while (results.isEmpty()) {
            // wait for the results to come back from the service.
        }
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
        results = new ArrayList<String>();

        address = "";
        query = new SodaQueryService(address);
        query.setSodaListener(this);
        query.send(SodaQueryService.ADDRESS_LCOATION);
        while (results.isEmpty()) {
            // wait for the results to come back from the service.
        }
        check1 = results.contains("Google JSON Address Problem");
        check2 = results.contains("Address Problem");
        check3 = results.contains("Schedule JSON Exception");
        check4 = results.contains("Not in Austin");
        check5 = results.size() == 4;
        assertEquals("A problem parsing the Google JSON response happened.  If this occurs, God help us!", false, check1);
        assertEquals("This will be true if a badly formatted address was passed", true, check2);
        assertEquals("This will be true if an Austin address was passed, but the schedule doesn't exist", false, check3);
        assertEquals("This will be true if someone typed in a different city than Austin", false, check4);
        assertEquals("Successful queries should always produce 4 items in results", false, check5);
        assertNotNull("Queries should never return null for results even if they fail", results);
        results = new ArrayList<String>();

        address = "bjkaldhngaw";
        query = new SodaQueryService(address);
        query.setSodaListener(this);
        query.send(SodaQueryService.ADDRESS_LCOATION);
        while (results.isEmpty()) {
            // wait for the results to come back from the service.
        }
        check1 = results.contains("Google JSON Address Problem");
        check2 = results.contains("Address Problem");
        check3 = results.contains("Schedule JSON Exception");
        check4 = results.contains("Not in Austin");
        check5 = results.size() == 4;
        assertEquals("A problem parsing the Google JSON response happened.  If this occurs, God help us!", false, check1);
        assertEquals("This will be true if a badly formatted address was passed", true, check2);
        assertEquals("This will be true if an Austin address was passed, but the schedule doesn't exist", false, check3);
        assertEquals("This will be true if someone typed in a different city than Austin", false, check4);
        assertEquals("Successful queries should always produce 4 items in results", false, check5);
        assertNotNull("Queries should never return null for results even if they fail", results);
        results = new ArrayList<String>();

        address = "2028 Greenlee Dr, El Paso, Tx 79936";
        query = new SodaQueryService(address);
        query.setSodaListener(this);
        query.send(SodaQueryService.ADDRESS_LCOATION);
        while (results.isEmpty()) {
            // wait for the results to come back from the service.
        }
        check1 = results.contains("Google JSON Address Problem");
        check2 = results.contains("Address Problem");
        check3 = results.contains("Schedule JSON Exception");
        check4 = results.contains("Not in Austin");
        check5 = results.size() == 4;
        assertEquals("A problem parsing the Google JSON response happened.  If this occurs, God help us!", false, check1);
        assertEquals("This will be true if a badly formatted address was passed", false, check2);
        assertEquals("This will be true if an Austin address was passed, but the schedule doesn't exist", false, check3);
        assertEquals("This will be true if someone typed in a different city than Austin", true, check4);
        assertEquals("Successful queries should always produce 4 items in results", false, check5);
        assertNotNull("Queries should never return null for results even if they fail", results);
        results = new ArrayList<String>();

        address = "1016 La Posada Dr";
        query = new SodaQueryService(address);
        query.setSodaListener(this);
        query.send(SodaQueryService.ADDRESS_LCOATION);
        while (results.isEmpty()) {
            // wait for the results to come back from the service.
        }
        check1 = results.contains("Google JSON Address Problem");
        check2 = results.contains("Address Problem");
        check3 = results.contains("Schedule JSON Exception");
        check4 = results.contains("Not in Austin");
        check5 = results.size() == 4;
        assertEquals("A problem parsing the Google JSON response happened.  If this occurs, God help us!", false, check1);
        assertEquals("This will be true if a badly formatted address was passed", false, check2);
        assertEquals("This will be true if an Austin address was passed, but the schedule doesn't exist", true, check3);
        assertEquals("This will be true if someone typed in a different city than Austin", false, check4);
        assertEquals("Successful queries should always produce 4 items in results", false, check5);
        assertNotNull("Queries should never return null for results even if they fail", results);
        results = new ArrayList<String>();

        address = "30.2643314,-97.71830079999999";
        query = new SodaQueryService(address);
        query.setSodaListener(this);
        query.send(SodaQueryService.GPS_LOCATION);
        while (results.isEmpty()) {
            // wait for the results to come back from the service.
        }
        check1 = results.contains("Google JSON Address Problem");
        check2 = results.contains("Address Problem");
        check3 = results.contains("Schedule JSON Exception");
        check4 = results.contains("Not in Austin");
        check5 = results.size() == 4;
        boolean check6 = results.contains("2103 East 10th Street");
        assertEquals("A problem parsing the Google JSON response happened.  If this occurs, God help us!", false, check1);
        assertEquals("This will be true if a badly formatted address was passed", false, check2);
        assertEquals("This will be true if an Austin address was passed, but the schedule doesn't exist", false, check3);
        assertEquals("This will be true if someone typed in a different city than Austin", false, check4);
        assertEquals("Successful queries should always produce 4 items in results", true, check5);
        assertNotNull("Queries should never return null for results even if they fail", results);
        assertEquals("If the address is in Austin, the service should include the street address", true, check6);
        results = new ArrayList<String>();

        address = "31.769688,-106.293973";
        query = new SodaQueryService(address);
        query.setSodaListener(this);
        query.send(SodaQueryService.GPS_LOCATION);
        while (results.isEmpty()) {
            // wait for the results to come back from the service.
        }
        check1 = results.contains("Google JSON Address Problem");
        check2 = results.contains("Address Problem");
        check3 = results.contains("Schedule JSON Exception");
        check4 = results.contains("Not in Austin");
        check5 = results.size() == 4;
        check6 = results.contains("2028 Greenlee Drive");
        assertEquals("A problem parsing the Google JSON response happened.  If this occurs, God help us!", false, check1);
        assertEquals("This will be true if a badly formatted address was passed", false, check2);
        assertEquals("This will be true if an Austin address was passed, but the schedule doesn't exist", false, check3);
        assertEquals("This will be true if someone typed in a different city than Austin", true, check4);
        assertEquals("Successful queries should always produce 4 items in results", false, check5);
        assertNotNull("Queries should never return null for results even if they fail", results);
        assertEquals("If an address is not in Austin, the service won't bother including the street address", false, check6);
        results = new ArrayList<String>();

        address = "30.326022,-97.70274099999999";
        query = new SodaQueryService(address);
        query.setSodaListener(this);
        query.send(SodaQueryService.GPS_LOCATION);
        while (results.isEmpty()) {
            // wait for the results to come back from the service.
        }
        check1 = results.contains("Google JSON Address Problem");
        check2 = results.contains("Address Problem");
        check3 = results.contains("Schedule JSON Exception");
        check4 = results.contains("Not in Austin");
        check5 = results.size() == 4;
        check6 = results.contains("1016 La Posada Drive");
        assertEquals("A problem parsing the Google JSON response happened.  If this occurs, God help us!", false, check1);
        assertEquals("This will be true if a badly formatted address was passed", false, check2);
        assertEquals("This will be true if an Austin address was passed, but the schedule doesn't exist", true, check3);
        assertEquals("This will be true if someone typed in a different city than Austin", false, check4);
        assertEquals("Successful queries should always produce 4 items in results", false, check5);
        assertNotNull("Queries should never return null for results even if they fail", results);
        assertEquals("If the address is in Austin, the service should include the street address", true, check6);
        results = new ArrayList<String>();

        address = "30.177044,-97.744585";
        query = new SodaQueryService(address);
        query.setSodaListener(this);
        query.send(SodaQueryService.GPS_LOCATION);
        while (results.isEmpty()) {
            // wait for the results to come back from the service.
        }
        check1 = results.contains("Google JSON Address Problem");
        check2 = results.contains("Address Problem");
        check3 = results.contains("Schedule JSON Exception");
        check4 = results.contains("Not in Austin");
        check5 = results.size() == 4;
        assertEquals("A problem parsing the Google JSON response happened.  If this occurs, God help us!", false, check1);
        assertEquals("This will be true if a badly formatted address was passed", true, check2);
        assertEquals("This will be true if an Austin address was passed, but the schedule doesn't exist", false, check3);
        assertEquals("This will be true if someone typed in a different city than Austin", false, check4);
        assertEquals("Successful queries should always produce 4 items in results", false, check5);
        assertNotNull("Queries should never return null for results even if they fail", results);
    }

    @Override
    public void resultsSent(List<String> results) {
        this.results.addAll(results);
    }
}