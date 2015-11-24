package android.com.solutions.nerd.testapp.boat;

import android.com.solutions.nerd.testapp.Global;
import android.com.solutions.nerd.testapp.R;
import android.com.solutions.nerd.testapp.helpers.PicassoImage;
import android.com.solutions.nerd.testapp.utils.LogUtils;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import it.sephiroth.android.library.widget.ExpandableHListView;

/**
 * Created by mookie on 10/29/15.
 * for Nerd.Solutions
 */
public class BoatArrayAdapter extends RecyclerView.Adapter<BoatArrayAdapter.CustomBoatHolder> {
    private static final String TAG = LogUtils.getLogTag(BoatArrayAdapter.class);
    protected static CustomBoatHolder mSelectedBoat;
    protected static int width = 0;
    private static float initialX;
    private final Context mContext;
    private List<Boat> boatList;
    private int lastPosition = -1;
    private int mPosition;
    public BoatArrayAdapter(Context context, List<Boat> boatList) {
        this.boatList = boatList;
        mContext = context;

    }

    @SuppressWarnings("unused")
    public int getPosition(){
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public CustomBoatHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.boat_card, null);


        // Get Window width
        WindowManager wm=(WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        width=metrics.widthPixels;

        final CustomBoatHolder mHolder =new CustomBoatHolder(view);
        mHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(mHolder.getAdapterPosition());
                return false;
            }
        });

        return mHolder;
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public void onBindViewHolder(final CustomBoatHolder customBoatHolder, int i) {

        Boat boatItem = boatList.get(i);
        
        final int vis_state = View.VISIBLE;

        setAnimation(customBoatHolder.itemView, i);


        String years = "Year(s)";

        // Get boat Years
        String boatYears = boatItem.getFirst_built();

        if (!boatYears.isEmpty())
            years += boatYears;

        String lastYear = boatItem.getLast_built();
        if (lastYear.length() > 0)
            years += " / " + lastYear;
        customBoatHolder.boatYears.setText(years);


        String img = boatItem.getImage(0);

        if (img != null && !img.isEmpty()) {
            String urlString = Global.api_image_path+img+".jpg";
            Log.i(TAG,"image is "+urlString);
            customBoatHolder.boatImage.setImageUrl(img);
        }


        String title = boatItem.getTitle();
        customBoatHolder.titleText.setText(title);


            //bal_disp
            String bal_disp = boatItem.getBal_disp();
            customBoatHolder.bal_disp.setText(bal_disp);
            customBoatHolder.bal_dispContainer.setVisibility(bal_disp.isEmpty() ? vis_state : View.VISIBLE);

            //bal_type
            String bal_type = boatItem.getBal_type();
            customBoatHolder.bal_type.setText(bal_type);
            customBoatHolder.bal_typeContainer.setVisibility(bal_type.isEmpty() ? vis_state : View.VISIBLE);

            //ballast
            String ballast = boatItem.getBallast();
            customBoatHolder.ballast.setText(ballast);
            customBoatHolder.ballastContainer.setVisibility(ballast.isEmpty() ? vis_state : View.VISIBLE);

            //beam
            String beam = boatItem.getBeam();
            customBoatHolder.beam.setText(beam);
            customBoatHolder.beamContainer.setVisibility(beam.isEmpty() ? vis_state : View.VISIBLE);

            //builder
            String builder = boatItem.getBuilder();
            customBoatHolder.builder.setText(builder);
            customBoatHolder.builderContainer.setVisibility(builder.isEmpty() ? vis_state : View.VISIBLE);

            //construct
            String construct = boatItem.getConstruct();
            customBoatHolder.construct.setText(construct);
            customBoatHolder.constructContainer.setVisibility(construct.isEmpty() ? vis_state : View.VISIBLE);

            //designer
            String designer = boatItem.getDesigner();
            customBoatHolder.designer.setText(designer);

            //disp
//            String disp = boatItem.getDisp();
//            customBoatHolder.disp.setText(disp);


            //draft_max
            String draft_max = boatItem.getDraft_max();
            customBoatHolder.draft_max.setText(draft_max);
            customBoatHolder.draft_maxContainer.setVisibility(draft_max.isEmpty() ? vis_state : View.VISIBLE);

            //draft_min
            String draft_min = boatItem.getDraft_min();
            customBoatHolder.draft_min.setText(draft_min);
            customBoatHolder.draft_minContainer.setVisibility(draft_min.isEmpty() ? vis_state : View.VISIBLE);

            //e
            String e = boatItem.getE();
            customBoatHolder.e.setText(e);
            customBoatHolder.eContainer.setVisibility(e.isEmpty() ? vis_state : View.VISIBLE);

            //ey
            String ey = boatItem.getEy();
            customBoatHolder.ey.setText(ey);
            customBoatHolder.eyContainer.setVisibility(ey.isEmpty() ? vis_state : View.VISIBLE);

            //first_built
            String first_built = boatItem.getFirst_built();
            customBoatHolder.first_built.setText(first_built);
            customBoatHolder.first_builtContainer.setVisibility(first_built.isEmpty() ? vis_state : View.VISIBLE);

            //fuel
            String fuel = boatItem.getFuel();
            customBoatHolder.fuel.setText(fuel);
            customBoatHolder.fuelContainer.setVisibility(fuel.isEmpty() ? vis_state : View.VISIBLE);

            //hp
            String hp = boatItem.getHp();
            customBoatHolder.hp.setText(hp);
            customBoatHolder.hpContainer.setVisibility(hp.isEmpty() ? vis_state : View.VISIBLE);

            //hull
            String hull = boatItem.getHull();
            customBoatHolder.hull.setText(hull);
            customBoatHolder.hullContainer.setVisibility(hull.isEmpty() ? View.GONE : View.VISIBLE);

            //i
            String _i = boatItem.getI();
            customBoatHolder.i.setText(_i);
            customBoatHolder.iContainer.setVisibility(_i.isEmpty() ? vis_state : View.VISIBLE);


            //isp
            String isp = boatItem.getIsp();
            customBoatHolder.isp.setText(isp);
            customBoatHolder.ispContainer.setVisibility(isp.isEmpty() ? vis_state : View.VISIBLE);

            //j
            String j = boatItem.getJ();
            customBoatHolder.j.setText(j);
            customBoatHolder.jContainer.setVisibility(j.isEmpty() ? vis_state : View.VISIBLE);

            //last_built
            String last_built = boatItem.getLast_built();
            customBoatHolder.last_built.setText(last_built);
            customBoatHolder.last_builtContainer.setVisibility(last_built.isEmpty() ? vis_state : View.VISIBLE);

            //loa
            String loa = boatItem.getLoa();
            customBoatHolder.loa.setText(loa);
            customBoatHolder.loaContainer.setVisibility(loa.isEmpty() ? vis_state : View.VISIBLE);

            //lwl
            String lwl = boatItem.getLwl();
            customBoatHolder.lwl.setText(lwl);
            customBoatHolder.lwlContainer.setVisibility(lwl.isEmpty() ? vis_state : View.VISIBLE);

            //make
            String make = boatItem.getMake();
            customBoatHolder.make.setText(make);
            customBoatHolder.makeContainer.setVisibility(make.isEmpty() ? vis_state : View.VISIBLE);

            //mast_height
            String mast_height = boatItem.getMast_height();
            customBoatHolder.mast_height.setText(mast_height);
            customBoatHolder.mast_heightContainer.setVisibility(mast_height.isEmpty() ? vis_state : View.VISIBLE);

//model
            String model = boatItem.getModel();
            customBoatHolder.model.setText(model);
            customBoatHolder.modelContainer.setVisibility(model.isEmpty() ? vis_state : View.VISIBLE);

            //more
            String more = boatItem.getMore();
            customBoatHolder.more.setText(more);
            customBoatHolder.moreContainer.setVisibility(more.isEmpty() ? vis_state : View.VISIBLE);

            //num_built
            String num_built = boatItem.getNum_built();
            customBoatHolder.num_built.setText(num_built);
            customBoatHolder.num_builtContainer.setVisibility(num_built.isEmpty() ? vis_state : View.VISIBLE);

            //p
            String p = boatItem.getP();
            customBoatHolder.p.setText(p);
            customBoatHolder.pContainer.setVisibility(p.isEmpty() ? vis_state : View.VISIBLE);

            //py
            String py = boatItem.getPy();
            customBoatHolder.py.setText(py);
            customBoatHolder.pyContainer.setVisibility(py.isEmpty() ? vis_state : View.VISIBLE);

            //rig_type
            String rig_type = boatItem.getRig_type();
            customBoatHolder.rig_type.setText(rig_type);

            customBoatHolder.rig_type_label.setText(rig_type.isEmpty() ? "" : "Rig Type:");


            //sa_disp
            String sa_disp = boatItem.getSa_disp();

            customBoatHolder.sa_disp.setText(sa_disp);
            customBoatHolder.sa_disp_label.setText(sa_disp.isEmpty() ? "" : "Sail Area Displacement");


            //sa_fore
            String sa_fore = boatItem.getSa_fore();
            customBoatHolder.sa_fore.setText(sa_fore);
            customBoatHolder.sa_foreContainer.setVisibility(sa_fore.isEmpty() ? vis_state : View.VISIBLE);

            //sa_list
            String sa_list = boatItem.getSa_list();
            customBoatHolder.sa_list.setText(sa_list);
            customBoatHolder.sa_listContainer.setVisibility(sa_list.isEmpty() ? vis_state : View.VISIBLE);

            /* spl */
            String spl = boatItem.getSpl();
            customBoatHolder.spl.setText(spl);
            customBoatHolder.splContainer.setVisibility(spl.isEmpty() ? vis_state : View.VISIBLE);


            /* total_calc */
            String total_calc = boatItem.getTotal_calc();
            customBoatHolder.total_calc.setText(total_calc);
            customBoatHolder.total_calcContainer.setVisibility(total_calc.isEmpty() ? vis_state : View.VISIBLE);

            /* type */
            String type = boatItem.getType();
            customBoatHolder.type.setText(type);
            customBoatHolder.typeContainer.setVisibility(type.isEmpty() ? vis_state : View.VISIBLE);

            /* water */
            String water = boatItem.getWater();
            customBoatHolder.water.setText(water);
            customBoatHolder.waterContainer.setVisibility(water.isEmpty() ? vis_state : View.VISIBLE);



            customBoatHolder.titleText.setText(boatItem.getTitle());
            customBoatHolder.rigText.setText(boatItem.getRig_type());


        customBoatHolder.shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Share Link clicked");
                Snackbar.make(v, "Share Link Clicked", Snackbar.LENGTH_LONG).show();
            }
        });

        customBoatHolder.editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Edit Link clicked");
                Snackbar.make(v, "Edit Link Clicked", Snackbar.LENGTH_SHORT).show();
            }


        });
        customBoatHolder.boatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "GET WebLink");
                Snackbar.make(v, "GET WebLink", Snackbar.LENGTH_SHORT).show();
                String url = "http://www.example.com";
                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setData(Uri.parse(url));
                mContext.startActivity(intent);

                Log.e(TAG, "Boat Link clicked");
                Snackbar.make(v, "Boat Link Clicked", Snackbar.LENGTH_SHORT).show();
            }

        });

        customBoatHolder.plusOneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });
        customBoatHolder.favoriteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Favorite Link clicked");
                Snackbar.make(v, "Favorite Link Clicked", Snackbar.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    public int getItemCount() {
        return (null != boatList ? boatList.size() : 0);
    }

    /**
     * Created by mookie on 10/30/15.
     * for Nerd.Solutions
     */
    public static class CustomBoatHolder extends RecyclerView.ViewHolder
        implements View.OnCreateContextMenuListener, View.OnTouchListener{


        protected ExpandableHListView boatImageList;
        protected PicassoImage boatImage;
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
        protected TextView rig_type_label;
        protected TextView rig_type;

        protected TextView sa_disp;
        protected TextView sa_disp_label;


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
        protected LinearLayout rearCard;
        protected LinearLayout boatFront;


        protected View itemView;

        protected ImageView boatView;
        protected ImageView shareView;
        protected ImageView editView;
        protected ImageView favoriteView;
        protected ImageView plusOneView;

        public CustomBoatHolder(View itemView) {

            super(itemView);
            this.itemView = itemView;
            itemView.setOnCreateContextMenuListener(this);


            boatView = (ImageView) itemView.findViewById(R.id.boatLink);
            plusOneView = (ImageView) itemView.findViewById(R.id.plusOneLink);
            favoriteView = (ImageView) itemView.findViewById(R.id.favoriteLink);
            editView = (ImageView) itemView.findViewById(R.id.editLink);
            shareView = (ImageView) itemView.findViewById(R.id.shareLink);


            this.boatImageList=(ExpandableHListView)itemView.findViewById(R.id.list);

            this.boatYears = (TextView) itemView.findViewById(R.id.yearText);
            this.titleText = (TextView) itemView.findViewById(R.id.title);
            this.boatImage = (PicassoImage) itemView.findViewById(R.id.boatThumbnail);
            this.rigText = (TextView) itemView.findViewById(R.id.rig_type);
            this.rig_type_label=(TextView)itemView.findViewById(R.id.rigLabel);
            this.designer = (TextView) itemView.findViewById(R.id.designer);



            this.frontCard = (CardView) itemView.findViewById(R.id.frontCard);
            this.rearCard=(LinearLayout)itemView.findViewById(R.id.rearCard);



            this.boatFront=(LinearLayout)itemView.findViewById(R.id.boatFront);
            boatImage.setOnTouchListener(this);
            itemView.setOnTouchListener(this);

            itemView.setMinimumWidth(width);
            int footer_width=width/5 ;
            int w=footer_width/5-(boatView.getWidth()/2);
/*            boatView.setPadding(w,8,8,w);
            plusOneView.setPadding(w,8,8,footer_width);
            favoriteView.setPadding(w,8,8,w);
            editView.setPadding(w,8,8,w);
            shareView.setPadding(w,8,8,w);*/




            this.boatLength=(TextView)itemView.findViewById(R.id.loa);
            this.loa = (TextView) itemView.findViewById(R.id.loa);

            this.bal_disp=(TextView)itemView.findViewById(R.id.bal_disp);
            this.bal_dispContainer=(LinearLayout)itemView.findViewById(R.id.bal_dispContainer);

            this.bal_type=(TextView)itemView.findViewById(R.id.bal_type);
            this.bal_typeContainer=(LinearLayout)itemView.findViewById(R.id.bal_typeContainer);

            /* ballast */
            this.ballast=(TextView)itemView.findViewById(R.id.ballast);
            this.ballastContainer=(LinearLayout)itemView.findViewById(R.id.ballastContainer);

            /* beam */
            this.beam=(TextView)itemView.findViewById(R.id.beam);
            this.beamContainer=(LinearLayout)itemView.findViewById(R.id.beamContainer);


            /* builder */
            this.builder=(TextView)itemView.findViewById(R.id.builder);
            this.builderContainer=(LinearLayout)itemView.findViewById(R.id.builderContainer);

            /* construct */
            this.construct=(TextView)itemView.findViewById(R.id.construct);
            this.constructContainer=(LinearLayout)itemView.findViewById(R.id.constructContainer);




                /* designer */
                this.designerContainer = (LinearLayout) itemView.findViewById(R.id.designerContainer);




                /* draft_max */
                this.draft_max = (TextView) itemView.findViewById(R.id.draft_max);
                this.draft_maxContainer = (LinearLayout) itemView.findViewById(R.id.draft_maxContainer);

                /* draft_min */
                this.draft_min = (TextView) itemView.findViewById(R.id.draft_min);
                this.draft_minContainer = (LinearLayout) itemView.findViewById(R.id.draft_minContainer);

                /* e */
                this.e = (TextView) itemView.findViewById(R.id.e);
                this.eContainer = (LinearLayout) itemView.findViewById(R.id.eContainer);

                /* ey */
                this.ey = (TextView) itemView.findViewById(R.id.ey);
                this.eyContainer = (LinearLayout) itemView.findViewById(R.id.eyContainer);

                /* first_built */
                this.first_built = (TextView) itemView.findViewById(R.id.first_built);
                this.first_builtContainer = (LinearLayout) itemView.findViewById(R.id.first_builtContainer);

                /* fuel */
                this.fuel = (TextView) itemView.findViewById(R.id.fuel);
                this.fuelContainer = (LinearLayout) itemView.findViewById(R.id.fuelContainer);

                /* hp */
                this.hp = (TextView) itemView.findViewById(R.id.hp);
                this.hpContainer = (LinearLayout) itemView.findViewById(R.id.hpContainer);

                /* hull */
                this.hull = (TextView) itemView.findViewById(R.id.hull);
                this.hullContainer = (LinearLayout) itemView.findViewById(R.id.hullContainer);

                /* i */
                this.i = (TextView) itemView.findViewById(R.id.i);
                this.iContainer = (LinearLayout) itemView.findViewById(R.id.iContainer);

                /* images */
                this.images = (TextView) itemView.findViewById(R.id.images);
                this.imagesContainer = (LinearLayout) itemView.findViewById(R.id.imagesContainer);

                /* isp */
                this.isp = (TextView) itemView.findViewById(R.id.isp);
                this.ispContainer = (LinearLayout) itemView.findViewById(R.id.ispContainer);

                /* j */
                this.j = (TextView) itemView.findViewById(R.id.j);
                this.jContainer = (LinearLayout) itemView.findViewById(R.id.jContainer);

                /* last_built */
                this.last_built = (TextView) itemView.findViewById(R.id.last_built);
                this.last_builtContainer = (LinearLayout) itemView.findViewById(R.id.last_builtContainer);

                /* loa */

                this.loaContainer = (LinearLayout) itemView.findViewById(R.id.loaContainer);

                /* lwl */
                this.lwl = (TextView) itemView.findViewById(R.id.lwl);
                this.lwlContainer = (LinearLayout) itemView.findViewById(R.id.lwlContainer);

                /* make */
                this.make = (TextView) itemView.findViewById(R.id.make);
                this.makeContainer = (LinearLayout) itemView.findViewById(R.id.makeContainer);

                /* mast_height */
                this.mast_height = (TextView) itemView.findViewById(R.id.mast_height);
                this.mast_heightContainer = (LinearLayout) itemView.findViewById(R.id.mast_heightContainer);

                /* model */
                this.model = (TextView) itemView.findViewById(R.id.model);
                this.modelContainer = (LinearLayout) itemView.findViewById(R.id.modelContainer);

                /* more */
                this.more = (TextView) itemView.findViewById(R.id.more);
                this.moreContainer = (LinearLayout) itemView.findViewById(R.id.moreContainer);

                /* num_built */
                this.num_built = (TextView) itemView.findViewById(R.id.num_built);
                this.num_builtContainer = (LinearLayout) itemView.findViewById(R.id.num_builtContainer);

                /* p */
                this.p = (TextView) itemView.findViewById(R.id.p);
                this.pContainer = (LinearLayout) itemView.findViewById(R.id.pContainer);

                /* py */
                this.py = (TextView) itemView.findViewById(R.id.py);
                this.pyContainer = (LinearLayout) itemView.findViewById(R.id.pyContainer);

                /* rig_type */
                this.rig_type = (TextView) itemView.findViewById(R.id.rig_type);



                /* sa_disp */
                this.sa_disp = (TextView) itemView.findViewById(R.id.sa_disp);
                this.sa_disp_label = (TextView) itemView.findViewById(R.id.saDispLabel);

                /* sa_fore */
                this.sa_fore = (TextView) itemView.findViewById(R.id.sa_fore);
                this.sa_foreContainer = (LinearLayout) itemView.findViewById(R.id.sa_foreContainer);

                /* sa_list */
                this.sa_list = (TextView) itemView.findViewById(R.id.sa_list);
                this.sa_listContainer = (LinearLayout) itemView.findViewById(R.id.sa_listContainer);

                /* spl */
                this.spl = (TextView) itemView.findViewById(R.id.spl);
                this.splContainer = (LinearLayout) itemView.findViewById(R.id.splContainer);


                /* total_calc */
                this.total_calc = (TextView) itemView.findViewById(R.id.total_calc);
                this.total_calcContainer = (LinearLayout) itemView.findViewById(R.id.total_calcContainer);

                /* type */
                this.type = (TextView) itemView.findViewById(R.id.type);
                this.typeContainer = (LinearLayout) itemView.findViewById(R.id.typeContainer);

                /* water */
                this.water = (TextView) itemView.findViewById(R.id.water);
                this.waterContainer = (LinearLayout) itemView.findViewById(R.id.waterContainer);

                /* Beam */
                this.beam = (TextView) itemView.findViewById(R.id.beam);
                this.beamContainer = (LinearLayout) itemView.findViewById(R.id.beamContainer);


//            cardView.setPreventCornerOverlap(false);
        }
        /**
         * Called when a touch event is dispatched to a view. This allows listeners to
         * get a chance to respond before the target view.
         *
         * @param v     The view the touch event has been dispatched to.
         * @param event The MotionEvent object containing full information about
         *              the event.
         * @return True if the listener has consumed the event, false otherwise.
         */
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int index = event.getActionIndex();
            int action = event.getActionMasked();
            int pointerId = event.getPointerId(index);

            mSelectedBoat = this;
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    initialX=event.getX();
                    return true;
                case MotionEvent.ACTION_UP:
                    LinearLayout more=(LinearLayout)itemView.findViewById(R.id.main_content);

                    more.setVisibility(more.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);



                    /*
                    final float finalX=event.getX();

                        if (initialX>finalX) {
                            if (viewFlipper.getDisplayedChild() == 1)
                                break;

                            viewFlipper.setInAnimation(v.getContext(),R.anim.slide_in_from_right);
                            viewFlipper.setOutAnimation(v.getContext(),R.anim.slide_out_to_left);

                            viewFlipper.showNext();


                        }else {
                            if (viewFlipper.getDisplayedChild() == 0)
                                break;

                            viewFlipper.setInAnimation(v.getContext(), R.anim.slide_in_from_left);
                            viewFlipper.setOutAnimation(v.getContext(), R.anim.slide_out_to_right);



                            viewFlipper.showPrevious();
                        }
                        */

                        break;
             }
            return false;
        }

        /**
         * Called when the context menu for this view is being built. It is not
         * safe to hold onto the menu after this method returns.
         *
         * @param menu     The context menu that is being built
         * @param v        The view for which the context menu is being built
         * @param menuInfo Extra information about the item for which the
         *                 context menu should be shown. This information will vary
         */
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select the action");
            menu.add(0,v.getId(),0,"Call");
            menu.add(0,v.getId(),0,"Sms");
        }



    }


}
