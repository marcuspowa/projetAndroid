package utils;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by remy on 12/03/2016.
 */
public class AsyncHttpPostRequest extends AsyncTask<String, Void, String> {
    private HttpUtils http;
    private JSONObject postData;

    public AsyncHttpPostRequest(HttpUtils http, JSONObject postData){
        this.http = http;
        this.postData = postData;
    }


    @Override
    protected String doInBackground(String... params) {

        String res = http.requestPostJson(params[0], postData);
        return res;
    }

    public String GetResult(){
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
