package android.com.solutions.nerd.testapp.boat;

import android.com.solutions.nerd.testapp.Global;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mookie on 10/29/15.
 * for Nerd.Solutions
 */
public class Boat {

    public static final String TAG_DISPLACEMENT = "disp";
    public static final String TAG_SA_DISPLACEMENT = "sa_disp";
    public static final String TAG_SA_LIST = "sa_list";
    public static final String TAG_DRAFT_MAX = "draft_max";
    public static final String TAG_TOTAL_CALC = "total_calc";
    public static final String TAG_LOA = "loa";
    public static final String TAG_TITLE = "title";
    public static final String TAG_BAL_DISP = "bal_disp";
    public static final String TAG_CONSTRUCT = "construct";
    public static final String TAG_RIG_TYPE = "rig_type";
    public static final String TAG_LWL = "lwl";
    public static final String TAG_BALLAST = "ballast";
    public static final String TAG_DESIGNER = "designer";
    public static final String TAG_FIRST_BUILD = "first_built";
    public static final String TAG_HULL = "hull";
    public static final String TAG_BEAM = "beam";
    public static final String TAG_SA_FORE = "sa_fore";
    public static final String TAG_E = "e";
    public static final String TAG_I = "i";
    public static final String TAG_J = "j";
    public static final String TAG_P = "p";
    public static final String TAG_NUM_BUILT = "num_built";
    public static final String TAG_BAL_TYPE = "bal_type";
    public static final String TAG_FUEL = "fuel";
    public static final String TAG_TYPE = "type";
    public static final String TAG_WATER = "water";
    public static final String TAG_HP = "hp";
    public static final String TAG_MAST_HEIGHT = "mast_height";
    public static final String TAG_IMAGES = "images";
    private static final String TAG = Boat.class.getSimpleName();
    private String ballast = "";
    private String beam = "";
    private String builder = "";
    private String construct = "";
    private String designer = "";
    private String disp = "";
    private String draft_min = "";
    private String e = "";
    private String ey = "";
    private String first_built = "";
    private String hp = "";
    private String hull = "";
    private String i = "";
    private String[] images;
    private String isp = "";
    private String j = "";
    private String last_built = "";
    private String lwl = "";
    private String make = "";
    private String mast_height = "";
    private String model = "";
    private String more = "";
    private String p = "";
    private String py = "";
    private String rig_type = "";
    private String sa_fore = "";
    private String spl = "";
    private String title = "";
    private String water = "";
    private String displacement = "";
    private String sa_disp = "";
    private String sa_list = "";
    private String draft_max = "";
    private String total_calc = "";
    private String loa = "";
    private String bal_disp = "";
    private String num_built = "";
    private String bal_type = "";
    private String fuel = "";
    private String type = "";

    public Boat(JSONObject boat) {
        try {

            if (boat.has(TAG_IMAGES)) {
                JSONArray imageArray = boat.getJSONArray(TAG_IMAGES);
                List<String> tmpImages = new ArrayList<>();
                for (int i = 0; i < imageArray.length(); i++) {
                    String img = Global.api_image_path+imageArray.get(i).toString()+".jpg";

                    tmpImages.add(img);
                }
                images = tmpImages.toArray(new String[tmpImages.size()]);
            }


            if (boat.has(TAG_BAL_DISP))
                bal_disp = boat.getString(TAG_BAL_DISP);

            if (boat.has(TAG_BAL_TYPE))
                bal_type = boat.getString(TAG_BAL_TYPE);

            if (boat.has(TAG_BALLAST))
                ballast = boat.getString(TAG_BALLAST);


            if (boat.has(TAG_BEAM))
                beam = boat.getString(TAG_BEAM);

            if (boat.has(TAG_CONSTRUCT))
                construct = boat.getString(TAG_CONSTRUCT);

            if (boat.has(TAG_DESIGNER))
                designer = boat.getString(TAG_DESIGNER);

            if (boat.has(TAG_DISPLACEMENT))
                displacement = boat.getString(TAG_DISPLACEMENT);

            if (boat.has(TAG_DRAFT_MAX))
                draft_max = boat.getString(TAG_DRAFT_MAX);
            if (boat.has(TAG_E))
                e = boat.getString(TAG_E);
            if (boat.has(TAG_FIRST_BUILD))
                first_built = boat.getString(TAG_FIRST_BUILD);

            if (boat.has(TAG_FUEL))
                fuel = boat.getString(TAG_FUEL);

            if (boat.has(TAG_HP))
                hp = boat.getString(TAG_HP);

            if (boat.has(TAG_HULL))
                hull = boat.getString(TAG_HULL);

            if (boat.has(TAG_I))
                i = boat.getString(TAG_I);

            if (boat.has(TAG_J))
                j = boat.getString(TAG_J);

            if (boat.has(TAG_LOA))
                loa = boat.getString(TAG_LOA);

            if (boat.has(TAG_LWL))
                lwl = boat.getString(TAG_LWL);

            if (boat.has(TAG_MAST_HEIGHT))
                mast_height = boat.getString(TAG_MAST_HEIGHT);

            if (boat.has(TAG_NUM_BUILT))
                num_built = boat.getString(TAG_NUM_BUILT);

            if (boat.has(TAG_P))
                p = boat.getString(TAG_P);

            if (boat.has(TAG_RIG_TYPE))
                rig_type = boat.getString(TAG_RIG_TYPE);

            if (boat.has(TAG_SA_DISPLACEMENT))
                sa_disp = boat.getString(TAG_SA_DISPLACEMENT);

            if (boat.has(TAG_SA_FORE))
                sa_fore = boat.getString(TAG_SA_FORE);
            if (boat.has(TAG_SA_LIST))
                sa_list = boat.getString(TAG_SA_LIST);

            if (boat.has(TAG_TITLE))
                title = boat.getString(TAG_TITLE);

            if (boat.has(TAG_TOTAL_CALC))
                total_calc = boat.getString(TAG_TOTAL_CALC);

            if (boat.has(TAG_TYPE))
                type = boat.getString(TAG_TYPE);

            if (boat.has(TAG_WATER))
                water = boat.getString(TAG_WATER);

            String searchString = "Sailboat";
            if (!model.isEmpty())
                searchString = "%20" + model;
            if (!title.isEmpty())
                searchString += "%20" + title;

            new ImageListParser().execute(searchString);

        } catch (JSONException e1) {
            e1.printStackTrace();
        }


    }

    public String getBallast() {
        return ballast;
    }

    public String getBeam() {
        return beam;
    }

    public String getBuilder() {
        return builder;
    }

    public String getConstruct() {
        return construct;
    }

    public String getDesigner() {
        return designer;
    }

    public String getDisp() {
        return disp;
    }

    public String getDraft_min() {
        return draft_min;
    }

    public String getE() {
        return e;
    }

    public String getEy() {
        return ey;
    }

    public String getFirst_built() {
        return first_built;
    }

    public String getHp() {
        return hp;
    }

    public String getHull() {
        return hull;
    }

    public String getI() {
        return i;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(List<String> strings) {
        images = strings.toArray(new String[strings.size()]);
    }

    public String getIsp() {
        return isp;
    }

    public String getJ() {
        return j;
    }

    public String getLast_built() {
        return last_built;
    }

    public String getLwl() {
        return lwl;
    }

    public String getMake() {
        return make;
    }

    public String getMast_height() {
        return mast_height;
    }

    public String getModel() {
        return model;
    }

    public String getMore() {
        return more;
    }

    public String getP() {
        return p;
    }

    public String getPy() {
        return py;
    }

    public String getRig_type() {
        return rig_type;
    }

    public String getSa_fore() {
        return sa_fore;
    }

    public String getSpl() {
        return spl;
    }

    public String getTitle() {
        return title;
    }

    public String getWater() {
        return water;
    }

    public String getDisplacement() {
        return displacement;
    }

    public String getSa_disp() {
        return sa_disp;
    }

    public String getSa_list() {
        return sa_list;
    }

    public String getDraft_max() {
        return draft_max;
    }

    public String getTotal_calc() {
        return total_calc;
    }

    public String getLoa() {
        return loa;
    }

    public String getBal_disp() {
        return bal_disp;
    }

    public String getNum_built() {
        return num_built;
    }

    public String getBal_type() {
        return bal_type;
    }

    public String getFuel() {
        return fuel;
    }

    public String getType() {
        return type;
    }

    public String getImage(int position) {

        if (images == null)
            return "";
        return images[position];
    }

    private class ImageListParser extends AsyncTask<String, String, List<String>> {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected List<String> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            List<String> images = new ArrayList<>();
            String result = "";
            try {
                String url_string = Global.getImageSearchUrl(params[0]);
                URL url = new URL(url_string);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = readStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject parentObject = jsonObject.getJSONObject("responseData");
                JSONArray jArray = parentObject.getJSONArray("results");


                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject obj = jArray.getJSONObject(i);
                    if (obj.has("url")) {

                        images.add(obj.getString("url"));

                    }
                }

                Log.d(TAG, "Found " + String.valueOf(images.size()) + " images.");


            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }


            return images;


        }


        @Override
        protected void onPostExecute(List<String> result) {


            List<String> strings = new ArrayList<>();
            if (result.size() > 0){
                strings = result;

                if (images!=null&&images.length>0) {
                    for (String s : images) {
                        strings.add(s);
                    }
                }
            }

          //  setImages(strings);
        }

        private String readStream(InputStream stream) {

            BufferedReader r = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            String line;

            try {
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }
            } catch (InterruptedIOException e) {
            } catch (IOException e) {
                e.printStackTrace();
            }


            return total.toString();

        }


    }

}
