package android.com.solutions.nerd.testapp;

import android.app.ProgressDialog;
import android.com.solutions.nerd.testapp.model.Boat;
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


import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mookie on 10/22/15.
 * for Nerd.Solutions
 */
public class BoatFragment extends Fragment
    implements ITextQueryListener
{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String TAG = BoatFragment.class.getSimpleName();
    private static BoatFragment instance;
    public static BoatFragment getInstance(){
        if (instance==null)
            instance = new BoatFragment();
        return instance;
    }


    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p/>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        return inflater.inflate(R.layout.boat_fragment, null);

    }

    @Override
    public void OnTextChanged(String queryText) {
        Log.d(TAG, queryText);
        new BoatParser().execute(queryText);

    }

    @Override
    public boolean UsesQueryText() {
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        MainActivity a;
        if(context instanceof MainActivity){
            a=(MainActivity)context;
            a.registerQueryListener(this);
        }
    }

    List<Boat> boatList;


    private class BoatParser extends AsyncTask<String ,String,List<Boat>>{
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            /*
            ver = (TextView)findViewById(R.id.vers);
            name = (TextView)findViewById(R.id.name);
            api = (TextView)findViewById(R.id.api);
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            */
        }
        @Override
        protected List<Boat> doInBackground(String... args) {

            JsonParser jParser = new JsonParser();

            // Getting JSON from URL
            // Adding value HashMap key => value

            HashMap<String, String> map = new HashMap<String, String>();
            JSONArray jsonArray = jParser.getJsonArrayFromURL(args[0]);


            List<Boat> json = jParser.getBoatsFromUrl(args[0]);
            return json;
        }

        @Override
        protected void onPostExecute(List<Boat> boats) {
            boatList=boats;

            View view = getView();
           mRecyclerView = (RecyclerView)view.findViewById(R.id.list);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);


            // specify an adapter (see also next example)
//            mAdapter = new MyAdapter(myDataset);
            mRecyclerView.setAdapter(mAdapter);

            Boat[] boatArray = boats.toArray(new Boat[boats.size()]);

    //        final BoatArrayAdapter adapter = new BoatArrayAdapter(getContext(),android.R.layout.simple_list_item_1, boatArray);
  //          list.setAdapter(adapter);



            Log.d(TAG,"onPostExecute");
    }
    }


}
