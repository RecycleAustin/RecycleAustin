package com.austindroids.recycleaustin.sodaquery;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.austindroids.recycleaustin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class SodaQueryActivity extends Activity{
    private Button sendButton;
    private EditText addressField;
    private TextView addressTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sodaquery);

        addressTest = (TextView) findViewById(R.id.test_address);
        sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ///////////// Show Alert before AsyncTask starts ////////////
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Loading");
                AlertDialog alert = builder.create();
                alert.show();

                ///////////// Get text from Fields and pass to AsyncTask //////////
                addressField = (EditText) findViewById(R.id.address_field);
                String address = addressField.getText().toString();
                if (address.equals("")) {
                    Toast.makeText(v.getContext(), "Please enter a valid address", Toast.LENGTH_SHORT).show();
                } else {
                    new GoogleHTTPGetRequest(address, alert).execute();
                }
            }
        });
    }

    /////////// Subclass AsyncTask to do query //////////////

    private class GoogleHTTPGetRequest extends AsyncTask<Void, Void, List<String>> {

        private AlertDialog alert;
        private String URLaddress = "https://maps.googleapis.com/maps/api/geocode/json?address=";

        /*
        *   This constructor takes the address that was put into the text field and also the alert dialog
        *   that was shown when the "Send" button was pressed
        */

        private GoogleHTTPGetRequest(String addr, AlertDialog alt) {
            alert = alt;
            addr = addr.trim().replace(" ", "+");
            URLaddress += addr + "+Austin,+Texas&key=AIzaSyCVfKDEiicmXo56w2Jrz0GHJ9z4wocMnoM";
        }

        //////// If doing reverse geocoding, use https://maps.googleapis.com/maps/api/geocode/json?latlng={lat},{lng}&key={API_KEY} ///////////////

        @Override
        protected List<String> doInBackground(Void... params) {
            HttpURLConnection connection = null;
            List<String> data = null;
            try {
                connection = (HttpURLConnection) new URL(URLaddress).openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream in = new BufferedInputStream(connection.getInputStream());

                data = readStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return data;
        }

        /*
        *   This method takes the entire JSON response and puts it into a StringBuilder object
        *
        */

        protected List<String> readStream(InputStream in) {
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            try {
                br = new BufferedReader(new InputStreamReader(in));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            /*
            *   This section of the method turns the StringBuilder to a String and breaks it up into the
            *   info I need: the number of the address (e.g. 1712) and the street name
            *   (e.g. West 11th Street).  It stores that info in an ArrayList.
            *   Check https://developers.google.com/maps/documentation/geocoding/#ReverseGeocoding for
            *   JSON structure.
            */

            String houseNumber = null;
            String streetName = null;
            List<String> address = new ArrayList<String>();
            try {
                JSONObject topObj = (JSONObject) new JSONTokener(sb.toString()).nextValue();
                JSONArray jArray = topObj.getJSONArray("results");
                JSONObject addressObj = jArray.getJSONObject(0);
                JSONArray addressCompsArray = addressObj.getJSONArray("address_components");
                JSONObject houseNo = addressCompsArray.getJSONObject(0);
                JSONObject streetComps = addressCompsArray.getJSONObject(1);
                houseNumber = houseNo.getString("long_name");
                streetName = streetComps.getString("long_name");
            } catch (JSONException e) {
                address.add("problem");
            }
            if (houseNumber != null && streetName != null) {
                address.add(houseNumber);
                address.add(streetName);
                return address;
            }
            return address;
        }

        /*
        *   On post execute, if the address is valid, pass the house number and street name to the next
        *   HTTPGetRequest.  If the address came back from Google with no response, make an error Toast
        *   and return.
        */

        @Override
        protected void onPostExecute(List<String> address) {
            if (address.get(0).equals("problem")) {
                alert.dismiss();
                Toast.makeText(SodaQueryActivity.this, "Problem getting correct address", Toast.LENGTH_SHORT).show();
                return;
            }
            addressTest.setText(address.get(0) + " " + address.get(1));
            new SODAHTTPGetRequest(address.get(0), address.get(1), alert).execute();
        }
    }

    private class SODAHTTPGetRequest extends AsyncTask<Void, Void, String> {

        private String URLaddress = "https://data.austintexas.gov/resource/rfif-mmvg.json?$select=collection_day,collection_week&$where=";
        private String houseNo;
        private String addr;
        private AlertDialog alert;

        private SODAHTTPGetRequest(String houseNo, String addr, AlertDialog alt) {
            this.houseNo = houseNo;
            this.addr = addr;
            URLaddress += "house_no=\'" + houseNo + "\'";
            alert = alt;
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection connection = null;
            String[] addrComponents = addr.split("\\s");
            List<String> addrComponentsParsed = new LinkedList<String>(Arrays.asList(addrComponents));

            String possibleStreetDir = addrComponentsParsed.get(0);
            Map<String, String> streetDirContainer = new StreetDirectionMap();

            if (streetDirContainer.containsKey(possibleStreetDir)) {
                addrComponentsParsed.remove(possibleStreetDir);
                String streetDirAbbr = streetDirContainer.get(possibleStreetDir);
                URLaddress += "&st_dir=" + streetDirAbbr;
            }
            Map<String, String> streetTypeContainer = new StreetTypeMap();
            String possibleStreetType = addrComponentsParsed.get(addrComponentsParsed.size() - 1);

            if (streetTypeContainer.containsKey(possibleStreetType)) {
                int index = addrComponentsParsed.size() - 1;
                addrComponentsParsed.remove(index);
                String streetTypeAbbr = streetTypeContainer.get(possibleStreetType);
                URLaddress += "&street_typ=" + streetTypeAbbr;
            }

            Iterator<String> it = addrComponentsParsed.iterator();
            StringBuilder sb = new StringBuilder();
            while (it.hasNext()) {
                sb.append(it.next());
                if (it.hasNext()) {
                    sb.append(" ");
                }
            }
            URLaddress += "&street_nam=" + sb.toString();
            String data = null;
            try {
                connection = (HttpURLConnection) new URL(URLaddress.replace(" ", "+")).openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream in = new BufferedInputStream(connection.getInputStream());

                data = readStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return data;
        }

        protected String readStream(InputStream in) {
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            try {
                br = new BufferedReader(new InputStreamReader(in));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            String collectionDay = null;
            String collectionWeek = null;
            try {
                JSONArray topArray = (JSONArray) new JSONTokener(sb.toString()).nextValue();
                JSONObject jObj = topArray.getJSONObject(0);
                collectionDay = jObj.getString("collection_day");
                collectionWeek = jObj.getString("collection_week");
            } catch (JSONException e) {
                e.printStackTrace();
                return "JSON Exception";
            }

            if (collectionDay != null && collectionWeek != null) {
                return "Collection Day: " + collectionDay + "\nCollection Week: " + collectionWeek;
            }
            return "problem";
        }

        @Override
        protected void onPostExecute(String s) {
            addressTest.append("\n");
            addressTest.append(URLaddress + "\n");
            addressTest.append(s);
            alert.dismiss();
        }
    }

}
