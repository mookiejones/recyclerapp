package android.com.solutions.nerd.testapp.map;

import android.com.solutions.nerd.testapp.model.Ship;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cberman on 11/24/2015.
 */
public class ShipQueryAsyncTask extends AsyncTask<LatLngBounds, Void, List<Ship>> {

    public ShipQueryAsyncTask(OnShipQueryCompleteListener listener){
        super();
        mListener=listener;
    }


        private static final String url_path = "http://ais.boatnerd.com/ship-data.jsonp-alt.php?_1402627434151=";
        HttpURLConnection urlConnection = null;

        private OnShipQueryCompleteListener mListener;
        @Override
        protected List<Ship> doInBackground(LatLngBounds... params) {
            List<Ship> result = new ArrayList<>();

            URL url = null;
            try {
                String response = "";
                url = new URL(url_path);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String s = "";
                LatLngBounds bound =params[0];


                while ((s = reader.readLine()) != null) {
                    response += s;
                }
                response = response.replace("callback(", "");
                response = response.replace("]);", "]}");


                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (isCancelled())
                        return result;
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Ship ship = new Ship(jsonObject);
                    if (!ship.getPicture().isEmpty()){
                        URL img_url = new URL(ship.getPicture());
                        HttpURLConnection imageConnection=(HttpURLConnection)img_url.openConnection();
                        InputStream imageStream=imageConnection.getInputStream();
                        ship.setImage(BitmapFactory.decodeStream(imageStream));
                    }

                        result.add(ship);


                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param ships The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(List<Ship> ships) {
            if (isCancelled())
                return;

            mListener.QueryComplete(ships);


        }

    public interface OnShipQueryCompleteListener{
        void QueryComplete(List<Ship> ships);
    }
    public class AsyncParams{
        OnShipQueryCompleteListener mCallback;
        LatLngBounds mBounds;
        public AsyncParams(OnShipQueryCompleteListener callback,LatLngBounds bounds){
            mCallback=callback;
            mBounds=bounds;
        }
        public OnShipQueryCompleteListener getCallback(){
            return mCallback;
        }
        public LatLngBounds getBounds(){
            return mBounds;
        }

    }
}
