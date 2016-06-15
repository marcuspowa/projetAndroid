package com.example.remy.mmsongquizz.activities;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


import dagger.ObjectGraph;
import utils.Logger;
import utils.MMQuizzModule;

/**
 * Created by remy on 12/03/2016.
 */
public class MMQuizzApplication extends Application {

    public static final String NAME = "MMSongQuizz";
    private static Context context;
    private ObjectGraph container;

    public MMQuizzApplication(){
        super();
        container = ObjectGraph.create(new MMQuizzModule());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MMQuizzApplication.context = getApplicationContext();
    }

    public ObjectGraph getContainer() {
        return container;
    }

    public static Context getContext() {
        return MMQuizzApplication.context;
    }

    public void notify(String msg){
        // Toast : affichage sur l'ecran
        Logger.info("[TOAST] " + msg);
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        t.show();
    }



    public boolean networkIsAvailable()
    {
        String sType = "Aucun réseau détecté";
        boolean networkAvailable = false;
        ConnectivityManager cnMngr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cnMngr.getActiveNetworkInfo();

        if (netInfo != null)
        {
            NetworkInfo.State netState = netInfo.getState();
            if (netState.compareTo(NetworkInfo.State.CONNECTED) == 0)
            {
                networkAvailable = true;
                int netType= netInfo.getType();
                switch (netType)
                {
                    case ConnectivityManager.TYPE_MOBILE :
                        sType = "Réseau mobile détecté"; break;
                    case ConnectivityManager.TYPE_WIFI :
                        sType = "Réseau wifi détecté"; break;
                }
            }
        }
        Logger.debug(sType);
        return networkAvailable;
    }

}
