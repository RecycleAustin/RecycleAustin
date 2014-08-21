package com.austindroids.recycleaustin.service;

import android.os.AsyncTask;

import com.austindroids.recycleaustin.beans.PostalAddress;
import com.austindroids.recycleaustin.sodaquery.StreetDirectionMap;
import com.austindroids.recycleaustin.sodaquery.StreetTypeMap;

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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by mt_slasher on 7/11/14.
 */

public class SodaQueryService {
    private String addr;
    private ArrayList<String> results = new ArrayList<String>();
    private PostalAddress postalAddress;
    public static final String GPS_LOCATION = "latlng=";
    public static final String ADDRESS_LCOATION = "address=";

    public SodaQueryService(String anAddress) {
        addr = anAddress;
    }

    public ArrayList<String> send(String locationType) {
        GoogleHTTPGetRequest googleRequest = new GoogleHTTPGetRequest(addr, locationType);
        googleRequest.execute();
        try {
            googleRequest.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            results.add("Timeout");
        }
        if (!results.contains("Timeout") && !results.contains("JSON Address Problem") && !results.contains("Not in Austin")
                 && !results.contains("Address Problem")) {
            SODAHTTPGetRequest sodaRequest = new SODAHTTPGetRequest(postalAddress.getHouseNumber(), postalAddress.getStreet());
            sodaRequest.execute();
            try {
                sodaRequest.get(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                results.add("Timeout");
            }
        }
        return results;
    }

    private class GoogleHTTPGetRequest extends AsyncTask<Void, Void, Void> {

        private String URLaddress = "https://maps.googleapis.com/maps/api/geocode/json?";
        private final String longName = "long_name";

        private GoogleHTTPGetRequest(String addr, String locationType) {
            addr = addr.trim().replace(" ", "+");
            if(locationType.equals(ADDRESS_LCOATION)) {
                URLaddress += ADDRESS_LCOATION + addr + "+Austin,+Texas&key=AIzaSyCVfKDEiicmXo56w2Jrz0GHJ9z4wocMnoM";
            } else if (locationType.equals(GPS_LOCATION)) {
                URLaddress += GPS_LOCATION + addr + "&key=AIzaSyCVfKDEiicmXo56w2Jrz0GHJ9z4wocMnoM";
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(URLaddress).openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream in = new BufferedInputStream(connection.getInputStream());
                readStream(in);
            } catch (IOException e) {
                e.printStackTrace();
                results.add("Google JSON Address Problem");
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        protected void readStream(InputStream in) {
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

            postalAddress = new PostalAddress();
            try {
                JSONObject topObj = (JSONObject) new JSONTokener(sb.toString()).nextValue();
                JSONArray jArray = topObj.getJSONArray("results");
                JSONObject addressObj = jArray.getJSONObject(0);
                JSONArray addressCompsArray = addressObj.getJSONArray("address_components");
                for (int i = 0; i < addressCompsArray.length(); i++) {
                    JSONObject object = addressCompsArray.getJSONObject(i);
                    JSONArray types = object.getJSONArray("types");
                    String component = types.getString(0);
                    if (component.equals("street_number")) {
                        postalAddress.setHouseNumber(object.getString(longName));
                    } else if (component.equals("route")) {
                        postalAddress.setStreet(object.getString(longName));
                    } else if (component.equals("locality") || component.equals("sublocality")) {
                        postalAddress.setCity(object.getString(longName));
                    } else if (component.equals("administrative_area_level_1")) {
                        postalAddress.setState(object.getString(longName));
                    } else if (component.equals("postal_code")) {
                        postalAddress.setZip(object.getString(longName));
                    }
                }
            } catch (JSONException e) {
                results.add("Google JSON Address Problem");
                return;
            }
            String houseNumber = postalAddress.getHouseNumber();
            String streetName = postalAddress.getStreet();
            if (!postalAddress.getCity().equals("Austin")) {
                results.add("Not in Austin");
            } else if (houseNumber != null && streetName != null) {
                results.add(houseNumber + " " + streetName);
                results.add(postalAddress.getCity() + ", " + postalAddress.getState() + " " + postalAddress.getZip());
            } else {
                results.add("Address Problem");
            }
        }
    }

    private class SODAHTTPGetRequest extends AsyncTask<Void, Void, Void> {

        private String URLaddress = "https://data.austintexas.gov/resource/rfif-mmvg.json?$select=collection_day,collection_week&$where=";
        private String houseNo;
        private String addr;

        private SODAHTTPGetRequest(String houseNo, String addr) {
            this.houseNo = houseNo;
            this.addr = addr;
            URLaddress += "house_no=\'" + houseNo + "\'";
        }

        @Override
        protected Void doInBackground(Void... params) {
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
            int endingIndex = addrComponentsParsed.size() - 1;
            String possibleStreetType = addrComponentsParsed.get(endingIndex);

            if (streetTypeContainer.containsKey(possibleStreetType)) {
                addrComponentsParsed.remove(endingIndex);
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
            try {
                connection = (HttpURLConnection) new URL(URLaddress.replace(" ", "+")).openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream in = new BufferedInputStream(connection.getInputStream());
                readStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        protected void readStream(InputStream in) {
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
                results.add("Schedule JSON Exception");
                return;
            }

            results.add("Collection Day: " + collectionDay);
            results.add("Collection Week: " + collectionWeek);
        }
    }
}
