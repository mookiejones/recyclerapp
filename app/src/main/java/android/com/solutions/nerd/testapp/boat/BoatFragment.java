package android.com.solutions.nerd.testapp.boat;

import android.com.solutions.nerd.testapp.ITextQueryListener;
import android.com.solutions.nerd.testapp.JsonParser;
import android.com.solutions.nerd.testapp.R;
import android.com.solutions.nerd.testapp.helpers.OnStartDragListener;
import android.com.solutions.nerd.testapp.helpers.SimpleItemTouchHelperCallback;
import android.com.solutions.nerd.testapp.main.MainActivity;
import android.com.solutions.nerd.testapp.utils.LogUtils;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
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
        implements ITextQueryListener,
        OnStartDragListener
{

    private static final String TAG = LogUtils.getLogTag(BoatFragment.class);
    private static BoatFragment instance;
    BoatParser mParser;
    List<Boat> boatList;
    private CardView mSelectedCard;
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
/*
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);

        // use a linear layout manager

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        OnTextChanged("Irwin");
*/
        return view;
    }


    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        final BoatArrayAdapter adapter = new BoatArrayAdapter(getActivity(), new ArrayList<Boat>());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);


    //    final int spanCount = getResources().getInteger(R.integer.grid_columns);
     //   final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

//        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
 //       mItemTouchHelper = new ItemTouchHelper(callback);
  //      mItemTouchHelper.attachToRecyclerView(recyclerView);

    }
    @Override
    public void OnTextChanged(String queryText) {
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

    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        //mItemTouchHelper.startDrag(viewHolder);
    }

    private class BoatParser extends AsyncTask<String, String, List<Boat>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Boat> doInBackground(String... args) {

            JsonParser jParser = new JsonParser();
            return jParser.getBoatsFromUrl(args[0]);
        }

        @Override
        protected void onPostExecute(List<Boat> boats) {
            boatList = boats;
            BoatArrayAdapter adapter = new BoatArrayAdapter(getContext(), boatList);
            mRecyclerView.setAdapter(adapter);


            Log.d(TAG, "onPostExecute");
        }
    }


}
