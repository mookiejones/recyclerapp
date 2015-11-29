package android.com.solutions.nerd.testapp.map;

import android.com.solutions.nerd.testapp.R;
import android.com.solutions.nerd.testapp.db.ShipDatabase;
import android.com.solutions.nerd.testapp.model.Ship;
import android.com.solutions.nerd.testapp.utils.LogUtils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLngBounds;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.List;

/**
 * Created by cberman on 11/24/2015.
 */
public class ShipAdapter extends ArrayAdapter<Ship> {
    public static final String TAG= LogUtils.getLogTag(ShipAdapter.class);
    private final Context context;
    private Ship[] values;

    private LatLngBounds mBounds;

    public ShipAdapter(Context context, Ship[] values) {
        super(context, -1, values);
        this.context = context;
        this.values=values;
    }

    public void setBounds(LatLngBounds bounds){
        mBounds=bounds;

        Log.d(TAG, "Need to remove ships not in bounds");
        List<Ship> ships=ShipDatabase.getShips(bounds);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Ship ship = values[position];

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        Bitmap originalImage = ship.getImage();

        if (originalImage!=null) {
            final ImageView img = (ImageView) rowView.findViewById(R.id.boatListImage);

            Bitmap bmp =Bitmap.createBitmap(originalImage);
            if (originalImage.isRecycled()){
                Log.d(TAG,"Image is recycled");

            }
            if (bmp.isRecycled()){
                Log.d(TAG,"Image is recycled");
            }
            img.setImageBitmap(bmp);
        }else{
            Log.d(TAG, "Ship Image is null");
        }


        TextView textView = (TextView) rowView.findViewById(R.id.lblListItem);

        textView.setText(ship.getName());
        // change the icon for Windows and iPhone


        return rowView;
    }
}
