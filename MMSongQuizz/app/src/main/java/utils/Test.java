package utils;

import android.net.Uri;
import android.util.Base64;


import java.io.UnsupportedEncodingException;

import java.net.URI;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Mickael on 08/03/2016.
 */
public class Test {
    String method;
    String requete;
    String parametre;

    public Test(String method,String requete,String parametre){
        this.method = method;

        this.requete = requete;
        this.parametre = parametre;
    }

    public String getSignature(){
        String encodedRequete =Uri.encode(requete, "UTF-8");
        String encodedParametre =Uri.encode(parametre, "UTF-8");

        String signature="";
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        String base=method+"&";
        base+=encodedRequete+"&";
        base+=encodedParametre;

        String key = "c9a6a97e3fe0f115120471c481190baa96649eea";
        try {
            signature = sha1(base, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base+" - "+signature;
    }

   /* public String encrypt(String baseString, String key)
            throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException,  UnsupportedEncodingException
    {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), mac.getAlgorithm());
        mac.init(secret);
        byte[] data = baseString.getBytes("UTF-8");
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        byte[] digest = mac.doFinal(secret.getBytes());
        byte[] result= Base64.encode(digest, Base64.DEFAULT);
        return new String(result);
    }*/

    private String sha1(String base, String keyString) throws
        UnsupportedEncodingException, NoSuchAlgorithmException,
        InvalidKeyException {

        SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(key);

        byte[] bytes = mac.doFinal(base.getBytes("UTF-8"));

        return new String(Base64.encodeToString(bytes, Base64.DEFAULT));
    }
}
