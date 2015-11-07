package android.com.solutions.nerd.testapp;

/**
 * Created by admin on 11/7/15.
 */
public class Global {
    public static final String api_string="http://sailsite.meteor.com/api/";
    public static String getApiPath(String args){
        return api_string+args;
    }
}
