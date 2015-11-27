package android.com.solutions.nerd.testapp.db;

import android.com.solutions.nerd.testapp.model.Ship;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 11/27/15.
 */
public class ShipDatabase extends SQLiteOpenHelper {
    public static final int VERSION=2;
    private static final String DATABASE_NAME="ships.db";
    private  static SQLiteDatabase mDb;




    public ShipDatabase(Context context){
        super(context, DATABASE_NAME, null, VERSION);
        mDb = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table ships( _id integer primary key autoincrement, " +
                        Ship.CALLSIGN + " ," +
                        Ship.COURSE + " ," +
                        Ship.DESTINATION + " ," +
                        Ship.DRAFT + " ," +
                        Ship.ETA + " ," +
                        Ship.HEADING + " ," +
                        Ship.IMAGE + " ," +
                        Ship.IMO + " ," +
                        Ship.LAT + " ," +
                        Ship.LENGTH + " ," +
                        Ship.LINK + " ," +
                        Ship.LNG + " ," +
                        Ship.MMSI + " ," +
                        Ship.NAME + " ," +
                        Ship.PICTURE + " ," +
                        Ship.ROUTE + " ," +
                        Ship.SPEED + " ," +
                        Ship.STATUS + " ," +
                        Ship.TYPE + " ," +
                        Ship.WIDTH +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){



    }


    public static void addShip(Ship ship){
        ContentValues values = Ship.getContentValues(ship);
        String mmsi = ship.getMmsi();
        String picture = ship.getPicture();

        ShipCursorWrapper cursor=queryShips(
                Ship.MMSI+" = ?",
                new String[]{ship.getMmsi()}
        );

        try{
            if (cursor.getCount()==0){
                mDb.insert(Ship.TABLENAME, null, values);

            }else{
                updateShip(ship);
            }

        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            cursor.close();
        }

    }

    public static void updateShip(Ship ship){
        ContentValues values = Ship.getContentValues(ship);
        String mmsi = ship.getMmsi();
        mDb.update(Ship.TABLENAME, values, Ship.MMSI + " = ?", new String[]{mmsi});
    }


    public static List<Ship> getShips(LatLngBounds bnd){
        List<Ship> mShips = new ArrayList<>();
        ShipCursorWrapper cursor = ShipDatabase.queryShips(null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Ship ship = cursor.getShip();
                if (bnd.contains(ship.getLocation()))
                    mShips.add(cursor.getShip());
                cursor.moveToNext();
            }
        }finally{
            cursor.close();
        }
        return mShips;
    }


    public static ShipCursorWrapper queryShips(String whereClause,String[] whereArgs){
        Cursor cursor = mDb.query(
                Ship.TABLENAME,
                null, // Columns - null selected all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new ShipCursorWrapper(cursor);
    }


}
