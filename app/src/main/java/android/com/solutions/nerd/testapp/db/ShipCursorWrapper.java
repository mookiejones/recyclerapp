package android.com.solutions.nerd.testapp.db;

import android.com.solutions.nerd.testapp.model.Ship;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by admin on 11/27/15.
 */
public class ShipCursorWrapper extends CursorWrapper {
    public ShipCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Ship getShip(){

        String id = getString(getColumnIndex(Ship.ID));
        String callsign = getString(getColumnIndex(Ship.CALLSIGN));
        String course = getString(getColumnIndex(Ship.COURSE));
        String destination = getString(getColumnIndex(Ship.DESTINATION));
        String draft = getString(getColumnIndex(Ship.DRAFT));
        String eta = getString(getColumnIndex(Ship.ETA));
        String heading = getString(getColumnIndex(Ship.HEADING));
        String imo = getString(getColumnIndex(Ship.IMO));
        String lat = getString(getColumnIndex(Ship.LAT));
        String length = getString(getColumnIndex(Ship.LENGTH));
        String link = getString(getColumnIndex(Ship.LINK));
        String lng = getString(getColumnIndex(Ship.LNG));
        String mmsi = getString(getColumnIndex(Ship.MMSI));
        String name = getString(getColumnIndex(Ship.NAME));
        String picture = getString(getColumnIndex(Ship.PICTURE));
        String route = getString(getColumnIndex(Ship.ROUTE));
        String speed = getString(getColumnIndex(Ship.SPEED));
        String status = getString(getColumnIndex(Ship.STATUS));
        String type = getString(getColumnIndex(Ship.TYPE));
        String width = getString(getColumnIndex(Ship.WIDTH));
        byte[] img = getBlob(getColumnIndex(Ship.IMAGE));


        Ship ship = new Ship();
        ship.setCallsign(callsign);
        ship.setCourse(course);
        ship.setDestination(destination);
        ship.setDraft(draft);
        ship.setEta(eta);
        ship.setHeading(heading);
  //      ship.setId(id);
        ship.setImo(imo);
        ship.setLat(Double.parseDouble(lat));
        ship.setLng(Double.parseDouble(lng));
        ship.setLength(length);
        ship.setLink(link);
        ship.setMmsi(mmsi);
        ship.setName(name);
        ship.setRoute(route);
        ship.setSpeed(speed);
        ship.setStatus(status);
        ship.setType(type);
        ship.setWidth(width);
        if (img!=null)
            ship.setImage(BitmapFactory.decodeByteArray(img,0,img.length));

        return ship;
    }
}
