package android.com.solutions.nerd.testapp.boat;

import android.annotation.SuppressLint;
import android.com.solutions.nerd.testapp.ITextQueryListener;
import android.com.solutions.nerd.testapp.JsonParser;
import android.com.solutions.nerd.testapp.R;
import android.com.solutions.nerd.testapp.main.MainActivity;
import android.com.solutions.nerd.testapp.model.Boat;
import android.com.solutions.nerd.testapp.utils.LogUtils;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by mookie on 10/22/15.
 * for Nerd.Solutions
 */
public class BoatFragment extends Fragment
    implements ITextQueryListener
{
    private RecyclerView mRecyclerView;
    private BoatArrayAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String TAG = LogUtils.getLogTag(BoatFragment.class);

    private static BoatFragment instance;
    public static BoatFragment getInstance(){
        if (instance==null)
            instance = new BoatFragment();
        return instance;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mAdapter = new BoatArrayAdapter(getContext(),new ArrayList<Boat>());

        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.boat_fragment,null);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.list);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        OnTextChanged("Irwin");

        return view;
    }


    BoatParser mParser;
    @Override
    public void OnTextChanged(String queryText) {
        Log.d(TAG, queryText);
        if(mParser!=null)
            mParser.cancel(true);
        mParser= new BoatParser();
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
        if(context instanceof MainActivity){
            a=(MainActivity)context;
            a.registerQueryListener(this);
        }
    }

    List<Boat> boatList;

    private interface OnBoatParserCompleteListener {
       void OnPostExecute(List<Boat> boats);
    }


    private class BoatParser extends AsyncTask<String ,String,List<Boat>>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected List<Boat> doInBackground(String... args) {

            JsonParser jParser = new JsonParser();
            return jParser.getBoatsFromUrl(args[0]);
        }

        @Override
        protected void onPostExecute(List<Boat> boats) {
            boatList=boats;
            BoatArrayAdapter adapter=new BoatArrayAdapter(getContext(),boatList);
            mRecyclerView.setAdapter(adapter);


            adapter.setOnCardClickedListener(new BoatArrayAdapter.OnCardClickedListener() {
                @Override
                public void onCardClicked(View view) {
                    int right_in  = R.animator.card_flip_right_in;
                 Log.d(TAG, "onCardClicked");
                    Snackbar.make(getView(),"Card Clicked",Snackbar.LENGTH_LONG).show();
/*                    getFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(
                                    right_in, R.animator.card_flip_right_out,
                                    R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                            .replace(R.id.container, new BoatBackFragment())

                            .addToBackStack(null)
                            .commit();*/



                }
            });


            Log.d(TAG, "onPostExecute");
    }
    }

    @SuppressLint("ValidFragment")
    public class BoatFrontFragment extends Fragment{
        @Override
        public View onCreateView(LayoutInflater inflator,ViewGroup container,Bundle savedInstanceState){
            return inflator.inflate(R.layout.boat_row_layout,container,false);
        }
    }

    @SuppressLint("ValidFragment")
    public class BoatBackFragment extends Fragment{
        @Override
        public View onCreateView(LayoutInflater inflator,ViewGroup container,Bundle savedInstanceState){
            return inflator.inflate(R.layout.boat_card_back,container,false);
        }
    }
}
