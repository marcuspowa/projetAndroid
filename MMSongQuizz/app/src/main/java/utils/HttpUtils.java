package utils;

import android.net.Uri;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by remy on 12/03/2016.
 */
public class HttpUtils {


    @Inject
    public HttpUtils(){

    }


    public AsyncHttpRequest asyncRequest(String urlStr){
        AsyncHttpRequest asyncHttpRequest = new AsyncHttpRequest(this);

        asyncHttpRequest.execute(urlStr);

        return asyncHttpRequest;
    }


    public String request(String urlStr) {
        Logger.debug("[HttpUtils] sending http request to: " + urlStr);
        try {
            URL url = new URL(urlStr);

            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = null;
            in = new BufferedInputStream(urlConnection.getInputStream());
            String txtResponse = streamToString(in);
            int code = urlConnection.getResponseCode();
            Logger.debug("HTTP code:" + code);
            urlConnection.disconnect();
            return txtResponse;
        } catch (MalformedURLException e) {
            Logger.error("MalformedURLException in HttpUtils.request",e);
            e.printStackTrace();
        } catch (IOException e) {
            Logger.error("IOException in HttpUtils.request",e);
            e.printStackTrace();
        }
        return null;
    }


    private String streamToString(InputStream in) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                Logger.error("IOException in HttpUtils.streamToString",e);
                e.printStackTrace();
            }
        }
    }

    public static String concatParams(Map<String,String> params){

        ArrayList<String> list=new ArrayList<String>();
        //Parcour les paramêtres
        for(Map.Entry<String,String> entry : params.entrySet()){
            list.add(entry.getKey()+"="+Uri.encode(entry.getValue(), "UTF-8"));
        }
        return TextUtils.join("&", list);
    }

}
