package android.com.solutions.nerd.testapp.map;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.com.solutions.nerd.testapp.R;
import android.com.solutions.nerd.testapp.main.MainActivity;
import android.com.solutions.nerd.testapp.model.TagInfo;
import android.com.solutions.nerd.testapp.utils.AccountUtils;
import android.com.solutions.nerd.testapp.utils.PrefUtils;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
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
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

//import com.firebase.client.Firebase;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * Created by mookie on 10/22/15.
 * for Nerd.Solutions
 */
public class MapFragment extends Fragment
        implements
        SharedPreferences.OnSharedPreferenceChangeListener,
        LocationListener,
        GoogleMap.OnInfoWindowClickListener {
    private static final String TAG = MapFragment.class.getSimpleName();
    private static final String tileUrl = "http://earthncseamless.s3.amazonaws.com/{zoom}/{x}/{y}.png";
    private final static LatLng dearborn = new LatLng(42.3222600f, -83.1763100f);

    private FloatingActionButton mDeleteButtoon;

    public static MapFragment getInstance() {
        if (mInstance == null)
            mInstance = new MapFragment();
        return mInstance;
    }

    private static MapFragment mInstance;
 //   private Firebase mFirebaseJourneys;

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


      //  Firebase.setAndroidContext(this.getActivity());

        // Set Text
        /*if (mFirebaseJourneys==null)
        {
            String url = AccountUtils.getUserJourneysURL(this.getActivity());
            mFirebaseJourneys = new Firebase(url);
        }*/

        if (mMap == null) {
            setupMapIfNeeded();
        }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getBaseContext());


        if (mMap == null) {
            setupMapIfNeeded();
        }
        setHasOptionsMenu(true);

    }

    private Menu mFloatingMenu;

    private GoogleMap mMap;
    @SuppressWarnings("UnusedDeclaration")
    private PolylineOptions polyLineOptions = new PolylineOptions().geodesic(true);
    @SuppressWarnings("UnusedDeclaration")
    private Dictionary<String, TagInfo> mMarkerDict;
    private final List<Marker> mTags = new ArrayList<>();
    private Polyline mRouteLine;
    private final List<Marker> mMarkers = new ArrayList<>();

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

//        return super.onCreateView(inflater, container, savedInstanceState);

    }

    private static View view;

    private void setupMapIfNeeded() {
        if (mMap != null) {
            return;
        }
        mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                updateMarkers();
            }
        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                selectedMarker = marker;
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
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
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
        });


        LatLng current = getCurrentLocation();
        if (current != null) {
            CameraPosition cp = new CameraPosition(current,8,0,0);

            CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cp);
            String dataUrl = AccountUtils.getUserDataUrl(this.getActivity());
/*
            Firebase ref = new Firebase(AccountUtils.getUserDataUrl(this.getActivity()));

            Firebase child = ref.child("location");
            child.setValue(current);

*/

            mMap.moveCamera(cu);

        }


        /* Get Current Click Location */
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mSelectedLatLng = latLng;
                updateMarkers();
            }
        });
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

    }

    private Marker selectedMarker;
    private LatLng mSelectedLatLng;

    private void updatePath() {

    }

    private void updateMarkers() {

    }

    /**
     * Called when a shared preference is changed, added, or removed. This
     * may be called even if a preference is set to its existing value.
     * <p/>
     * <p>This callback will be run on your main thread.
     *
     * @param sharedPreferences The {@link SharedPreferences} that received
     *                          the change.
     * @param key               The key of the preference that was changed, added, or
     */
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

    /**
     * Called when the location has changed.
     * <p/>
     * <p> There are no restrictions on the use of the supplied Location object.
     *
     * @param location The new location, as a Location object.
     */
    @Override
    public void onLocationChanged(Location location) {

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderEnabled(String provider) {

    }

    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderDisabled(String provider) {

    }
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private LatLng getCurrentLocation() {
        LatLng result = null;

        //noinspection PointlessBooleanExpression
//        if (!Config.DEBUG)
 //           return dearborn;

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
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                String[] permissions = new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                };

                ActivityCompat.requestPermissions(getActivity(),permissions,REQUEST_CODE_ASK_PERMISSIONS);
                return null;
            }
            Location location = locationManager.getLastKnownLocation(provider);


           result=new LatLng(location.getLatitude(), location.getLongitude());
        }


        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
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
    private LocationManager locationManager;

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

    private final int mDistance = 100;

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
    private String provider;
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

}
