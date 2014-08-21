package com.austindroids.recycleaustin.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.austindroids.recycleaustin.R;
import com.austindroids.recycleaustin.service.SodaQueryService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;


public class SodaQueryActivity extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {
    private Button gpsButton;
    private Button sendButton;
    private EditText addressField;
    private TextView addressTest;
    private final static int
            CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    public static final String APPTAG = "RecycleAustin";
    public static final String EMPTY_STRING = new String();
    private LocationClient locationClient;
    private Location location;
    private LocationRequest locationRequest;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sodaquery);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);

        locationClient = new LocationClient(this, this, this);

        gpsButton = (Button) findViewById(R.id.gps_button);
        gpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (servicesConnected()) {


                    address = getLatLng(location);
                    if (address.equals(EMPTY_STRING)) {
                        Toast.makeText(v.getContext(), "Problem with GPS Locating", Toast.LENGTH_SHORT).show();
                    } else {
                        ///////////// Show Alert before AsyncTask starts ////////////
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setMessage("Loading");
                        AlertDialog alert = builder.create();
                        alert.setCancelable(false);
                        alert.show();
                        SodaQueryService sodaQuery = new SodaQueryService(address);
                        ArrayList<String> results = sodaQuery.send(SodaQueryService.GPS_LOCATION);
                        alert.dismiss();
                        if (results.isEmpty() || results.contains("Timeout")) {
                            Toast.makeText(SodaQueryActivity.this, "Problem contacting database", Toast.LENGTH_LONG).show();
                        } else if (results.contains("Address Problem") || results.contains("JSON Address Problem")) {
                            Toast.makeText(SodaQueryActivity.this, "Problem getting address", Toast.LENGTH_LONG).show();
                        } else if (results.contains("Schedule JSON Exception")) {
                            Toast.makeText(SodaQueryActivity.this, "No schedule for address", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(SodaQueryActivity.this, ScheduleActivity.class);
                            intent.putExtra(ScheduleActivity.SCHEDULE_STREET, results.get(0));
                            intent.putExtra(ScheduleActivity.SCHEDULE_CITY_STATE, results.get(1));
                            intent.putExtra(ScheduleActivity.SCHEDULE_DAY, results.get(2));
                            intent.putExtra(ScheduleActivity.SCHEDULE_WEEK, results.get(3));
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        addressTest = (TextView) findViewById(R.id.test_address);
        sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressField = (EditText) findViewById(R.id.address_field);
                address = addressField.getText().toString();

                ///////////// Check if address is blank //////////////////////
                if (address.equals("")) {
                    Toast.makeText(v.getContext(), "Please enter a valid address", Toast.LENGTH_SHORT).show();
                } else {
                    ///////////// Show Alert before AsyncTask starts ////////////
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Loading");
                    AlertDialog alert = builder.create();
                    alert.setCancelable(false);
                    alert.show();

                    ArrayList<String> results = new SodaQueryService(address).send(SodaQueryService.ADDRESS_LCOATION);
                    alert.dismiss();
                    if (results.isEmpty() || results.contains("Timeout")) {
                        Toast.makeText(SodaQueryActivity.this, "Problem contacting database", Toast.LENGTH_LONG).show();
                    } else if (results.contains("Address Problem") || results.contains("JSON Address Problem")) {
                        Toast.makeText(SodaQueryActivity.this, "Problem getting address", Toast.LENGTH_LONG).show();
                    } else if (results.contains("Schedule JSON Exception")) {
                        Toast.makeText(SodaQueryActivity.this, "No schedule for address", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(SodaQueryActivity.this, ScheduleActivity.class);
                        intent.putExtra(ScheduleActivity.SCHEDULE_STREET, results.get(0));
                        intent.putExtra(ScheduleActivity.SCHEDULE_CITY_STATE, results.get(1));
                        intent.putExtra(ScheduleActivity.SCHEDULE_DAY, results.get(2));
                        intent.putExtra(ScheduleActivity.SCHEDULE_WEEK, results.get(3));
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public String getLatLng(Location currentLocation) {
        if (currentLocation != null) {

            // Return the latitude and longitude as strings
            return getString(
                    R.string.latitude_longitude,
                    currentLocation.getLatitude(),
                    currentLocation.getLongitude());
        } else {

            // Otherwise, return the empty string
            return EMPTY_STRING;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        locationClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Disconnect the client.
        locationClient.removeLocationUpdates(this);
        locationClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        location = locationClient.getLastLocation();
        locationClient.requestLocationUpdates(locationRequest, this);
    }

    @Override
    public void onDisconnected() {
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            showErrorDialog(connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        Toast.makeText(this, "Location updated", Toast.LENGTH_LONG).show();
    }

    // Define a DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends DialogFragment {
        // Global field to contain the error dialog
        private Dialog mDialog;
        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }
        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }
        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }

    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.
                        isGooglePlayServicesAvailable(this);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates",
                    "Google Play services is available.");
            // Continue
            return true;
            // Google Play services was not available for some reason
        } else {
            // Get the error code
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                    resultCode,
                    this,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment =
                        new ErrorDialogFragment();
                // Set the dialog in the DialogFragment
                errorFragment.setDialog(errorDialog);
                // Show the error dialog in the DialogFragment
                errorFragment.show(getSupportFragmentManager(),
                        "Location Updates");
            }
        }
        return false;
    }

    private void showErrorDialog(int errorCode) {

        // Get the error dialog from Google Play services
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                errorCode,
                this,
                CONNECTION_FAILURE_RESOLUTION_REQUEST);

        // If Google Play services can provide an error dialog
        if (errorDialog != null) {

            // Create a new DialogFragment in which to show the error dialog
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

            // Set the dialog in the DialogFragment
            errorFragment.setDialog(errorDialog);

            // Show the error dialog in the DialogFragment
            errorFragment.show(getSupportFragmentManager(), APPTAG);
        }
    }

}
