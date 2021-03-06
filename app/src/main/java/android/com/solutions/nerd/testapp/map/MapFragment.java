package android.com.solutions.nerd.testapp.map;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.com.solutions.nerd.testapp.R;
import android.com.solutions.nerd.testapp.db.ShipDatabase;
import android.com.solutions.nerd.testapp.helpers.OnUpdateTimerListener;
import android.com.solutions.nerd.testapp.main.MainActivity;
import android.com.solutions.nerd.testapp.model.Ship;
import android.com.solutions.nerd.testapp.model.TagInfo;
import android.com.solutions.nerd.testapp.utils.PrefUtils;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Config;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import it.sephiroth.android.library.widget.HListView;


public class MapFragment extends Fragment
        implements
        OnUpdateTimerListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnCameraChangeListener,
        SharedPreferences.OnSharedPreferenceChangeListener,
        ShipQueryAsyncTask.OnShipQueryCompleteListener,
        LocationListener,
        GoogleMap.OnInfoWindowClickListener {
    private static final String TAG = MapFragment.class.getSimpleName();
    private static String tileUrl;
    private final static LatLng dearborn = new LatLng(42.3222600f, -83.1763100f);


    private SQLiteDatabase mDatabase;
    protected static List<Ship> mShips = new ArrayList<>();
    private static MapFragment mInstance;
    private static View view;
    private static ShipQueryAsyncTask task;
    private final List<Marker> mTags = new ArrayList<>();
    private final List<MarkerOptions> mMarkers = new ArrayList<>();
    //   private Firebase mFirebaseJourneys;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private final int mDistance = 100;
    SlidingUpPanelLayout mPanelLayout;
    private HListView mShipList;
//    protected static HashMap<String, Ship> mShips = new HashMap<>();
    // ===========================================================
    // Fields
    // ===========================================================
    private SharedPreferences mPrefs;
    private FloatingActionButton mDeleteButtoon;
    private Menu mFloatingMenu;
    private GoogleMap mMap;
    @SuppressWarnings("UnusedDeclaration")
    private PolylineOptions polyLineOptions = new PolylineOptions().geodesic(true);
    @SuppressWarnings("UnusedDeclaration")
    private Dictionary<String, TagInfo> mMarkerDict;
    private Polyline mRouteLine;
    private MarkerOptions selectedMarker;
    private LatLng mSelectedLatLng;
    private LocationManager locationManager;
    private String provider;
    private boolean updating_ships = false;

    public static MapFragment getInstance() {
        if (mInstance == null)
            mInstance = new MapFragment();
        return mInstance;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tileUrl=getResources().getString(R.string.tile_url);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mMap == null) {
            setupMapIfNeeded(savedInstanceState);
        }


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getBaseContext());


        mShipList = (HListView) view.findViewById(R.id.shipList);
        mShipList.setOnItemClickListener(new it.sephiroth.android.library.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> adapterView, View view, int i, long l) {
                Ship ship = mShips.get(i);
                MarkerOptions m = findMarker(ship.getMmsi());
                CameraPosition cp = new CameraPosition(ship.getLocation(), 18, 0, Float.parseFloat(ship.getHeading()));
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));

                if (m != null) {

                    m.alpha(99);
                    if (selectedMarker != null) {
                        selectedMarker.alpha(1);
                        selectedMarker = m;


                    }
                }
            }
        });




         view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                updateMarkers();

                return false;
            }
        });


        if (mMap == null) {
            setupMapIfNeeded(savedInstanceState);
        }
        setHasOptionsMenu(true);

    }

    public MarkerOptions findMarker(String mmsi) {
        MarkerOptions result = null;


        for (MarkerOptions m : mMarkers) {
            String mMmsi = m.getSnippet();

            if (mmsi == mMmsi) {
                result = m;
                break;
            }
        }
        return result;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // retain this fragment
        setRetainInstance(true);

        Context mContext=getContext();
        mDatabase=new ShipDatabase(mContext).getWritableDatabase();
        mShips=new ArrayList<>();

    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p/>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.activity_maps, null);
        } catch (InflateException e) {
            Log.e(TAG, e.getMessage());
        }

        return view;



    }

    private ShipAdapter mShipAdapter;

    @Override
    public void onCameraChange(CameraPosition cameraPosition){
        LatLngBounds bnd = mMap.getProjection().getVisibleRegion().latLngBounds;
        if(mShipAdapter!=null)
            mShipAdapter.setBounds(bnd);

        if (cameraPosition.zoom > 15)
            return;
        if (updating_ships){
//            task.cancel(true);
            updating_ships=false;
        }
        updateMarkers();
        updating_ships=true;

//        task =   new ShipQueryAsyncTask(this);
//        task.execute(bnd);
     //   updateMarkers();
    }

    private void setupMapIfNeeded(final Bundle savedInstanceState) {
        if (mMap != null) {
            return;
        }



        mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();


        mMap.setOnCameraChangeListener(this);
        mMap.setOnMarkerClickListener(this);


        // Disable Buttons

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(final Marker marker) {
                return null;
            }

            private Ship getShip(String mmsi) {
                for (Ship ship : mShips) {
                    if (ship.getMmsi().equals(mmsi))
                        return ship;
                }
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                final View v = getLayoutInflater(savedInstanceState).inflate(R.layout.map_info_window, null);


                String id = marker.getId();
                String snippet = marker.getSnippet();

                final Ship mShip = getShip(snippet);


                ((TextView) v.findViewById(R.id.info_title)).setText(marker.getTitle());
                ((TextView) v.findViewById(R.id.destination)).setText(mShip.getDestination());
                ((TextView) v.findViewById(R.id.draft)).setText(mShip.getDraft());
                ((TextView) v.findViewById(R.id.lat)).setText(String.valueOf(mShip.getLat()));
                ((TextView) v.findViewById(R.id.lng)).setText(String.valueOf(mShip.getLng()));

                ((TextView) v.findViewById(R.id.speed)).setText(mShip.getSpeed());
                ((TextView) v.findViewById(R.id.eta)).setText(mShip.getEta());
                //          ((TextView)v.findViewById(R.id.course)).setText(mShip.getCourse());
                ((TextView) v.findViewById(R.id.heading)).setText(mShip.getHeading());
                ((TextView) v.findViewById(R.id.length)).setText(mShip.getLength());
                final TextView linkView = (TextView) v.findViewById(R.id.link);
                linkView.setText(Html.fromHtml("<a href=\"" + mShip.getLink() + "\">More</a>"));
                linkView.setMovementMethod(LinkMovementMethod.getInstance());

                ((TextView) v.findViewById(R.id.name)).setText(mShip.getName());


                final ImageView img = (ImageView) v.findViewById(R.id.marker_image);
                if (!mShip.getPicture().isEmpty()) {
                    Picasso.with(getContext())
                            .load(mShip.getPicture())
                            .into(img);
                }

                ((TextView) v.findViewById(R.id.status)).setText(mShip.getStatus());
                ((TextView) v.findViewById(R.id.type)).setText(mShip.getType());
                ((TextView) v.findViewById(R.id.width)).setText(mShip.getWidth());
                return v;


            }
        });
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setContentDescription("Content Description");


        mMap.setBuildingsEnabled(false);

        UiSettings settings = mMap.getUiSettings();
        mMap.setIndoorEnabled(false);


        LatLng current = getCurrentLocation();
        if (current != null) {
            CameraPosition cp = new CameraPosition(current, 8, 0, 0);
            CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cp);
            mMap.moveCamera(cu);
        }


        // Setup Long Click
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {
/*                //TODO Remove this when properly implemented
                Firebase newMarker = mFirebaseJourneys.child("markers").push();
                newMarker.setValue(latLng);


                addMarker(latLng);*/
            }
        });

        LatLngBounds bnd = mMap.getProjection().getVisibleRegion().latLngBounds;
        new ShipQueryAsyncTask(this).execute(bnd);

    }

    private void updatePath() {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int id = Integer.parseInt(marker.getId().substring(1));
        selectedMarker = mMarkers.get(id);


        if (selectedMarker != null) {
//                    removeButton.setVisibility(View.VISIBLE);
        }
        mSelectedLatLng = selectedMarker.getPosition();
        if (marker.isInfoWindowShown())
            marker.hideInfoWindow();
        else
            marker.showInfoWindow();
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "onSharedPreferenceChanged");
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "onInfoWindowClick");
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG,"OnStatusChanged provider:"+provider+" status:"+String.valueOf(status));
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG,"onProviderEnabled provider:"+provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG,"onProviderDisabled provider:"+provider);
    }

    private LatLng getCurrentLocation() {
        LatLng result = null;

        if (!Config.DEBUG)
            return dearborn;



        LocationManager locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // check if enabled and if not send to the GSP settings
        // Better Solution would be to display a dialog and suggesting to
        // go to settings

        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        } else {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                String[] permissions = new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                };

                ActivityCompat.requestPermissions(getActivity(), permissions, REQUEST_CODE_ASK_PERMISSIONS);
                return null;
            }
            Location location = locationManager.getLastKnownLocation(provider);

            if (location == null)
                return result;

            result = new LatLng(location.getLatitude(), location.getLongitude());
        }


        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Snackbar.make(view, "WRITE_CONTACTS Accepted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    getCurrentLocation();


                } else {

                    Snackbar.make(view, "WRITE_CONTACTS Denied", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    // Permission Denied

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void stopListeningLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
        final NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();

    }

    private void startListeningLocation() {
        locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);

        // get shared preferences
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getBaseContext());
        final long time = sp.getInt(PrefUtils.PREF_UPDATE_TIME, 0) * 1000;
        final float distance = Float.intBitsToFloat(sp.getInt(PrefUtils.PREF_UPDATE_DISTANCE, mDistance));
        final int accuracy = Integer.parseInt(sp.getString(PrefUtils.PREF_UPDATE_ACCURACY, "2"));

        createLocationListenerNotification();

        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // check if enabled and if not send to the GSP settings
        // Better Solution would be to display a dialog and suggesting to
        // go to settings.

        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        } else {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(accuracy);
            criteria.setBearingRequired(true);
            criteria.setSpeedRequired(true);


            final Context c = this.getActivity();
            provider = locationManager.getBestProvider(criteria, false);

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(provider, time, distance, this);
        }
    }

    private void createLocationListenerNotification() {
        Resources resources = getResources();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this.getActivity())
//                        .setSmallIcon(R.drawable.ic_action_map)
                        .setCategory(resources.getString(R.string.app_name))
                        .setContentTitle(resources.getString(R.string.recording_locations_title))
                        .setContentText(resources.getString(R.string.recording_locations_text));

        // Creates an explicit intent for an Activity
        Intent resultIntent = new Intent(this.getActivity(), MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this.getActivity());

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder.setVisibility(1);
        }

        int notifyID = 1;
        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        mNotificationManager.notify(notifyID, mBuilder.build());
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        updatePath();
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        updatePath();
    }




    private void updateMarkers() {
        // Get Screen Size
        final int size =(int)(50*getResources().getDisplayMetrics().density);
        LatLngBounds bnd = mMap.getProjection().getVisibleRegion().latLngBounds;
        List<Ship> ships=ShipDatabase.getShips(bnd);
        mMap.clear();

        for(Ship ship:ships){
            final LatLng[] points =  ship.getRoutePoints();

            if (points!=null)
            for(LatLng latLng:ship.getRoutePoints()){
                // Add a thin red line from London to New York.
               mRouteLine= mMap.addPolyline(new PolylineOptions()
                        .add(points)
                        .width(getResources().getInteger(R.integer.route_width))
                        .color(Color.RED));
            }
            Bitmap boatImage = ship.getImage();
            MarkerOptions marker = new MarkerOptions()
                    .title(ship.getName())
                    .snippet(ship.getMmsi())
                    .position(ship.getLocation());

            if (boatImage!=null){

                Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                Bitmap bmp = Bitmap.createBitmap(size, size, conf);
                Bitmap shipImage = Bitmap.createScaledBitmap(ship.getImage(),size,size,false);

                Canvas c = new Canvas(bmp);

                // paint defines the text color,
                // stroke width, size
                Paint color = new Paint();
                color.setTextSize(15);
                color.setColor(Color.BLACK);

                c.drawBitmap(shipImage, 0, 0, color);


                // Need to get Correct Location for Text;
                float textWidth=color.measureText(ship.getName());
                float center = (size-textWidth)/2;

                c.drawText(ship.getName(), center, 40, color);

                marker.icon(BitmapDescriptorFactory.fromBitmap(bmp));
                marker.anchor(0.5f, 1);
            }


            mMarkers.add(marker);
            mMap.addMarker(marker);

        }



        final ShipAdapter adapter = new ShipAdapter(getContext(),ships.toArray(new Ship[ships.size()]));


        mShipList.setAdapter(adapter);
    }



    @Override
    public void QueryComplete(Ship[] ships) {
        ShipDatabase.addShips(ships);



        updateMarkers();
        //ships;

    }



    @Override
    public void updateTimerItem() {

//        if (mMap.getProjection()!=null&&mMap.getProjection().getVisibleRegion()!=null){
//            LatLngBounds bnd = mMap.getProjection().getVisibleRegion().latLngBounds;

  //          task =   new ShipQueryAsyncTask(this);
 //           task.execute(bnd);
//        }



    }
}
