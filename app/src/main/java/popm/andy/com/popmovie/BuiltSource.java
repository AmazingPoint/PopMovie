package popm.andy.com.popmovie;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telecom.Connection;

/**
 * Created by andy on 12/10/16.
 */

public final class BuiltSource {
    public static final String MOVIE_API_VERSION = "3";
    public static final String MOVIE_PORTAL = "http";
    public static final String MOVIE_API_KEY = "bf55cc2ee381fad86453187d61aee408";
    public static final String MOVIE_BASE_URL = "api.themoviedb.org";
    public static final String MOVIE_PICTURE_BASE_URL = "http://image.tmdb.org/t/p/w185";
    public static final String PREFS_NAME = "appPrefsFile";

    public static boolean isConnect2Network(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }

    public static void showNetworkError(Activity activity){
        activity.setContentView(R.layout.network_error);
    }
}


