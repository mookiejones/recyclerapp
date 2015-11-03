package android.com.solutions.nerd.testapp;

import android.com.solutions.nerd.testapp.model.Boat;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mookie on 10/29/15.
 * for Nerd.Solutions
 */
public class BoatArrayAdapter extends RecyclerView.Adapter<BoatArrayAdapter.CustomBoatHolder> {
    private List<Boat> boatList;

    public interface OnCardClickedListener{
        void onCardClicked(View view);
    }
    /**
     * Created by mookie on 10/30/15.
     * for Nerd.Solutions
     */

    public static  class CustomBoatHolder extends RecyclerView.ViewHolder{
        private CardView cardView;

        protected ImageView boatImage;
        protected TextView designerText;
        protected TextView titleText;
        protected TextView rigText;
        protected TextView boatYears;

        protected View itemView;
        public CustomBoatHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.cardView=(CardView)itemView.findViewById(R.id.card_view);
            this.boatYears=(TextView)itemView.findViewById(R.id.yearText);
            this.titleText=(TextView)itemView.findViewById(R.id.title);
            this.boatImage=(ImageView)itemView.findViewById(R.id.boatThumbnail);
            this.rigText=(TextView)itemView.findViewById(R.id.rig_type);
            this.designerText=(TextView)itemView.findViewById(R.id.designer);

//            cardView.setPreventCornerOverlap(false);
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private static final String TAG = BoatArrayAdapter.class.getSimpleName();
    private OnCardClickedListener mListener;
    public void setOnCardClickedListener(OnCardClickedListener listener){
        mListener=listener;
    }
    @Override
    public CustomBoatHolder onCreateViewHolder(ViewGroup viewGroup,int position){
        Log.d(TAG,"onCreateViewHolder");

        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.boat_row_layout,null);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null)
                    mListener.onCardClicked(v);
            }
        });
        return new CustomBoatHolder(view);
    }

    private boolean mShowingBack;


    private final Context mContext;
    public BoatArrayAdapter(Context context,List<Boat> boatList){
        this.boatList=boatList;
        mContext=context;
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    private int lastPosition = -1;


    @Override
    public void onBindViewHolder(final CustomBoatHolder customBoatHolder, int i) {

        Boat boatItem = boatList.get(i);

        setAnimation(customBoatHolder.itemView,i);


        // Get boat Years
        String boatYears=boatItem.getFirst_build();


        String lastYear=boatItem.getLast_build();
        if (lastYear.length()>0)
            boatYears+=" / "+lastYear;
        customBoatHolder.boatYears.setText(boatYears);


        customBoatHolder.designerText.setText(boatItem.getDesigner());
        customBoatHolder.titleText.setText(boatItem.getTitle());
        customBoatHolder.rigText.setText(boatItem.getRig_type());



        String img = boatItem.getImage(0);

        if (img!=null&&!img.isEmpty()){
            String urlString = "http://sailsite.meteor.com/"+img+".jpg";
            Picasso.with(mContext).load(urlString)
                    .into(customBoatHolder.boatImage);
        }



    }

    @Override
    public int getItemCount() {
        return (null != boatList ? boatList.size() : 0);
    }



}
