package android.com.solutions.nerd.testapp;

/**
 * Created by admin on 11/7/15.
 */
public class Global {
    public static final String api_image_path="http://nerd.solutions/sailing/";
    public static final String api_base="http://nerd.solutions";
    public static final String api_string = "http://nerd.solutions/api/";
    public static final String api_sample = "http://nerd.solutions/api2";
    private static final String image_search = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
    final String google_image = "https://www.google.com/search?q=hardin+sailboats&espv=2&biw=1600&bih=744&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiI-b_srKLJAhVH8x4KHcFOByIQ_AUIBigB&dpr=1#q=hardin+sailboats&tbs=imgo:1&tbm=isch&tbas=0";

    public static String getApiPath(String args,boolean isSample) {
        if(isSample)
            return api_sample;
        return api_string + args;
    }

    public static String getImageSearchUrl(String name) {
        String url = name.replace(" ", "%20");


        return image_search + url;
    }

}
