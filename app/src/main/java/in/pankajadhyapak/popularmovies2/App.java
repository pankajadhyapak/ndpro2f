package in.pankajadhyapak.popularmovies2;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;


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

    public static void showNetworkError(View v) {
        Snackbar.make(v, "Please connect to wifi or enable cellular data!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
