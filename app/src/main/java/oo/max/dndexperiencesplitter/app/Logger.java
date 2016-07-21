package oo.max.dndexperiencesplitter.app;

import android.util.Log;

public class Logger {

    public static final String TAG = "oo.max.dnd.expsplitter";

    public static void debug(String message) {
        Log.e(TAG, message);
    }

    public static void error(Throwable e) {
        Log.e(TAG, e.getMessage(), e);
    }

    public static void info(String s) {
        Log.i(TAG, s);
    }

    public static void error(String message) {
        Log.e(TAG, message);
    }

}
