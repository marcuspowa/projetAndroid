package utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
        try {
            URL url = new URL(urlStr);

            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = null;
            in = new BufferedInputStream(urlConnection.getInputStream());
            String txtReponse = streamToString(in);
            urlConnection.disconnect();
            return txtReponse;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
                e.printStackTrace();
            }
        }
    }

}
