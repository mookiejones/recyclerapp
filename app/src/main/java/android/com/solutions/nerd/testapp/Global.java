package android.com.solutions.nerd.testapp;

/**
 * Created by admin on 11/7/15.
 */
public class Global {
    public static final String api_image_path="http://nerd.solutions/sailing/";
    public static final String api_base="http://nerd.solutions";
    public static final String api_string = "http://nerd.solutions/api/";
    public static final String api_sample = "http://nerd.solutions/api2";

    public static String getApiPath(String args,boolean isSample) {
        if(isSample)
            return api_sample;
        return api_string + args;
    }
}
