package android.com.solutions.nerd.testapp.boat;

import android.com.solutions.nerd.testapp.R;
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
import android.widget.LinearLayout;
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
    /**
     * Created by mookie on 10/30/15.
     * for Nerd.Solutions
     */

    public static  class CustomBoatHolder extends RecyclerView.ViewHolder{

        protected ViewFlipper viewFlipper;
        protected ImageView boatImage;
        protected TextView designerText;
        protected TextView titleText;
        protected TextView rigText;
        protected TextView boatYears;
        protected TextView boatLength;
        protected TextView bal_disp;
        protected LinearLayout bal_dispContainer;
        protected TextView bal_type;
        protected LinearLayout bal_typeContainer;
        protected TextView ballast;
        protected LinearLayout ballastContainer;
        protected TextView beam;
        protected LinearLayout beamContainer;
        protected TextView builder;
        protected LinearLayout builderContainer;
        protected TextView construct;
        protected LinearLayout constructContainer;
        protected TextView designer;
        protected LinearLayout designerContainer;
        protected TextView disp;
        protected LinearLayout dispContainer;
        protected TextView draft_max;
        protected LinearLayout draft_maxContainer;
        protected TextView draft_min;
        protected LinearLayout draft_minContainer;
        protected TextView e;
        protected LinearLayout eContainer;
        protected TextView ey;
        protected LinearLayout eyContainer;
        protected TextView first_built;
        protected LinearLayout first_builtContainer;
        protected TextView fuel;
        protected LinearLayout fuelContainer;
        protected TextView hp;
        protected LinearLayout hpContainer;
        protected TextView hull;
        protected LinearLayout hullContainer;
        protected TextView i;
        protected LinearLayout iContainer;
        protected TextView images;
        protected LinearLayout imagesContainer;
        protected TextView isp;
        protected LinearLayout ispContainer;
        protected TextView j;
        protected LinearLayout jContainer;
        protected TextView last_built;
        protected LinearLayout last_builtContainer;
        protected TextView loa;
        protected LinearLayout loaContainer;
        protected TextView lwl;
        protected LinearLayout lwlContainer;
        protected TextView make;
        protected LinearLayout makeContainer;
        protected TextView mast_height;
        protected LinearLayout mast_heightContainer;
        protected TextView model;
        protected LinearLayout modelContainer;
        protected TextView more;
        protected LinearLayout moreContainer;
        protected TextView num_built;
        protected LinearLayout num_builtContainer;
        protected TextView p;
        protected LinearLayout pContainer;
        protected TextView py;
        protected LinearLayout pyContainer;
        protected TextView rig_type;
        protected LinearLayout rig_typeContainer;
        protected TextView sa_disp;
        protected LinearLayout sa_dispContainer;
        protected TextView sa_fore;
        protected LinearLayout sa_foreContainer;
        protected TextView sa_list;
        protected LinearLayout sa_listContainer;
        protected TextView spl;
        protected LinearLayout splContainer;
        protected TextView title;
        protected TextView total_calc;
        protected LinearLayout total_calcContainer;
        protected TextView type;
        protected LinearLayout typeContainer;
        protected TextView water;
        protected LinearLayout waterContainer;
        protected CardView frontCard;
        protected CardView rearCard;


        protected View itemView;
        public CustomBoatHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.boatLength=(TextView)itemView.findViewById(R.id.loa);
            this.viewFlipper=(ViewFlipper)itemView.findViewById(R.id.viewflipper);
            this.boatYears=(TextView)itemView.findViewById(R.id.yearText);
            this.titleText=(TextView)itemView.findViewById(R.id.title);
            this.boatImage=(ImageView)itemView.findViewById(R.id.boatThumbnail);
            this.rigText=(TextView)itemView.findViewById(R.id.rig_type);
            this.designerText=(TextView)itemView.findViewById(R.id.designer);

            this.frontCard=(CardView) itemView.findViewById(R.id.frontCard);
            this.rearCard=(CardView) itemView.findViewById(R.id.rearCard);

            this.bal_disp=(TextView)itemView.findViewById(R.id.bal_disp);
            this.bal_dispContainer=(LinearLayout)itemView.findViewById(R.id.bal_dispContainer);

            this.bal_type=(TextView)itemView.findViewById(R.id.bal_type);
            this.bal_typeContainer=(LinearLayout)itemView.findViewById(R.id.bal_typeContainer);

            // ballast
            this.ballast=(TextView)itemView.findViewById(R.id.ballast);
            this.ballastContainer=(LinearLayout)itemView.findViewById(R.id.ballastContainer);

            // beam
            this.beam=(TextView)itemView.findViewById(R.id.beam);
            this.beamContainer=(LinearLayout)itemView.findViewById(R.id.beamContainer);

            // builder
            this.builder=(TextView)itemView.findViewById(R.id.builder);
            this.builderContainer=(LinearLayout)itemView.findViewById(R.id.builderContainer);

            // construct
            this.construct=(TextView)itemView.findViewById(R.id.construct);
            this.constructContainer=(LinearLayout)itemView.findViewById(R.id.constructContainer);

            // designer
            this.designer=(TextView)itemView.findViewById(R.id.designer);
            this.designerContainer=(LinearLayout)itemView.findViewById(R.id.designerContainer);


            // disp
            this.disp=(TextView)itemView.findViewById(R.id.disp);
            this.dispContainer=(LinearLayout)itemView.findViewById(R.id.dispContainer);

            // draft_max
            this.draft_max=(TextView)itemView.findViewById(R.id.draft_max);
            this.draft_maxContainer=(LinearLayout)itemView.findViewById(R.id.draft_maxContainer);

            // draft_min
            this.draft_min=(TextView)itemView.findViewById(R.id.draft_min);
            this.draft_minContainer=(LinearLayout)itemView.findViewById(R.id.draft_minContainer);

            // e
            this.e=(TextView)itemView.findViewById(R.id.e);
            this.eContainer=(LinearLayout)itemView.findViewById(R.id.eContainer);

            // ey
            this.ey=(TextView)itemView.findViewById(R.id.ey);
            this.eyContainer=(LinearLayout)itemView.findViewById(R.id.eyContainer);

            // first_built
            this.first_built=(TextView)itemView.findViewById(R.id.first_built);
            this.first_builtContainer=(LinearLayout)itemView.findViewById(R.id.first_builtContainer);

            // fuel
            this.fuel=(TextView)itemView.findViewById(R.id.fuel);
            this.fuelContainer=(LinearLayout)itemView.findViewById(R.id.fuelContainer);

            // hp
            this.hp=(TextView)itemView.findViewById(R.id.hp);
            this.hpContainer=(LinearLayout)itemView.findViewById(R.id.hpContainer);

            // hull
            this.hull=(TextView)itemView.findViewById(R.id.hull);
            this.hullContainer=(LinearLayout)itemView.findViewById(R.id.hullContainer);

            // i
            this.i=(TextView)itemView.findViewById(R.id.i);
            this.iContainer=(LinearLayout)itemView.findViewById(R.id.iContainer);

            // images
            this.images=(TextView)itemView.findViewById(R.id.images);
            this.imagesContainer=(LinearLayout)itemView.findViewById(R.id.imagesContainer);

            // isp
            this.isp=(TextView)itemView.findViewById(R.id.isp);
            this.ispContainer=(LinearLayout)itemView.findViewById(R.id.ispContainer);

            // j
            this.j=(TextView)itemView.findViewById(R.id.j);
            this.jContainer=(LinearLayout)itemView.findViewById(R.id.jContainer);

            // last_built
            this.last_built=(TextView)itemView.findViewById(R.id.last_built);
            this.last_builtContainer=(LinearLayout)itemView.findViewById(R.id.last_builtContainer);

            // loa
            this.loa=(TextView)itemView.findViewById(R.id.loa);
            this.loaContainer=(LinearLayout)itemView.findViewById(R.id.loaContainer);

            // lwl
            this.lwl=(TextView)itemView.findViewById(R.id.lwl);
            this.lwlContainer=(LinearLayout)itemView.findViewById(R.id.lwlContainer);

            // make
            this.make=(TextView)itemView.findViewById(R.id.make);
            this.makeContainer=(LinearLayout)itemView.findViewById(R.id.makeContainer);

            // mast_height
            this.mast_height=(TextView)itemView.findViewById(R.id.mast_height);
            this.mast_heightContainer=(LinearLayout)itemView.findViewById(R.id.mast_heightContainer);

            // model
            this.model=(TextView)itemView.findViewById(R.id.model);
            this.modelContainer=(LinearLayout)itemView.findViewById(R.id.modelContainer);

            // more
            this.more=(TextView)itemView.findViewById(R.id.more);
            this.moreContainer=(LinearLayout)itemView.findViewById(R.id.moreContainer);

            // num_built
            this.num_built=(TextView)itemView.findViewById(R.id.num_built);
            this.num_builtContainer=(LinearLayout)itemView.findViewById(R.id.num_builtContainer);

            // p
            this.p=(TextView)itemView.findViewById(R.id.p);
            this.pContainer=(LinearLayout)itemView.findViewById(R.id.pContainer);

            // py
            this.py=(TextView)itemView.findViewById(R.id.py);
            this.pyContainer=(LinearLayout)itemView.findViewById(R.id.pyContainer);

            // rig_type
            this.rig_type=(TextView)itemView.findViewById(R.id.rig_type);
            this.rig_typeContainer=(LinearLayout)itemView.findViewById(R.id.rig_typeContainer);


            // sa_disp
            this.sa_disp=(TextView)itemView.findViewById(R.id.sa_disp);
            this.sa_dispContainer=(LinearLayout)itemView.findViewById(R.id.sa_dispContainer);

            // sa_fore
            this.sa_fore=(TextView)itemView.findViewById(R.id.sa_fore);
            this.sa_foreContainer=(LinearLayout)itemView.findViewById(R.id.sa_foreContainer);

            // sa_list
            this.sa_list=(TextView)itemView.findViewById(R.id.sa_list);
            this.sa_listContainer=(LinearLayout)itemView.findViewById(R.id.sa_listContainer);

            // spl
            this.spl=(TextView)itemView.findViewById(R.id.spl);
            this.splContainer=(LinearLayout)itemView.findViewById(R.id.splContainer);

            // title
            this.title=(TextView)itemView.findViewById(R.id.title);
//            this.titleContainer=(LinearLayout)itemView.findViewById(R.id.titleContainer);

            // total_calc
            this.total_calc=(TextView)itemView.findViewById(R.id.total_calc);
            this.total_calcContainer=(LinearLayout)itemView.findViewById(R.id.total_calcContainer);

            // type
            this.type=(TextView)itemView.findViewById(R.id.type);
            this.typeContainer=(LinearLayout)itemView.findViewById(R.id.typeContainer);

            // water
            this.water=(TextView)itemView.findViewById(R.id.water);
            this.waterContainer=(LinearLayout)itemView.findViewById(R.id.waterContainer);
            // Beam
            this.beam=(TextView)itemView.findViewById(R.id.beam);
            this.beamContainer=(LinearLayout)itemView.findViewById(R.id.beamContainer);




//            cardView.setPreventCornerOverlap(false);
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private static final String TAG = BoatArrayAdapter.class.getSimpleName();




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



                if (customBoatHolder.viewFlipper.getDisplayedChild()==0){
                    customBoatHolder.rearCard.setVisibility(View.VISIBLE);
                    // Next screen comes in from left.
                    customBoatHolder.viewFlipper.setInAnimation(context, R.anim.slide_in_from_right);
                    // Current screen goes out from right.
                    customBoatHolder.viewFlipper.setOutAnimation(context, R.anim.slide_out_to_left);

                    // Display next screen.
                    customBoatHolder.viewFlipper.showNext();
                }else{
                    customBoatHolder.rearCard.setVisibility(View.GONE);

                    // Next screen comes in from right.
                    customBoatHolder.viewFlipper.setInAnimation(context, R.anim.slide_in_from_left);
                    // Current screen goes out from left.
                    customBoatHolder.viewFlipper.setOutAnimation(context, R.anim.slide_out_to_right);

                    // Display previous screen.
                    customBoatHolder.viewFlipper.showPrevious();

                }
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
        String boatYears=boatItem.getFirst_built();

        if (!boatYears.isEmpty())
            years+=boatYears;

        String lastYear=boatItem.getLast_built();
        if (lastYear.length()>0)
            years+=" / "+lastYear;
        customBoatHolder.boatYears.setText(years);




        //bal_disp
        String bal_disp = boatItem.getBal_disp();
        customBoatHolder.bal_disp.setText(bal_disp);
        customBoatHolder.bal_dispContainer.setVisibility(bal_disp.isEmpty()?View.GONE:View.VISIBLE);

        //bal_type
        String bal_type = boatItem.getBal_type();
        customBoatHolder.bal_type.setText(bal_type);
        customBoatHolder.bal_typeContainer.setVisibility(bal_type.isEmpty()?View.GONE:View.VISIBLE);

        //ballast
        String ballast = boatItem.getBallast();
        customBoatHolder.ballast.setText(ballast);
        customBoatHolder.ballastContainer.setVisibility(ballast.isEmpty()?View.GONE:View.VISIBLE);

        //beam
        String beam = boatItem.getBeam();
        customBoatHolder.beam.setText(beam);
        customBoatHolder.beamContainer.setVisibility(beam.isEmpty()?View.GONE:View.VISIBLE);

        //builder
        String builder = boatItem.getBuilder();
        customBoatHolder.builder.setText(builder);
        customBoatHolder.builderContainer.setVisibility(builder.isEmpty()?View.GONE:View.VISIBLE);

        //construct
        String construct = boatItem.getConstruct();
        customBoatHolder.construct.setText(construct);
        customBoatHolder.constructContainer.setVisibility(construct.isEmpty()?View.GONE:View.VISIBLE);

        //designer
        String designer = boatItem.getDesigner();
        customBoatHolder.designer.setText(designer);
        customBoatHolder.designerContainer.setVisibility(designer.isEmpty()?View.GONE:View.VISIBLE);

        //disp
        String disp = boatItem.getDisp();
        customBoatHolder.disp.setText(disp);
        customBoatHolder.dispContainer.setVisibility(disp.isEmpty()?View.GONE:View.VISIBLE);

        //draft_max
        String draft_max = boatItem.getDraft_max();
        customBoatHolder.draft_max.setText(draft_max);
        customBoatHolder.draft_maxContainer.setVisibility(draft_max.isEmpty()?View.GONE:View.VISIBLE);

        //draft_min
        String draft_min = boatItem.getDraft_min();
        customBoatHolder.draft_min.setText(draft_min);
        customBoatHolder.draft_minContainer.setVisibility(draft_min.isEmpty()?View.GONE:View.VISIBLE);

        //e
        String e = boatItem.getE();
        customBoatHolder.e.setText(e);
        customBoatHolder.eContainer.setVisibility(e.isEmpty()?View.GONE:View.VISIBLE);

        //ey
        String ey = boatItem.getEy();
        customBoatHolder.ey.setText(ey);
        customBoatHolder.eyContainer.setVisibility(ey.isEmpty()?View.GONE:View.VISIBLE);

        //first_built
        String first_built = boatItem.getFirst_built();
        customBoatHolder.first_built.setText(first_built);
        customBoatHolder.first_builtContainer.setVisibility(first_built.isEmpty()?View.GONE:View.VISIBLE);

        //fuel
        String fuel = boatItem.getFuel();
        customBoatHolder.fuel.setText(fuel);
        customBoatHolder.fuelContainer.setVisibility(fuel.isEmpty()?View.GONE:View.VISIBLE);

        //hp
        String hp = boatItem.getHp();
        customBoatHolder.hp.setText(hp);
        customBoatHolder.hpContainer.setVisibility(hp.isEmpty()?View.GONE:View.VISIBLE);

        //hull
        String hull = boatItem.getHull();
        customBoatHolder.hull.setText(hull);
        customBoatHolder.hullContainer.setVisibility(hull.isEmpty()?View.GONE:View.VISIBLE);

        //i
        String _i = boatItem.getI();
        customBoatHolder.i.setText(_i);
        customBoatHolder.iContainer.setVisibility(_i.isEmpty()?View.GONE:View.VISIBLE);



        //isp
        String isp = boatItem.getIsp();
        customBoatHolder.isp.setText(isp);
        customBoatHolder.ispContainer.setVisibility(isp.isEmpty()?View.GONE:View.VISIBLE);

        //j
        String j = boatItem.getJ();
        customBoatHolder.j.setText(j);
        customBoatHolder.jContainer.setVisibility(j.isEmpty()?View.GONE:View.VISIBLE);

        //last_built
        String last_built = boatItem.getLast_built();
        customBoatHolder.last_built.setText(last_built);
        customBoatHolder.last_builtContainer.setVisibility(last_built.isEmpty()?View.GONE:View.VISIBLE);

        //loa
        String loa = boatItem.getLoa();
        customBoatHolder.loa.setText(loa);
        customBoatHolder.loaContainer.setVisibility(loa.isEmpty()?View.GONE:View.VISIBLE);

        //lwl
        String lwl = boatItem.getLwl();
        customBoatHolder.lwl.setText(lwl);
        customBoatHolder.lwlContainer.setVisibility(lwl.isEmpty()?View.GONE:View.VISIBLE);

        //make
        String make = boatItem.getMake();
        customBoatHolder.make.setText(make);
        customBoatHolder.makeContainer.setVisibility(make.isEmpty()?View.GONE:View.VISIBLE);

        //mast_height
        String mast_height = boatItem.getMast_height();
        customBoatHolder.mast_height.setText(mast_height);
        customBoatHolder.mast_heightContainer.setVisibility(mast_height.isEmpty()?View.GONE:View.VISIBLE);

//model
        String model = boatItem.getModel();
        customBoatHolder.model.setText(model);
        customBoatHolder.modelContainer.setVisibility(model.isEmpty()?View.GONE:View.VISIBLE);

        //more
        String more = boatItem.getMore();
        customBoatHolder.more.setText(more);
        customBoatHolder.moreContainer.setVisibility(more.isEmpty()?View.GONE:View.VISIBLE);

        //num_built
        String num_built = boatItem.getNum_built();
        customBoatHolder.num_built.setText(num_built);
        customBoatHolder.num_builtContainer.setVisibility(num_built.isEmpty()?View.GONE:View.VISIBLE);

        //p
        String p = boatItem.getP();
        customBoatHolder.p.setText(p);
        customBoatHolder.pContainer.setVisibility(p.isEmpty()?View.GONE:View.VISIBLE);

        //py
        String py = boatItem.getPy();
        customBoatHolder.py.setText(py);
        customBoatHolder.pyContainer.setVisibility(py.isEmpty()?View.GONE:View.VISIBLE);

        //rig_type
        String rig_type = boatItem.getRig_type();
        customBoatHolder.rig_type.setText(rig_type);
        customBoatHolder.rig_typeContainer.setVisibility(rig_type.isEmpty()?View.GONE:View.VISIBLE);

        //sa_disp
        String sa_disp = boatItem.getSa_disp();
        customBoatHolder.sa_disp.setText(sa_disp);
        customBoatHolder.sa_dispContainer.setVisibility(sa_disp.isEmpty()?View.GONE:View.VISIBLE);

        //sa_fore
        String sa_fore = boatItem.getSa_fore();
        customBoatHolder.sa_fore.setText(sa_fore);
        customBoatHolder.sa_foreContainer.setVisibility(sa_fore.isEmpty()?View.GONE:View.VISIBLE);

        //sa_list
        String sa_list = boatItem.getSa_list();
        customBoatHolder.sa_list.setText(sa_list);
        customBoatHolder.sa_listContainer.setVisibility(sa_list.isEmpty()?View.GONE:View.VISIBLE);

        //spl
        String spl = boatItem.getSpl();
        customBoatHolder.spl.setText(spl);
        customBoatHolder.splContainer.setVisibility(spl.isEmpty()?View.GONE:View.VISIBLE);

//title
        String title = boatItem.getTitle();
        customBoatHolder.title.setText(title);
    //    customBoatHolder.titleContainer.setVisibility(title.isEmpty()?View.GONE:View.VISIBLE);

//total_calc
        String total_calc = boatItem.getTotal_calc();
        customBoatHolder.total_calc.setText(total_calc);
        customBoatHolder.total_calcContainer.setVisibility(total_calc.isEmpty()?View.GONE:View.VISIBLE);

        //type
        String type = boatItem.getType();
        customBoatHolder.type.setText(type);
        customBoatHolder.typeContainer.setVisibility(type.isEmpty()?View.GONE:View.VISIBLE);

//water
        String water = boatItem.getWater();
        customBoatHolder.water.setText(water);
        customBoatHolder.waterContainer.setVisibility(water.isEmpty()?View.GONE:View.VISIBLE);


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