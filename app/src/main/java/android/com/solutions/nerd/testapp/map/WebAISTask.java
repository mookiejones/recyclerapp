package android.com.solutions.nerd.testapp.map;

import android.com.solutions.nerd.testapp.Global;
import android.com.solutions.nerd.testapp.utils.LogUtils;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 11/7/15.
 */
public class WebAISTask extends AsyncTask<String,Void,String> {

    private static final String TAG = LogUtils.getLogTag(WebAISTask.class);
    private static String url_path = "http://www.marinetraffic.com/map/getjson/sw_x:-90.0/sw_y:35.0/ne_x:-70.0/ne_y:50.0/zoom:7/station:0/focusedshipid:146267";

    static String json = "";

    @Override

    protected String doInBackground(String... params) {
        String target = params[0];
        HttpURLConnection urlConnection = null;

        try {

            URL url = new URL(url_path);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            json = readStream(in);
         return json;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            return null;
        }

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.i(TAG, "got json");
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
