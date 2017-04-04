package in.pankajadhyapak.popularmovies2;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


/**
 * Created by pankaj on 04/04/17.
 */

public class App extends Application {

    private static final String TAG = "App";
    private static App instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        Log.e(TAG, "Creating our Application");
    }

    public static App getInstance()
    {
        return instance;
    }

    public static boolean hasNetwork ()
    {
        return instance.checkIfHasNetwork();
    }

    public boolean checkIfHasNetwork()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
