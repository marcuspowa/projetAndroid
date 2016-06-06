package utils;

import android.net.Uri;
import android.text.TextUtils;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
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

    public AsyncHttpPostRequest asyncPostRequest(String urlStr, JSONObject postData){
        AsyncHttpPostRequest asyncHttpRequest = new AsyncHttpPostRequest(this, postData);

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

    public String requestPostJson(String urlStr, JSONObject postData) {
        Logger.debug("[HttpUtils] sending POST http request to: " + urlStr + " with data: " + postData.toString());
        try {
            URL url = new URL(urlStr);

            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(postData.toString());
            writer.flush();
            writer.close();
            os.close();

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
