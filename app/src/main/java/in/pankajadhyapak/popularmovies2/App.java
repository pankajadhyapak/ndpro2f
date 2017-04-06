package in.pankajadhyapak.popularmovies2;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

public class App extends Application {

    private static final String TAG = "Mail Application Class";
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
        Snackbar.make(v, R.string.network_error_msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showNetworkLoading(View v) {
        Snackbar.make(v, R.string.loading_data_msg, Snackbar.LENGTH_LONG).show();
    }
}
