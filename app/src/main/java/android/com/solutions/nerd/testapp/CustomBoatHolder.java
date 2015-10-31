package android.com.solutions.nerd.testapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mookie on 10/30/15.
 * for Nerd.Solutions
 */
public class CustomBoatHolder extends RecyclerView.ViewHolder{
    protected ImageView boatImage;
    protected TextView designerText;
    protected TextView titleText;
    protected TextView rigText;
    protected TextView firstYear;
    protected TextView lastYear;

    public CustomBoatHolder(View itemView) {
        super(itemView);
        this.titleText=(TextView)itemView.findViewById(R.id.title);
        this.boatImage=(ImageView)itemView.findViewById(R.id.boatThumbnail);
        this.rigText=(TextView)itemView.findViewById(R.id.rig_type);
        this.firstYear=(TextView)itemView.findViewById(R.id.first_year);
        this.lastYear=(TextView)itemView.findViewById(R.id.last_year);
        this.designerText=(TextView)itemView.findViewById(R.id.designer);
    }
}
