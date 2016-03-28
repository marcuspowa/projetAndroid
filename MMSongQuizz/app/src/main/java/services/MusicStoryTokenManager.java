package services;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import javax.inject.Inject;

import utils.AsyncHttpRequest;
import utils.HttpUtils;
import utils.Logger;
import utils.MusicStoryEncryption;

/**
 * Created by remy on 28/03/2016.
 */
public class MusicStoryTokenManager {
    private HttpUtils httpUtils;

    @Inject
    public MusicStoryTokenManager(HttpUtils httpUtils){
        this.httpUtils = httpUtils;
    }


    public void getToken(){
        String token = "", tokenSecret = "";
        String baseUrl = "http://api.music-story.com/oauth/request_token";
        HashMap<String,String> param = new HashMap<>();
        param.put("oauth_consumer_key", MusicStoryEncryption.consumerKey);
        String signedUrl= MusicStoryEncryption.signUrl(baseUrl, param);


        AsyncHttpRequest req = httpUtils.asyncRequest(signedUrl);

        String requestResult = req.GetResult();

        try {
            XmlPullParserFactory xmlFactory = XmlPullParserFactory.newInstance();
            xmlFactory.setNamespaceAware(true);
            XmlPullParser xpp= xmlFactory.newPullParser();
            xpp.setInput(new StringReader(requestResult));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_TAG){
                    if("token".equals(xpp.getName())){
                        eventType = xpp.next();
                        token = xpp.getText();
                    }
                    else if("token_secret".equals(xpp.getName())){
                        eventType = xpp.next();
                        tokenSecret = xpp.getText();
                    }
                }
                eventType = xpp.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MusicStoryEncryption.setToken(token, tokenSecret);
    }

}
