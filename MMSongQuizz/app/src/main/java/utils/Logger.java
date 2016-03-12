package utils;

import android.util.Log;

import com.example.remy.mmsongquizz.activities.MMQuizzApplication;

/**
 * Created by remy on 12/03/2016.
 */
public class Logger {

    public static void debug(String message){
        Log.d(MMQuizzApplication.NAME, message);
    }

    public static void info(String message){
        Log.i(MMQuizzApplication.NAME, message);
    }

    public static void warn(String message){
        Log.w(MMQuizzApplication.NAME, message);
    }

    public static void error(String message){
        Log.e(MMQuizzApplication.NAME, message);
    }

}
