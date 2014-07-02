package com.austindroids.recycleaustin.service;

/**
 * Created by dutch on 7/2/14.
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A unit test class based on junit 4+ and using annotations. Note you do not have to inherit from UnitTest.
 */
public class CheckRecyclableServiceTest {

    CheckRecyclableService service;
    /**
     * Does setup before any unit test is run
     */
    @Before
    public void setup() {
        service = new CheckRecyclableService();
    }

    /**
     * Do any cleanup after a single test is run.
     */
    @After
    public void teardown() {
    }

    /**
     * does setup before the start of all unit tests
     */
    @BeforeClass
    public static void setupClass() {
    }

    @Test
    public void testGetCategories() {
        String [] cats = {"category1", "category2"};
        List <String> expected = Arrays.asList(cats);

        List <String> actual = service.getCategories();
        assertNotNull("Returned categories was null.", actual);
        assertNotEquals("Returned categories have no entries", 0, actual.size());
        assertEquals("Categories returned was different from expected.", expected, actual);
    }

    /**
     * This test method will be ignored by the runner.
     */
    @Ignore
    public void testSomething() {
        // will always fail
        fail();
    }


    @Test(expected=NullPointerException.class)
    public void testFailure() {
        String str = null;
        str.compareTo("something");
    }
}
