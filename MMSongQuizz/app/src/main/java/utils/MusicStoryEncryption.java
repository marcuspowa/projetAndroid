package utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Mickael on 28/03/2016.
 */
public class MusicStoryEncryption {
    private static final String consumerSecret  = "9f38e975910d2dd21da469bab37a24436b57b51f";
    public static final String consumerKey  = "c9a6a97e3fe0f115120471c481190baa96649eea";

    public static String signUrl(String baseUrl,Map<String,String> params,String tokenSecret){
        String signature="";
        String encodedRequete = Uri.encode(baseUrl, "UTF-8");

        if(tokenSecret != ""){
            params.put("oauth_token",tokenSecret);
        }
        String encodedParams=Uri.encode(concatParams(params),"UTF-8");
        String base="GET&"+encodedRequete+"&"+encodedParams;
        Logger.debug(encodedParams);
        String key = consumerSecret+"&"+tokenSecret;
        try {
            signature = sha1(base, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.put("oauth_signature",signature);
        encodedParams=concatParams(params);

        return baseUrl+"?"+encodedParams;
    }

    private static String sha1(String base, String keyString) throws
    UnsupportedEncodingException, NoSuchAlgorithmException,
    InvalidKeyException {
        SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(key);

        byte[] bytes = mac.doFinal(base.getBytes("UTF-8"));

        return new String(Base64.encodeToString(bytes, Base64.DEFAULT));
    }

    private static String concatParams(Map<String,String> params){

        ArrayList<String> list=new ArrayList<String>();
        //Parcour les paramêtres
        for(Map.Entry<String,String> entry : params.entrySet()){
            list.add(entry.getKey()+"="+entry.getValue());
        }
        return TextUtils.join("&", list);
    }

}
