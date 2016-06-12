package utils;

import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import javax.inject.Inject;

import models.Track;

/**
 * Created by remy on 12/06/2016.
 */
public class SpotifyUtils {
    public static final String CLIENT_ID = "96efaa3336f641688ed968c9a0498c34";
    public static final String CLIENT_SECRET = "1cd1b0848e144d1093cd7f1f29d92902";
    public static final String REDIRECT_URI = "com.example.remy.mmsongquizz://callback";

    public static final String BASE_URL = "https://api.spotify.com/v1/";

    public static String currentToken = null;
    private HttpUtils httpUtils;

    @Inject
    public SpotifyUtils(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }

    public void fetchToken(){
        HashMap<String, String> headers = new HashMap<>();
        String base64encoded = Base64.encodeToString((CLIENT_ID+":"+CLIENT_SECRET).getBytes(), Base64.DEFAULT);

        String url = "https://accounts.spotify.com/api/token";

        headers.put("Authorization","Basic OTZlZmFhMzMzNmY2NDE2ODhlZDk2OGM5YTA0OThjMzQ6MWNkMWIwODQ4ZTE0NGQxMDkzY2Q3ZjFmMjlkOTI5MDI=");
        headers.put("content-type", "application/x-www-form-urlencoded");

        HashMap<String, String> bodyParams = new HashMap<>();
        bodyParams.put("grant_type", "client_credentials");

        String postData = HttpUtils.concatParams(bodyParams);

        AsyncHttpPostRequest req = httpUtils.asyncPostRequest(url,postData, headers);

        String requestResult = req.getResult();

        try {
            Logger.debug("token result: " + requestResult);
            JSONObject jsonObject = new JSONObject(requestResult);
            String accesToken = jsonObject.getString("access_token");

            currentToken = accesToken;
        } catch (JSONException e) {
            Logger.error("[SpotifyUtils] json error", e);
        }
    }

}
