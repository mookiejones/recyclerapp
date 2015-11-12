package android.com.solutions.nerd.testapp.utils;

import android.util.Log;

/**
 * Created by mookie on 11/5/15.
 * for Nerd.Solutions
 */
public class LogUtils {
    public static String getLogTag(Class name) {
        return name.getSimpleName();

    }
    public static void LOGD(Class name,String message){
        String tag = getLogTag(name);
        if (Log.isLoggable(tag,Log.DEBUG)){
            Log.d(tag,message);
        }
    }
    public static void LOGD(String tag,String message){

    }
}