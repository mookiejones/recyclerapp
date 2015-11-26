package android.com.solutions.nerd.testapp.map;

import android.com.solutions.nerd.testapp.R;
import android.com.solutions.nerd.testapp.model.Ship;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by cberman on 11/24/2015.
 */
public class ShipAdapter extends ArrayAdapter<Ship> {
    private final Context context;
    private final Ship[] values;

    public ShipAdapter(Context context, Ship[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Ship ship = values[position];
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);

        final ImageView img = (ImageView) rowView.findViewById(R.id.boatListImage);
        Picasso.with(getContext()).load(ship.getPicture()).into(img);
        TextView textView = (TextView) rowView.findViewById(R.id.lblListItem);

        textView.setText(ship.getName());
        // change the icon for Windows and iPhone


        return rowView;
    }
}
