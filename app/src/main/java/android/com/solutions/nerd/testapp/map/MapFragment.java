package android.com.solutions.nerd.testapp.map;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.com.solutions.nerd.testapp.R;
import android.com.solutions.nerd.testapp.main.MainActivity;
import android.com.solutions.nerd.testapp.model.Ship;
import android.com.solutions.nerd.testapp.model.TagInfo;
import android.com.solutions.nerd.testapp.utils.PrefUtils;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

//import com.firebase.client.Firebase;

/**
 * Created by mookie on 10/22/15.
 * for Nerd.Solutions
 */
public class MapFragment extends Fragment
        implements
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener,
        SharedPreferences.OnSharedPreferenceChangeListener,
        LocationListener,
        GoogleMap.OnInfoWindowClickListener {
    private static final String TAG = MapFragment.class.getSimpleName();
    private static final String tileUrl = "http://earthncseamless.s3.amazonaws.com/{zoom}/{x}/{y}.png";
    private final static LatLng dearborn = new LatLng(42.3222600f, -83.1763100f);
    private static MapFragment mInstance;
    private static View view;
    private final List<Marker> mTags = new ArrayList<>();
    private final List<Marker> mMarkers = new ArrayList<>();
    //   private Firebase mFirebaseJourneys;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private final int mDistance = 100;
    private HashMap<String, Ship> mShips = new HashMap<>();
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
    private Marker selectedMarker;
    private LatLng mSelectedLatLng;
    private LocationManager locationManager;
    private String provider;

    public static MapFragment getInstance() {
        if (mInstance == null)
            mInstance = new MapFragment();
        return mInstance;
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


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                updateMarkers();

                return false;
            }
        });


        if (mMap == null) {
            setupMapIfNeeded(savedInstanceState);
        }
        setHasOptionsMenu(true);

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // retain this fragment
        setRetainInstance(true);
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


    @Override
    public void onMapClick(LatLng latLng) {
        mSelectedLatLng = latLng;
        updateMarkers();
    }

    private void setupMapIfNeeded(final Bundle savedInstanceState) {
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

        mMap.setOnMarkerDragListener(this);
        mMap.setOnMarkerClickListener(this);

        // Disable Buttons

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(final Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                final View v = getLayoutInflater(savedInstanceState).inflate(R.layout.map_info_window, null);


                String id = marker.getId();
                String snippet = marker.getSnippet();

                final Ship mShip = mShips.get(snippet);

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
                final TextView linkView = (TextView)v.findViewById(R.id.link);
                linkView.setText(Html.fromHtml("<a href=\"" + mShip.getLink() + "\">More</a>"));
                linkView.setMovementMethod(LinkMovementMethod.getInstance());

                ((TextView) v.findViewById(R.id.name)).setText(mShip.getName());


                final ImageView img = (ImageView) v.findViewById(R.id.marker_image);
                if (!mShip.getPicture().isEmpty()) {
                    Picasso.with(getContext())
                            .load(mShip.getPicture())
                            .into(img);
                }
                String route = mShip.getRoute();

                ((TextView) v.findViewById(R.id.route)).setText(route);




                ((TextView) v.findViewById(R.id.status)).setText(mShip.getStatus());
                ((TextView) v.findViewById(R.id.type)).setText(mShip.getType());
                ((TextView) v.findViewById(R.id.width)).setText(mShip.getWidth());

                ((FloatingActionButton) v.findViewById(R.id.boat_more)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mShip.getLink().isEmpty())
                            return;
                        Intent intent = new Intent(Intent.ACTION_VIEW);

                        intent.setData(Uri.parse(mShip.getLink()));
                        startActivity(intent);


                    }
                });

                //Width

                return v;


            }
        });
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setBuildingsEnabled(false);
        mMap.setIndoorEnabled(false);


        LatLng current = getCurrentLocation();
        if (current != null) {
            CameraPosition cp = new CameraPosition(current, 8, 0, 0);

            CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cp);


            mMap.moveCamera(cu);

        }



        /* Get Current Click Location */
        mMap.setOnMapClickListener(this);


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

    private void updatePath() {

    }

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

    private void updateMarkers() {
        LatLngBounds bnd = mMap.getProjection().getVisibleRegion().latLngBounds;
        new QueryShipsTask().execute(bnd);
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

    private class QueryShipsTask extends AsyncTask<LatLngBounds, Void, List<Ship>> {
        private static final String url_path = "http://ais.boatnerd.com/ship-data.jsonp-alt.php?_1402627434151=";
        HttpURLConnection urlConnection = null;

        @Override
        protected List<Ship> doInBackground(LatLngBounds... bounds) {

            URL url = null;
            try {
                String response = "";
                url = new URL(url_path);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String s = "";


                while ((s = reader.readLine()) != null) {
                    response += s;
                }
                response = response.replace("callback(", "");
                response = response.replace("]);", "]}");

                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Ship ship = new Ship(jsonObject);
                    mShips.put(ship.getMmsi(), ship);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            List<Ship> ships = new ArrayList<>();
            LatLngBounds bound = bounds[0];
            Set<String> keys = mShips.keySet();
            for (String key : keys) {
                Ship ship = mShips.get(key);
                if (bound.contains(ship.getLocation()))
                    ships.add(ship);

            }
            return ships;

        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param ships The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(List<Ship> ships) {
            mMap.clear();
            mShips.clear();

            for (Ship ship : ships) {
                List<LatLng> routePoints = ship.getRoutePoints();


                LatLng[] points =  routePoints.toArray(new LatLng[routePoints.size()]);



                for(LatLng latLng:ship.getRoutePoints()){
                    // Add a thin red line from London to New York.
                    Polyline line = mMap.addPolyline(new PolylineOptions()
                            .add(points)
                            .width(5)
                            .color(Color.RED));


                }
                mShips.put(ship.getMmsi(), ship);
                MarkerOptions marker = new MarkerOptions()

                        .title(ship.getName())
                        .snippet(ship.getMmsi())


                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                        .position(ship.getLocation());


                mMap.addMarker(marker);
            }
        }
    }
}
