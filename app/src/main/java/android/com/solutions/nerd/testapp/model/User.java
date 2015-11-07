package android.com.solutions.nerd.testapp.model;

//import com.fasterxml.jackson.annotation.JsonIgnore;

import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;


public class User {
    private static final String TAG = User.class.getSimpleName();


    //  @JsonIgnore
    private String key;
    private HashMap<String, Marker> mMarkers;

    public User() {
        mMarkers = new HashMap<>();

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public HashMap<String, Marker> getMarkers() {
        return mMarkers;
    }

    public void setMarkers(HashMap<String, Marker> mMarkers) {
        this.mMarkers = mMarkers;
    }


}

