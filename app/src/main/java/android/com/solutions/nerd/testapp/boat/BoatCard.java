package android.com.solutions.nerd.testapp.boat;

import android.com.solutions.nerd.testapp.R;
import android.com.solutions.nerd.testapp.utils.LogUtils;
import android.content.Context;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * Created by mookie on 11/17/15.
 * for Nerd.Solutions
 */
public class BoatCard extends CardView {


    private static final String TAG = LogUtils.getLogTag(BoatCard.class);


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
    protected TextView rig_type_label;
    protected TextView rig_type;
    protected LinearLayout rig_typeContainer;
    protected TextView sa_disp;
    protected TextView sa_disp_label;

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
    protected LinearLayout rearCard;
    protected LinearLayout boatFront;


    protected View itemView;

    public BoatCard(Context context) {
        super(context);
    }

    public BoatCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BoatCard(Context context, AttributeSet attrs) {

        super(context, attrs);

    }

    private float initialX;

    private VelocityTracker mVelocityTracker;

    /**
     * Attaches a view to this view group. Attaching a view assigns this group as the parent,
     * sets the layout parameters and puts the view in the list of children so that
     * it can be retrieved by calling {@link #getChildAt(int)}.
     * <p/>
     * This method is intended to be lightweight and makes no assumptions about whether the
     * parent or child should be redrawn. Proper use of this method will include also making
     * any appropriate {@link #requestLayout()} or {@link #invalidate()} calls.
     * For example, callers can {@link #post(Runnable) post} a {@link Runnable}
     * which performs a {@link #requestLayout()} on the next frame, after all detach/attach
     * calls are finished, causing layout to be run prior to redrawing the view hierarchy.
     * <p/>
     * This method should be called only for views which were detached from their parent.
     *
     * @param child  the child to attach
     * @param index  the index at which the child should be attached
     * @param params the layout parameters of the child
     * @see #removeDetachedView(View, boolean)
     * @see #detachAllViewsFromParent()
     * @see #detachViewFromParent(View)
     * @see #detachViewFromParent(int)
     */
    @Override
    protected void attachViewToParent(View child, int index, ViewGroup.LayoutParams params) {
        super.attachViewToParent(child, index, params);
        viewFlipper.findViewById(R.id.saDispLabel);
    }

    private Boat mBoat;
    public  void setBoat(final Boat boat){
        mBoat=boat;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                initialX=event.getX();
                if (mVelocityTracker==null){
                    // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                    mVelocityTracker = VelocityTracker.obtain();
                }else{
                    mVelocityTracker.clear();
                }
                mVelocityTracker.addMovement(event);
                return true;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ACTION_UP");
                final float finalX=event.getX();
                Log.i(TAG,"Action Up");
                viewFlipper = (ViewFlipper)findViewById(R.id.viewflipper);
                if (initialX>finalX) {
                    if (viewFlipper.getDisplayedChild() == 1)
                        break;

                    viewFlipper.setInAnimation(getContext(), R.anim.slide_in_from_right);
                    viewFlipper.setOutAnimation(getContext(),R.anim.slide_out_to_left);

                    viewFlipper.showNext();


                }else {
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;

                    viewFlipper.setInAnimation(getContext(), R.anim.slide_in_from_left);
                    viewFlipper.setOutAnimation(getContext(), R.anim.slide_out_to_right);


                    viewFlipper.showPrevious();
                }

                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(event);
                // When you want to determine the velocity, call
                // computeCurrentVelocity(). Then call getXVelocity()
                // and getYVelocity() to retrieve the velocity for each pointer ID.
                mVelocityTracker.computeCurrentVelocity(1000);
                // Log velocity of pixels per second
                // Best practice to use VelocityTrackerCompat where possible.
                Log.d(TAG, "X velocity: " +  VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId));
                Log.d(TAG, "Y velocity: " + VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId));
                break;
            case MotionEvent.ACTION_CANCEL:
                // Return a VelocityTracker object back to be re-used by others.
//                    mVelocityTracker.recycle();
                break;
        }
        return false;

    }
}
