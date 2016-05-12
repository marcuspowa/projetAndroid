package utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by remy on 05/05/2016.
 */
public class EchonestUtils {
    private static HashMap<String, String> echonestResponseCodes = new HashMap<String, String>();
    static
    {
        echonestResponseCodes = new HashMap<String, String>();
        echonestResponseCodes.put("-1", "Unknown Error");
        echonestResponseCodes.put("0", "Success");
        echonestResponseCodes.put("1", "Missing/ Invalid API Key");
        echonestResponseCodes.put("2", "This API key is not allowed to call this method");
        echonestResponseCodes.put("3", "Rate Limit Exceeded");
        echonestResponseCodes.put("4", "Missing Parameter");
        echonestResponseCodes.put("5", "Invalid Parameter");
    }


    public static final String API_KEY = "61FRNU3NSVCJLPBIU";
    public static final String BASE_URL = "http://developer.echonest.com/api/v4/";

    public static boolean getSuccessFromReponse(String response){
        if(null == response){
            Logger.warn("response is null");
            return false;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONObject jsonResponse = jsonObject.getJSONObject("response");
            JSONObject jsonStatus = jsonResponse.getJSONObject("status");
            int code = jsonStatus.getInt("code");
            if( code == 0 ){
                return true;
            }
            String message = jsonStatus.getString("message");
            Logger.info("Echonest Error ("+code+") Message: " + message);
        } catch (JSONException e) {
            Logger.warn("Echonest Response error", e);
        }
        return false;
    }

}
