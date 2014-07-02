package com.austindroids.recycleaustin.service;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.austindroids.recycleaustin.beans.PostalAddress;

import java.util.List;

/**
 * Created by dutch on 6/18/14.
 */

public class GeoCodingService {

    Context thisContext;

    public GeoCodingService(Context thisContext) {
        this.thisContext = thisContext;
        LocationManager locationManager = (LocationManager) thisContext.getSystemService(Context.LOCATION_SERVICE);
        LocationListener mlocListener = new MyLocationListener();

        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
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
        // this does not actually do a GPS lookup!
        Location location = locationManager.getLastKnownLocation(provider);
        if (location == null) {
            location = this.getPreviousLocation();
        }

        return location;
    }

    private Location getPreviousLocation() {
        String location_context = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) thisContext.getSystemService(location_context);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            log("","last known location, provider: %s, location: %s", provider,
                    l);

            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {
                log("found best last known location: %s", l);
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        System.out.println("GOTHERE");
        return bestLocation;
    }

    protected void log(String format, Object... msg) {
        Log.i("com.austindroids.recycleaustin", String.format(format, msg));
    }

       /* Class My Location Listener */

    public class MyLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location loc)
        {

            loc.getLatitude();
            loc.getLongitude();
            String Text = "My current location is: " +
                    "Latitud = " + loc.getLatitude() +
                    "Longitud = " + loc.getLongitude();
            Toast.makeText(thisContext,
                    Text,
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Toast.makeText( thisContext,
                    "Gps Disabled",
                    Toast.LENGTH_SHORT ).show();
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Toast.makeText(thisContext,
                    "Gps Enabled",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }

    }/* End of Class MyLocationListener */


}