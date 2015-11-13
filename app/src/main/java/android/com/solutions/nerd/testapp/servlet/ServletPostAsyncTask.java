package android.com.solutions.nerd.testapp.servlet;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cberman on 11/12/2015.
 */
public class ServletPostAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private Context context;
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
    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        HttpURLConnection urlConnection = null;
        context = params[0].first;
        String name = params[0].second;
        String json="";

        try {
            String url_string = "http://10.0.2.2:8080/hello";
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
        return json;

    }


    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}