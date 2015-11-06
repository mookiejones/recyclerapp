package android.com.solutions.nerd.testapp.boat;

import android.animation.AnimatorSet;
import android.com.solutions.nerd.testapp.R;
import android.com.solutions.nerd.testapp.model.Boat;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

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

        protected ViewFlipper viewFlipper;
        protected ImageView boatImage;
        protected TextView designerText;
        protected TextView titleText;
        protected TextView rigText;
        protected TextView boatYears;

        protected View itemView;
        public CustomBoatHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.viewFlipper=(ViewFlipper)itemView.findViewById(R.id.viewflipper);
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



    private Context context;
    @Override
    public CustomBoatHolder onCreateViewHolder(ViewGroup viewGroup,int position){
        Log.d(TAG,"onCreateViewHolder");


        final Context c = viewGroup.getContext();
        context=c;
        View view = LayoutInflater.from(c).inflate(R.layout.boat_row_layout, null);


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


    private float lastX;
    @Override
    public void onBindViewHolder(final CustomBoatHolder customBoatHolder, int i) {

        Boat boatItem = boatList.get(i);

        customBoatHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {


                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = touchEvent.getX();
                        Log.i(TAG, "Action Down");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i(TAG, "MOVE");
                        break;
                    case MotionEvent.ACTION_UP:


                        float currentX = touchEvent.getX();
                        // Handling left to right screen swap.
                        if (lastX < currentX) {
                            Log.i(TAG, "onTouch");
                            if (customBoatHolder.viewFlipper.getDisplayedChild()==0)
                                break;

                            // Next screen comes in from left.
                            customBoatHolder.viewFlipper.setInAnimation(context, R.anim.slide_in_from_left);
                            // Current screen goes out from right.
                            customBoatHolder.viewFlipper.setOutAnimation(context, R.anim.slide_out_to_right);

                            // Display next screen.
                            customBoatHolder.viewFlipper.showNext();

                        }
                        Log.i(TAG, "Action UP");
                        Log.i(TAG, "onTouch");

                        if (currentX < lastX) {

                            Log.d(TAG, "Backside");

                            // If there is a child (to the left), kust break.
                            if (customBoatHolder.viewFlipper.getDisplayedChild() == 1)
                                break;

                            // Next screen comes in from right.
                            customBoatHolder.viewFlipper.setInAnimation(context, R.anim.slide_in_from_right);
                            // Current screen goes out from left.
                            customBoatHolder.viewFlipper.setOutAnimation(context, R.anim.slide_out_to_left);

                            // Display previous screen.
                            customBoatHolder.viewFlipper.showPrevious();

                        }
                        break;

                    default:
                        Log.i(TAG, "Action");
                        break;


                }
                return false;
            }
        });

        setAnimation(customBoatHolder.itemView, i);


        String years="Year(s)";

        // Get boat Years
        String boatYears=boatItem.getFirst_build();

        if (!boatYears.isEmpty())
            years+=boatYears;

        String lastYear=boatItem.getLast_build();
        if (lastYear.length()>0)
            years+=" / "+lastYear;
        customBoatHolder.boatYears.setText(years);


        /*

    private String title="";
    private String construct="";
    private String rig_type="";
    private String lwl="";
    private String ballast="";
    private String designer="";
    private String first_build="";
    private String hull="";
    private String beam="";
    private String sa_fore="";
    private String e="";
    private String i="";
    private String j="";
    private String p="";
    private String last_build="";

         */

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
