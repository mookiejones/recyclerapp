package android.com.solutions.nerd.testapp.utils;

/**
 * Created by mookie on 11/5/15.
 * for Nerd.Solutions
 */
public class LogUtils {
    public static String getLogTag(Class name) {
        return name.getSimpleName();

    }
}