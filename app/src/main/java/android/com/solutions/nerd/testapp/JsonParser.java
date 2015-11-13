package android.com.solutions.nerd.testapp;

import android.com.solutions.nerd.testapp.boat.Boat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mookie on 10/29/15.
 * for Nerd.Solutions
 */
public class JsonParser {
    private static final String TAG = JsonParser.class.getSimpleName();
    static JSONArray jArray = null;
    static String json = "";

    // constructor
    public JsonParser() {
    }

    public List<Boat> getBoatsFromUrl(String args) {
        HttpURLConnection urlConnection = null;
        List<Boat> boats = new ArrayList<>();

        try {
            String url_string = Global.getApiPath(args);
            URL url = new URL(url_string);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            json = readStream(in);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        // try parse the string to a JSON object
        try {
            jArray = new JSONArray(json);


            for (int i = 0; i < jArray.length(); i++) {
                JSONObject obj = jArray.getJSONObject(i);
                Boat boat = new Boat(obj);
                boats.add(boat);
            }

        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return boats;

    }


    private String readStream(InputStream stream) {

        BufferedReader r = new BufferedReader(new InputStreamReader(stream));
        StringBuilder total = new StringBuilder();
        String line;

        try {
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total.toString();

    }


}
