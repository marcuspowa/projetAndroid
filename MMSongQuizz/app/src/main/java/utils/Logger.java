package utils;

import android.util.Log;

import com.example.remy.mmsongquizz.activities.MMQuizzApplication;

/**
 * Created by remy on 12/03/2016.
 */
public class Logger {

    public static void debug(String message){
        message = message == null ? "" : message;
        Log.d(MMQuizzApplication.NAME, message);
    }
    public static void debug(String message, Exception e){
        debug(message + " -EXCEPTION- " + e.getMessage());
    }

    public static void info(String message){
        message = message == null ? "" : message;
        Log.i(MMQuizzApplication.NAME, message);
    }
    public static void info(String message, Exception e){
        info(message + " -EXCEPTION- " + e.getMessage());
    }


    public static void warn(String message) {
        message = message == null ? "" : message;
        Log.w(MMQuizzApplication.NAME, message);
    }
    public static void warn(String message, Exception e){
        warn(message + " -EXCEPTION- " + e.getMessage());
    }

    public static void error(String message) {
        message = message == null ? "" : message;
        Log.e(MMQuizzApplication.NAME, message);
    }
    public static void error(String message, Exception e){
        error(message +" -EXCEPTION- "+ e.getMessage());
    }

}
