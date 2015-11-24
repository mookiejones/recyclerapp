package android.com.solutions.nerd.testapp.boat;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by mookie on 11/23/15.
 * for Nerd.Solutions
 */
public class BoatCardFooter extends LinearLayout {

    private boolean mFavorite;
    private boolean mBoat;
    private boolean mPlusOne;
    private boolean mShare;

    public boolean getFavorite(){return mFavorite;}
    public boolean getBoat(){return mBoat;}
    public boolean getPlusOne(){return mPlusOne;}
    public boolean getShared(){return mShare;}
    public void setFavorite(boolean favorite){mFavorite=favorite;}
    public void setBoat(boolean boat){mBoat=boat;}
    public void setPlusOne(boolean plusOne){mPlusOne=plusOne;}
    public void setShared(boolean shared){mShare=shared;}




    public BoatCardFooter(Context context) {
        super(context);
    }

    public BoatCardFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BoatCardFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BoatCardFooter(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
