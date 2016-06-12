package utils;

import android.os.AsyncTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by remy on 12/03/2016.
 */
public class AsyncHttpRequest extends AsyncTask<String, Void, String> {
    private HttpUtils http;
    private Map<String, String> headers;

    public AsyncHttpRequest(HttpUtils http, Map<String, String> headers){
        this.http = http;
        this.headers = headers;
    }
    public AsyncHttpRequest(HttpUtils http){
        this(http, new HashMap<String, String>());
    }


    @Override
    protected String doInBackground(String... params) {


        String res = http.request(params[0], headers);
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
