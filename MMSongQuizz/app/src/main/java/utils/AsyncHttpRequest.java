package utils;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by remy on 12/03/2016.
 */
public class AsyncHttpRequest extends AsyncTask<String, Void, String> {
    private HttpUtils http;

    public AsyncHttpRequest(HttpUtils http){
        this.http = http;
    }


    @Override
    protected String doInBackground(String... params) {


        String res = http.request(params[0]);
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
