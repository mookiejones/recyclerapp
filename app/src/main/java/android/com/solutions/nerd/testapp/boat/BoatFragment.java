package android.com.solutions.nerd.testapp.boat;

import android.com.solutions.nerd.testapp.Global;
import android.com.solutions.nerd.testapp.ITextQueryListener;
import android.com.solutions.nerd.testapp.JsonParser;
import android.com.solutions.nerd.testapp.R;
import android.com.solutions.nerd.testapp.main.MainActivity;
import android.com.solutions.nerd.testapp.utils.LogUtils;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
 * Created by mookie on 10/22/15.
 * for Nerd.Solutions
 */
public class BoatFragment extends Fragment
        implements ITextQueryListener
{

    private static final String TAG = LogUtils.getLogTag(BoatFragment.class);
    private static BoatFragment instance;
    BoatParser mParser;
    List<Boat> boatList;

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;

    public static BoatFragment getInstance() {
        if (instance == null)
            instance = new BoatFragment();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        BoatArrayAdapter mAdapter = new BoatArrayAdapter(getContext(), new ArrayList<Boat>());


        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.boat_fragment, null);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mProgressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        // use a linear layout manager
         RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        registerForContextMenu(mRecyclerView);

     //   OnTextChanged("Irwin");

        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.VISIBLE);

        if (mParser != null)
            mParser.cancel(true);
        mParser = new BoatParser(true);
        mParser.execute("");

        return view;
    }


    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }




    @Override
    public void OnTextChanged(String queryText) {

        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.VISIBLE);
        Log.d(TAG, queryText);
        if (mParser != null)
            mParser.cancel(true);
        mParser = new BoatParser();
        mParser.execute(queryText);


        ImageListParser imageListParser = new ImageListParser();
        imageListParser.execute(queryText);
    }

    @Override
    public boolean UsesQueryText() {
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        MainActivity a;
        if (context instanceof MainActivity) {
            a = (MainActivity) context;
            a.registerQueryListener(this);
        }
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
            return images;


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

    private class BoatParser extends AsyncTask<String, String, List<Boat>> {

        private boolean mIsSample;
        public BoatParser(){}
        public BoatParser(boolean isSample){
            mIsSample=isSample;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Boat> doInBackground(String... args) {

            JsonParser jParser = new JsonParser(mIsSample);

            return jParser.getBoatsFromUrl(args[0]);
        }

        @Override
        protected void onPostExecute(List<Boat> boats) {
            boatList = boats;
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

            final BoatArrayAdapter adapter = new BoatArrayAdapter(getContext(), boatList);

            mRecyclerView.setAdapter(adapter);
            mProgressBar.setIndeterminate(false);
            mProgressBar.setVisibility(View.GONE);

        }
    }


}
