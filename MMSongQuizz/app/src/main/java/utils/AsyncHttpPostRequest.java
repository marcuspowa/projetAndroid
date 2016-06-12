package utils;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by remy on 12/03/2016.
 */
public class AsyncHttpPostRequest extends AsyncTask<String, Void, String> {
    private HttpUtils http;
    private String postData;
    private Map<String, String> headers;

    public AsyncHttpPostRequest(HttpUtils http, String postData, Map<String, String> headers){
        this.http = http;
        this.postData = postData;
        this.headers = headers;
    }
    public AsyncHttpPostRequest(HttpUtils http, String postData){
        this(http,postData, new HashMap<String, String>());
    }

    @Override
    protected String doInBackground(String... params) {

        String res = http.requestPostJson(params[0], postData, headers);
        return res;
    }

    public String getResult(){
        String result = null;
        try {
            result = this.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }
}
