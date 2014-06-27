package com.austindroids.recycleaustin.service;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.austindroids.recycleaustin.beans.PostalAddress;

/**
 * Created by dutch on 6/18/14.
 */

public class GeoCodingService {

    Context thisContext;

    public GeoCodingService(Context thisContext) {
        this.thisContext = thisContext;
    }

    public PostalAddress getAddressFromLocation(Location location) {
        PostalAddress address = new PostalAddress();
        address.setHouseNumber("2302");
        address.setStreet("10TH");
        address.setCity("AUSTIN");
        address.setState("TX");
        address.setZip("78703");
        return address;
    }


    public Location getCurrentLocation() {
        // Get the location manager
        LocationManager locationManager = (LocationManager) thisContext.getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria to select location provider (default)
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        return location;
    }
}