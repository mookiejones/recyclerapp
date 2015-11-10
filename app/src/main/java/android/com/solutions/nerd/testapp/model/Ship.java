package android.com.solutions.nerd.testapp.model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mookie on 11/7/15.
 * for Nerd.Solutions
 */
public class Ship {


    public static final String MMSI = "mmsi";
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    public static final String SPEED = "speed";
    public static final String LENGTH = "length";
    public static final String WIDTH = "width";
    public static final String DRAFT = "draft";
    public static final String NAME = "name";
    public static final String CALLSIGN = "callsign";
    public static final String DESTINATION = "destination";
    public static final String ETA = "eta";
    public static final String IMO = "imo";
    public static final String HEADING = "heading";
    public static final String COURSE = "course";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String ROUTE = "route";
    public static final String PICTURE = "picture";
    public static final String LINK = "link";
    private String id = "";
    private String mmsi = "";
    private double lat;
    private double lng;
    private String speed = "";
    private String length = "";
    private String width = "";
    private String draft = "";
    private String name = "";
    private String callsign = "";
    private String destination = "";
    private String eta = "";
    private String imo = "";
    private String heading = "";
    private String course = "";
    private String status = "";
    private String type = "";
    private String route = "";
    private String picture = "";
    private String link = "";


    public Ship(JSONObject json) {
        try {
            if (json.has(MMSI))
                mmsi = json.getString(MMSI);

            if (json.has(LAT))
                lat = json.getDouble(LAT);

            if (json.has(LNG))
                lng = json.getDouble(LNG);

            if (json.has(SPEED))
                speed = json.getString(SPEED);

            if (json.has(LENGTH))
                length = json.getString(LENGTH);

            if (json.has(WIDTH))
                width = json.getString(WIDTH);

            if (json.has(DRAFT))
                draft = json.getString(DRAFT);

            if (json.has(NAME))
                name = json.getString(NAME);

            if (json.has(CALLSIGN))
                callsign = json.getString(CALLSIGN);

            if (json.has(DESTINATION))
                destination = json.getString(DESTINATION);

            if (json.has(ETA))
                eta = json.getString(ETA);

            if (json.has(IMO))
                imo = json.getString(IMO);

            if (json.has(HEADING))
                heading = json.getString(HEADING);

            if (json.has(COURSE))
                course = json.getString(COURSE);

            if (json.has(STATUS))
                status = json.getString(STATUS);

            if (json.has(TYPE))
                type = json.getString(TYPE);

            if (json.has(ROUTE))
                route = json.getString(ROUTE);

            if (json.has(PICTURE))
                picture = json.getString(PICTURE);

            if (json.has(LINK))
                link = json.getString(LINK);
        } catch (JSONException e) {
            Log.e(Ship.class.getName(), e.getMessage());
        }
    }

    public String getId() {
        return id;
    }

    public String getMmsi() {
        return mmsi;
    }

    public void setMmsi(String mmsi) {
        this.mmsi = mmsi;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getDraft() {
        return draft;
    }

    public void setDraft(String draft) {
        this.draft = draft;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getImo() {
        return imo;
    }

    public void setImo(String imo) {
        this.imo = imo;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LatLng getLocation() {
        double _lat = lat;
        double _lng = lng;
        return new LatLng(_lat, _lng);
    }

    public MarkerOptions getMarker() {
        final LatLng latlng = getLocation();

        MarkerOptions marker = new MarkerOptions();
        marker.title(name);
        marker.position(latlng);
        marker.snippet(name);
        marker.alpha(0.7f);
//		marker.icon(BitmapDescriptorFactory.defaultMarker(icon));
//		marker.icon(BitmapDescriptorFactory.fromBitmap(UrlImageViewHelper.getCachedBitmap(picture)));

        return marker;
    }

}