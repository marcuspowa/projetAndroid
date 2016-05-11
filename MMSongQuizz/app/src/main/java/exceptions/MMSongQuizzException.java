package exceptions;

import utils.Logger;

/**
 * Created by remy on 11/05/2016.
 */
public class MMSongQuizzException extends Exception{

    public MMSongQuizzException(String message){
        super(message);
        Logger.error("[MMSongQuizzException] "+message);
    }

    public MMSongQuizzException(String message, Exception innerException){
        super(message, innerException);
        Logger.error("[MMSongQuizzException] " + message);
    }
}
