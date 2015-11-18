package android.com.solutions.nerd.testapp;

/**
 * Created by admin on 11/7/15.
 */
public class Global {
    public static final String api_image_path="http://nerd.solutions/sailing/";
    public static final String api_base="http://nerd.solutions";
    public static final String api_string = "http://nerd.solutions/api/";

    public static String getApiPath(String args) {
        return api_string + args;
    }
}
