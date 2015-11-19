package android.com.solutions.nerd.testapp.boat;

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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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

       BoatArrayAdapter adapter = new BoatArrayAdapter(getActivity(), new ArrayList<Boat>());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
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
